/**
 * 菜单控制器
 */
Ext.define('Techsupport.controller.sysadmin.Menu', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.menu.List',
        'sysadmin.menu.Detail',
        'sysadmin.menu.MenuTree',
        'sysadmin.menu.Manage'
    ],
    stores: [ 'Menu'],
    models: ['Menu'],
    refs: [
        {ref: 'queryForm', selector: 'menumanage form'},
        {ref: 'menuTree', selector: 'menumanage menutree'},
        {ref: 'menuListGrid', selector: 'menumanage menulist'}
    ],
    init: function () {
        //加载依赖菜单树控制器
        var menuTreeController = this.getController("sysadmin.MenuTree")

        this.toEditMenu = function (record) { //初始化编辑窗口
            var title = '';
            var config = {};

            if (record) {
                title = '菜单修改[' + record.data.menuname + ']';
                config.title = title;
                config.name = "modifyMenuWindow";
            }
            else {
                var tree = this.getMenuTree();
                record = this.getMenuModel().create({
                    parentmenucode: tree.cdata.menucode,
                    menulevel: tree.cdata.menulevel + 1,
                    isleaf: 'Y',
                    systemcode: tree.cdata.systemcode,
                    parentMenufullcode: tree.cdata.menufullcode
                });

                title = '菜单新增[上级菜单:' + tree.cdata.menuname + ']';
                config.title = title;
                config.name = "addMenuWindow";
            }
            var editWindow = this.getView('sysadmin.menu.Detail').create(config);
            var form = editWindow.down('form');
            form.down('textfield[name=id]').originalValue = record.data.id;
            if (record.data.id) {
                Ext.Ajax.request({
                    url: '/api/menus/' + record.data.id,
                    success: function (response) {
                        var res = Ext.decode(response.responseText);
                        if (res.data) {
                            record.data.parentMenufullcode = res.data.menufullcode;
                            form.getForm().getRecord().set('parentMenufullcode', res.data.menufullcode);
                        }
                    }
                });
            }
            form.getForm().loadRecord(record);
            editWindow.show();
        };
        this.saveMenu = function (form, store, queryFunc) { //保存菜单
            var controller = this;
            if (form.getForm().isValid()) {
                var window = form.up('window');
                if (form.up('window[name=addMenuWindow]')) {
                    var record = this.getMenuModel().create(
                        form.getForm().getValues()
                    );
                    store.add(record);
                    form.getForm().submit({
                        url: '/api/menus',
                        method: 'post',
                        params: form.getForm().getValues(),
                        waitMsg: '添加中...',
                        success: function (form, action) {
                            this.queryMenu();
                            this.getMenuTree().refresh(this.getMenuTree().getSelectionModel().getSelection()[0].data.id)
                            window.close();
                        },
                        failure: function (form, action) {
                            if (action.response.status == 200)
                                Ext.Msg.alert('错误', action.result.message);
                            else
                                Ext.Msg.alert('错误', '添加菜单发生致命错误');
                        },
                        scope: this
                    });
                }
                else {
                    form.getForm().updateRecord();
                    var extraParams = store.getProxy().extraParams;
                    store.getProxy().extraParams = {};
                    store.sync({
                        success: function (batch, options) {
                            store.commitChanges();
                            this.queryMenu();
                            this.getMenuTree().refresh(this.getMenuTree().getSelectionModel().getSelection()[0].data.id)
                            window.close();
                        },
                        failure: function (batch, options) {
                            store.rejectChanges();
                        },
                        scope: this
                    });
                    store.getProxy().extraParams = extraParams;
                }
            }
        };
        this.queryMenu = function () { //查询菜单
            var form = this.getQueryForm();
            var params = form.getForm().getValues();
            var store = this.getMenuStore();
            if(this.getMenuTree().getSelectionModel().getCount()==0)
                this.getMenuTree().getSelectionModel().select(this.getMenuTree().getRootNode())
            params.parentmenucode = this.getMenuTree().getSelectionModel().getSelection()[0].data.id;
            Ext.apply(store.getProxy().extraParams, params);
            store.load();
        };

        this.removeMenu = function () { //删除菜单
            var grid = this.getMenuListGrid();
            var store = this.getMenuStore();
            var selection = Ext.Array.map(grid.getSelectionModel().getSelection(), function (record) {
                store.remove(record);
            });
            if (selection.length > 0) {
                store.sync({
                    success: function (batch, options) {
                        store.commitChanges();
                        if (store.getProxy().extraParams.limit < store.getTotalCount()) {
                            this.queryMenu();
                        }
                    },
                    failure: function (batch, options) {
                        store.rejectChanges();
                    },
                    scope: this
                });
            }
        };

        this.control({
            'menulist': { //菜单列表
                itemdblclick: function (g, record, item, index, e, eOpts) {
                    this.toEditMenu(record);
                }
            },
            'menudetail form textfield': {
                specialkey: function (field, evt, option) {
                    if (evt.getKey() === evt.ENTER) {
                        this.saveMenu(field.up('form'), this.getMenuStore())
                    }
                }
            },
            'menudetail textfield[name=id]': {
                change: function (field, newValue, oldValue) {
                    if (newValue && newValue != field.originalValue) { //菜单代码重复验证
                        Ext.Ajax.request({
                            url: '/api/menus/checkMenucodeAvaliable/' + newValue,
                            scope: field,
                            success: function (response) {
                                var res = Ext.decode(response.responseText);
                                if (res.isAvaliable && res.result == 0) {
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
                        field.textValid = true;
                        field.clearInvalid();
                    }

                    //自动补全菜单全码
                    var form = field.findParentByType('form')
                    var fullcodeField = form.down('textfield[name=menufullcode]')
                    var record = form.getRecord();
                    fullcodeField.setValue(record.data.parentMenufullcode + (newValue ? newValue + '.' : newValue));
                }
            },
            'menudetail[name=addMenuWindow] textfield[name=nodeorder]': { //新增菜单时候初始化序列
                render: function (field) {
                    var parentId = field.up('form').getForm().getRecord().data.parentmenucode;
                    Ext.Ajax.request({
                        url: '/api/menus/maxMenuOrder/' + parentId,
                        success: function (response) {
                            var res = Ext.decode(response.responseText);
                            field.setValue(res.data + 1);
                        },
                        failure: function (response) {
                            if (response.state == 200) {
                                var res = Ext.decode(response.responseText);
                                Ext.Msg.alert('错误', res.message);
                            }
                            else
                                Ext.Msg.alert('错误', '获取父菜单为' + parentId + '的当前最大序列发生致命错误');
                        }
                    });
                }
            },
            'menudetail button[action=enter]': { //确认按钮
                click: function (button, evt) {
                    var form = button.up('window').down('form');
                    this.saveMenu(form, this.getMenuStore());
                }
            },
            'menudetail button[action=cancel]': { //取消按钮
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
                    var selection = Ext.Array.map(grid.getSelectionModel().getSelection(),function (record) {
                        controller.toEditMenu(record);
                    });
                }
            },
            'menumanage button[action=add]': { //添加按钮
                click: function () {
                    this.toEditMenu();
                }
            },
            'menumanage button[action=remove]': { //删除按钮
                click: function () {
                    this.removeMenu();
                }
            },
            'menumanage menutree':{//菜单管理里面的菜单树
                render: function (p) {
                    p.on('select', function (view, record, index, eOpts) { //在选择的时候,查询菜单信息
                        this.queryMenu()
                    },this)
                }
            }
        });
    }
});