/**
 * Created by hooxin on 14-9-17.
 */
Ext.define('Techsupport.controller.sysadmin.Department', {
    extend: 'Ext.app.Controller',
    views: [
        "sysadmin.department.List",
        "sysadmin.department.Manage",
        "sysadmin.department.Add",
        "sysadmin.department.Edit"
    ],
    stores: ['YN', 'Department'],
    models:['Department','DepartmentTree'],
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
                },
                itemdblclick: function (g, record, item, index, e, eOpts) {
                    var _window = Ext.create('Techsupport.view.sysadmin.department.Edit', {name: 'departmentEditWindow'});
                    _window.query('form:first').map(function (f) {
                        Ext.Ajax.request({
                            url: '/api/departments/' + record.data.parentDepartid,
                            success: function (action, options) {
                                var res = Ext.decode(action.responseText);
                                if (res.result == 0) {
                                    record.data.parentDepartname = res.data.departname;
                                    record.data.parentDepartfullcode=res.data.departfullcode;
                                    f.query('textfield[name=departcode]').map(function (field) {
                                        field.originalValue = record.data.departcode;
                                    });
                                    f.getForm().loadRecord(record);
                                }
                                else {
                                    Ext.Msg.alert("提示", res.message);
                                }
                            },
                            failure: function (action, options) {
                                Ext.Msg.alert("错误", "获取单一机构发生错误");
                            }
                        });

                        _window.title += "[" + record.data.departname + "]";
                        _window.show();
                    });
                }
            },
            'departmentManage button[action=add]': {
                //添加按钮
                click: function (button, evt) {
                    var controller=this;
                    var _window = Ext.create('Techsupport.view.sysadmin.department.Add', {name: 'departmentAddWindow'});
                    var tree = this.getDepartmentTree();
                    _window.query('form:first').map(function (f) {
                        var record=controller.getDepartmentModel().create({
                            parentDepartid: tree.cdata.departid,
                            parentDepartname: tree.cdata.departname,
                            parentDepartfullcode: tree.cdata.departfullcode,
                            departfullcode: tree.cdata.departfullcode ? tree.cdata.departfullcode : '',
                            departlevel: tree.cdata.departlevel + 1
                        });
                        f.getForm().loadRecord(record);
                        _window.title += "[上级机构:" + tree.cdata.departname + "]";
                        _window.show();
                    });
                }
            },
            'departmentManage button[action=query]': {
                //查询按钮
                click: function (button, evt) {
                    this.queryDepartment(this);
                }
            },
            'departmentManage button[action=remove]': {
                //删除按钮
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
            'departmentManage button[action=modify]': {
                //修改
                click: function (button, evt) {
                    var controller = this;
                    var grid = button.findParentByType('departmentManage').query('grid')[0];
                    grid.getSelectionModel().getSelection().map(function (r) {
                        var _window = Ext.create('Techsupport.view.sysadmin.department.Edit', {name: 'departmentEditWindow'});
                        var tree = controller.getDepartmentTree();
                        _window.query('form:first').map(function (f) {
                            Ext.Ajax.request({
                                url: '/api/departments/' + r.data.parentDepartid,
                                success: function (action, options) {
                                    var res = Ext.decode(action.responseText);
                                    if (res.result == 0) {
                                        r.data.parentDepartname = res.data.departname;
                                        r.data.parentDepartfullcode = res.data.departfullcode;
                                        f.query('textfield[name=departcode]').map(function (field) {
                                            field.originalValue = r.data.departcode;
                                        });
                                        f.getForm().loadRecord(r);

                                    }
                                }
                            });
                            _window.title += "[" + r.data.departname + "]";
                            _window.show();
                        });
                    });

                }
            },
            'departmentedit[name=departmentEditWindow] button[action=enter]': {
                //修改按钮
                click: function (button, evt) {
                    var _window = button.findParentByType('window');
                    var store = this.getDepartmentStore();
                    var form = _window.query('form')[0];
                    this.modifyDeparment(form, store, function (batch, option) {
                        store.commitChanges();
                        _window.close();
                    }, function (batch, option) {
                        store.rejectChanges();
                    });
                }
            },
            'departmentadd[name=departmentAddWindow] button[action=enter]': {
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
            },
            'departmentadd textfield[name=departcode]': {
                //机构代码重复验证
                change: function (field, newValue, oldValue) {
                    //机构全码补全联动
                    var panel=field.findParentByType('panel');
                    panel.query('textfield[name=departfullcode]').map(
                        function (f) {
                            var record=f.findParentByType('form').getForm().getRecord();
                            f.setValue(record.data.parentDepartfullcode + (newValue ? newValue + "." : ''));
                        }
                    );

                    //机构代码重复验证
                    if(newValue != field.originalValue){
                        Ext.Ajax.request({
                            url: '/api/departments/checkDepartcodeAvailable',
                            params: {'departcode': newValue},
                            scope: field,
                            success: function (response) {
                                var res = Ext.decode(response.responseText);
                                if (res.result == 0 && res.isAvailable) {
                                    this.clearInvalid();
                                    this.textValid = true;
                                }
                                else {
                                    this.textValid = '该机构代码已被使用';
                                    this.markInvalid(this.textValid);
                                    this.focus();
                                }
                            },
                            failure: function (response) {
                                if(response.status == 200){
                                    var res=Ext.decode(response.responseText);
                                    this.textValid = res.message;
                                    this.markInvalid(this.textValid);
                                }
                                else{
                                    Ext.Msg.alert("错误",'机构代码重复验证发生错误');
                                }
                                this.focus();
                            }
                        });
                    }
                    else{
                        field.clearInvalid();
                        field.textValid = true;
                    }
                }
            },
            'departmentadd[name=departmentAddWindow] textfield[name=nodeOrder]':{
                render: function (field) {
                    var tree=this.getDepartmentTree();
                    Ext.Ajax.request({
                        url:"/api/departments/maxDepartmentOrder",
                        params:{parentDepartid:tree.cdata.departid},
                        success: function (response) {
                            var res=Ext.decode(response.responseText);
                            if(res.result == 0) {
                                field.setValue(res.data + 1);
                            }
                            else
                                Ext.Msg.alert('错误',res.message);
                        },
                        failure: function (response) {
                            if(response.status == 200){
                                var res=Ext.decode(res.responseText);
                                Ext.Msg.alert('错误',res.message);
                            }
                            else
                                Ext.Msg.alert('错误','获取最大机构排序号发生致命错误');
                        }
                    });
                }
            }
        });
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
                    if (store.getTotalCount() > store.pageSize)
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
                url: '/api/departments',
                waitMsg: '正在添加...',
                params: basicForm.getValues(),
                success: function (form, action) {
                    if (action.result.result == 0) {
                        controller.queryDepartment(controller);
                        // TODO 获取视图刷新树节点
                        controller.getDepartmentTree().getView().collapse();

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
    modifyDeparment: function (form, store, success, failure) {
        //机构修改
        if (form.getForm().isValid()) {
            var record = form.getForm().updateRecord();
            var extraParams = store.getProxy().extraParams;
            store.getProxy().extraParams={};
            store.sync({
                success: success,
                failure: failure
            });
            store.getProxy().extraprams=extraParams;
        }
    },
    upDepartment: function (grid) {
        //机构上移
    },
    downDepartment: function (grid) {
        //机构下移
    }
});
