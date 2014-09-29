/**
 * 菜单控制器
 */
Ext.define('Techsupport.controller.Menu', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.menu.List',
        'sysadmin.menu.Detail'],
    stores: ['MenuTree', 'Menu'],
    models: ['Menu'],
    refs: [
        {ref: 'queryForm', selector: 'menumanage form'},
        {ref: 'menuTree', selector: 'menumanage menutree'},
        {ref: 'menuListGrid', selector: 'menulist'}
    ],
    init: function () {
        this.toEditMenu = function (record) { //初始化编辑窗口
            var editWindow = this.getView('sysadmin.menu.Detail').create();
            var form = editWindow.down('form');
            if (!record) {
                var tree = this.getMenuTree();
                record = this.getMenuModel().create({
                    parentmenucode: tree.cdata.menucode,
                    menulevel: tree.cdata.menulevel+1,
                    isleaf: 'Y',
                    systemcode: tree.cdata.systemcode
                });
            }
            form.getForm().loadRecord(record);

        };
        this.saveMenu = function (form, store,queryFunc) { //保存菜单
            var controller=this;
            if (form.getForm().isValid()) {
                var window = form.up('window');
                form.getForm().updateRecord();
                store.sync({
                    success: function (batch, options) {
                        store.commitChanges();
//                        queryFunc();
                        this.queryMenu();
                        window.close();
                    },
                    failure: function (batch, options) {
                        store.rejectChanges();
                    }
                });
            }
        };
        this.queryMenu = function () { //查询菜单
            var form = this.getQueryForm();
            var params = form.getForm().getValues();
            params.parentmenucode = this.getMenuTree().cdata.menucode;
            Ext.apply(store.getProxy().extraParams, params);
            store.load();
        };

        this.control({
            'menulist': { //菜单列表
                render: function (g) {
                    g.getStore().removeAll();
                },
                afterlayout: function (g, layout, opts) {
                    var headerHeight = g.headerCt.down('[id*=gridcolumn]').getHeight();
                    var pagesize = Math.round(g.getHeight() / headerHeight);
                    g.getStore().pageSize = pagesize;
                    g.getStore().trailingBufferZone = pagesize;
                    g.getStore().getProxy().setExtraParam('limit', pagesize);
                },
                itemdblclick: function (g, record, item, index, e, eOpts) {
                    this.toEditMenu(record);
                }
            },
            'menudtail textfield[name=id]': {
                change: function (field, newValue, oldValue) {
                    if (newValue != field.originalValue) { //菜单代码重复验证
                        Ext.Ajax.request({
                            url: '/api/menus/checkMenucodeAvaliable',
                            params: {menucode: newValue},
                            scope: idField,
                            success: function (response) {
                                var res = Ext.decode(response.responseText);
                                if (res.result == 0) {
                                    this.clearInvalid();
                                    this.textValid = true;
                                }
                                else {
                                    this.textValid = '该菜单代码已被使用';
                                    this.markInvalid(this.textValid);
                                }
                            },
                            failure: function (response) {
                                this.textValid = '菜单代码验证发生错误';
                                this.markInvalid(this.textValid);
                            }
                        });
                    }
                    else {
                        this.textValid = true;
                        this.clearInvalid();
                    }

                    //自动补全菜单全码
                    var form = field.findParentByType('form')
                    form.query('textfield[name=menufullcode]').map(function (fullcodeField) {
                        var record = form.getRecord();
                        fullcodeField.setValue(record.data.parentMenufullcode + (newValue ? newValue + '.' : newValue));
                    });
                }
            },
            'menudetail button[action=enter]': { //确认按钮
                click: function (button, evt) {
                    var form = button.up('window').down('form');
                    this.saveMenu(form, this.getMenuStore());
                }
            },
            'menudetail button[action=canel]': { //取消按钮
                click: function (button, evt) {
                    button.up('window').close();
                }
            },
            'menumanage button[action=query]': {//查询按钮
                click: function (button, evt) {
                    this.queryMenu();
                }
            },
            'menumanage button[action=modify]': { //修改按钮
                click: function (button, evt) {
                    var controller = this;
                    var grid = this.getMenuListGrid();
                    var selection = grid.getSelectionModel().getSelection().map(function (record) {
                        controller.toEditMenu(record);
                    });
                }
            },
            'menumanage button[action=add]': { //添加按钮
                click: function () {
                    this.toEditMenu();
                }
            }
        });
    }
});