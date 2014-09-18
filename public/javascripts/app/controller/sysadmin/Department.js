/**
 * Created by hooxin on 14-9-17.
 */
Ext.define('Techsupport.controller.sysadmin.Department', {
    extend: 'Ext.app.Controller',
    views: [
        "sysadmin.department.List",
        "sysadmin.department.Manage"
        /*,
         "sysadmin.department.Add", "sysadmin.department.Edit"*/
    ],
    stores: ['YN', 'Department'],
    refs: [
        {ref: 'departmentTree', selector: 'departmentManage departmenttree'},
        {ref: 'queryForm', selector: 'departmentManage panel buttongroup[dock=top] form'}
    ],
    init: function () {
        this.control({
            'departmentlist': {
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
                                parentDepartid: tree.cdata.departid,
                                parentDepartname: tree.cdata.departname,
                                departfullcode:tree.cdata.departcode?tree.cdata.departcode+'.' : ''
                            });
                            _window.title += "[" + tree.cdata.departname + "]";
                            _window.show();
                        }
                        else {
                            Ext.Msg.alert('提示', '请选择机构');
                        }
                    });
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
                    this.removeDeparment(button.findParentByType('departmentManage').query('grid')[0], this);
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
            },
            'departmentManage button[action=modify]':{
                //修改
                click: function (button, evt) {
                    var grid=button.findParentByType('departmentManage').query('grid')[0];
                    grid.getSelectionModel().getSelection().map(function (r) {
                        var _window = Ext.create('Techsupport.view.sysadmin.department.Edit');
                        var tree = this.getDepartmentTree();
                        _window.query('form:first').map(function (f) {
                            if (tree.cdata.departid && tree.cdata.departid != 0) {
//                            f.getForm().setValues({
//                                parentDepartid: tree.cdata.departid,
//                                parentDepartname: tree.cdata.departname,
//                                departfullcode:tree.cdata.departcode?tree.cdata.departcode+'.' : ''
//                            });
                                f.getForm().loadRecord(r);
                                _window.title += "[" + tree.cdata.departname + "]";
                                _window.show();
                            }
                            else {
                                Ext.Msg.alert('提示', '请选择机构');
                            }
                        });
                    });

                }
            },
            'departmentadd button[action=enter]': {
                //确认添加
                click: function (button, evt) {
                    this.addDeparment(this, button.findParentByType('window'));
                }
            },
            'departmentadd button[action=cancel]': {
                //关闭窗口
                click: function (button, evt) {
                    button.findParentByType('window').close();
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
    removeDeparment: function (grid, controller) {
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
    addDeparment: function (controller, window) {
        //机构添加
        var basicForm = window.query('form')[0].getForm();
        if (basicForm.isValid()) {
            basicForm.submit({
                url:'/api/departments',
                waitMsg:'正在添加...',
                params:basicForm.getValues(),
                success: function (form, action) {
                    if (action.result.result == 0) {
                        controller.queryDepartment(controller);
                        window.close();
                    }
                },
                failure: function (form, action) {
                    if (action.response.status == 200) {
                        Ext.Msg.alert("错误", action.result.message);
                    }
                    else {
                        Ext.Msg.alert('错误', '机构新增发生致命错误');
                    }
                }
            });
        }
    },
    modifyDeparment: function (controller,window) {
        //机构修改
    },
    upDepartment: function (grid) {
        //机构上移
    },
    downDepartment: function (grid) {
        //机构下移
    }
});
