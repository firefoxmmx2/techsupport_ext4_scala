/**
 * Created by hooxin on 14-9-17.
 */
Ext.define('Techsupport.controller.sysadmin.Department', {
    extend: 'Ext.app.Controller',
    views: [
        "sysadmin.department.List", "sysadmin.department.Manage"
        /*,
         "sysadmin.department.Add", "sysadmin.department.Edit"*/],
    stores: ['OneZero', 'DepartmentTree', 'Department'],
    refs: [
        {ref: 'departmentTree', selector: 'departmenttree'},
        {ref: 'queryForm', selector: 'panel buttongroup[dock=top] form'}
    ],
    init: function () {
        this.control({
            'departmentlist':{
                render: function (g) {
                    g.getStore().removeAll();
                },
                afterlayout: function (g, layout, opts) {
                    var headerHeight = g.headerCt.down('[id*=gridcolumn]').getHeight();
                    var pagesize = Math.round(g.getHeight() / headerHeight);
                    g.getStore().pageSize = pagesize;
                    g.getStore().trailingBufferZone = pagesize;
                    g.getStore().getProxy().setExtraParam('limit', pagesize);
                }
            },
            'departmentManage button[action=add]': {
                //添加机构
                click: function (button, evt) {
                    var _window = Ext.create('Techsupport.view.sysadmin.department.Add');
                    var tree = this.getDepartmentTree();
                    _window.query('form:first').map(function (f) {
                        if (tree.cdata.departid && tree.cdata.departid != 0) {
                            f.getForm().setValues({
                                departid: tree.cdata.departid,
                                departname: tree.cdata.departname
                            });
                            _window.title += "[" + tree.cdata.departname + "]";
                            _window.show();
                        }
                        else {
                            Ext.Msg.alert('提示', '请选择机构');
                        }
                    })
                }
            },
            'departmentManage button[action=query]': {
                //查询
                click: function (button, evt) {
                    this.queryDepartment(this);
                }
            },
            'departmentManage button[action=remove]': {
                //删除
                click: function (button, evt) {
                    this.removeDeparment(button.findParentByType('grid'),this);
                }
            },
            'departmentManage button[action=up]': {
                //上移
                click: function (button, evt) {
                    this.upDepartment(button.findParentByType('grid'));
                }
            },
            'departmentManage button[action=down]': {
                // 下移
                click: function (button, evt) {
                    this.downDepartment(button.findParentByType('grid'));
                }
            }
        })
        ;
    },
    queryDepartment: function (controller) {
        //机构查询
        var params = controller.getQueryForm().getForm().getValues();
        params.parentDepartid = controller.getDepartmentTree().cdata.departid;
        Ext.apply(controller.getDepartmentStore().getProxy().extraParams, params);
        controller.getDepartmentStore().load();
    },
    removeDeparment: function (grid,controller) {
        //机构删除
        var store = grid.getStore();
        var selection = grid.getSelectionModel().getSelection().map(function (r) {
            store.remove(r);
            return r;
        });
        if (selection.length > 0) {
            store.sync({
                success: function (batch, options) {
                    store.commitChanges();
                    controller.queryDepartment(controller);
                },
                failure: function (batch, options) {
                    store.rejectChanges();
                }
            });
        }
    },
    addDeparment: function (grid) {
        //机构添加
    },
    modifyDeparment: function (grid) {
        //机构修改
    },
    upDepartment: function (grid) {
        //机构上移
    },
    downDepartment: function (grid) {
        //机构下移
    }
});
