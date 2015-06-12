/**
 * Created by hooxin on 14-5-12.
 */
Ext.define('Techsupport.controller.sysadmin.User', {
    extend: 'Ext.app.Controller',
    views: [
        'sysadmin.user.Add',
        'sysadmin.user.List',
        'sysadmin.user.Manage',
        'sysadmin.user.Edit'
    ],
    stores: [
        'User',
        'OneZero',
        'DictItem'],
    models: [
        'User'
    ],
    refs: [
        {ref: 'queryForm', selector: 'usermanage panel form'},
        {ref: 'departmentTree', selector: 'usermanage departmenttree'}
    ],
    init: function () {
        //加载依赖机构树控制器
        var departmentTreeController = this.getController("sysadmin.DepartmentTree")
        this.control({
            'userlist': {
                itemdblclick: function (g, record, item, index, e, eOpts) {
                    //打开更新窗口
                    this.toModifyUser(g, this.getView('sysadmin.user.Edit'));
                },
                render: function (g) {
                    g.userTypeDictItemStore=Ext.create('Techsupport.store.DictItem');
                    g.userTypeDictItemStore.load({ params: {'dictcode': 'dm_yhlb', page: 1, limit: 999}})
                }
            },
            'usermanage button[action=add]': {
//                添加按钮
                click: function () {
                    var controller = this;
                    var _departmentTree = this.getDepartmentTree();
                    var _window = Ext.create('Techsupport.view.sysadmin.user.Add', {name: 'userAddWindow'});
                    var f = _window.down('form:first')
                    if (_departmentTree.cdata.departid && _departmentTree.cdata.departid != 0) {
                        var record = controller.getUserModel().create({
                            departid: _departmentTree.cdata.departid,
                            departname: _departmentTree.cdata.departname
                        });
                        f.getForm().loadRecord(record);
                        _window.title += "[" + _departmentTree.cdata.departname + "]";
                        _window.show();
                    }
                    else {
                        Ext.Msg.alert('提示', '请选择机构');
                    }
                }
            },
            'usermanage button[action=modify]': {
                click: function (button, evt) {
                    //修改按钮
                    var grid = button.findParentByType('usermanage').query('userlist')[0];
                    var editView = this.getView("sysadmin.user.Edit");
                    var dictItemStore = this.getDictItemStore();
                    this.toModifyUser(grid, editView, dictItemStore);
                }
            },
            'usermanage button[action=remove]': {
//                删除按钮
                click: function (b, evt) {
                    var grid = b.findParentByType('usermanage').query('grid')[0];
                    this.removeUser(grid, this);
                }
            },
            'usermanage button[action=query]': {
//                查询按钮
                click: function () {
                    this.queryUsers(this);
                }
            },
            'useradd': {
                afterrender: function (w) {
                    var record = w.record;
                    if (record) {
                        var form = w.query('form:first')[0];
                        var store = this.getDictItemStore();
                        Ext.Ajax.request({
                            url: '/api/departments/' + record.data.departid,
                            success: function (response) {
                                var res = Ext.decode(response.responseText);
                                if (res.result == 0) {
                                    record.data.departname = res.data.departname;
                                    record.data.password2 = record.data.password;
                                    var field = form.down('textfield[name=useraccount]')
                                    field.originalValue = record.data.useraccount;
                                    record.data.userType = Ext.isString(record.data.userType) ? record.data.userType.split(',') : record.data.userType;
                                    form.loadRecord(record);
                                }
                                else {

                                    Ext.Msg.alert('提示', res.message);
                                }
                            },
                            failure: function (response) {
                                Ext.Msg.alert("错误", "获取机构信息发生错误");
                            }
                        });

                    }
                }
            },
            'useradd button[action=cancel]': {
                //添加窗口取消按钮
                click: function (button, evt) {
                    button.findParentByType('window').close();
                }
            },
            'useradd[name=userAddWindow] button[action=enter]': {
                //添加窗口确认按钮
                click: function (button, evt) {
                    var _window = button.findParentByType('window');
                    this.addUser(this, _window);
                }
            },
            'useradd checkboxgroup': {
                render: function (cg) {
                    var record = cg.findParentByType('window').record;
                    var store = Ext.create('Techsupport.store.DictItem');
                    store.on('load', function (s, records, successful, eOpts) {
                        if (successful) {
                            records.forEach(function (r) {
                                cg.add({boxLabel: r.raw.displayName, name: 'userType', inputValue: r.raw.factValue,
                                    uncheckedValue: '0'});
                            });
                            if (record) //当窗口不存在更新记录的时候,再进行复选框内容初始化
                                cg.setValue({userType: record.data.userType});
                        }
                    });
                    store.load({
                        params: {'dictcode': 'dm_yhlb', page: 1, limit: 999}
                    });
                }
            },
            'useradd[name=userAddWindow] textfield[name=userorder]': {
                //自动获取最大用户序号
                render: function (t) {
                    Ext.Ajax.request({
                        url: '/api/users/maxUserOrder/' + t.findParentByType('panel').query('textfield[name=departid]')[0].getValue(),
                        success: function (response) {
                            var res = Ext.decode(response.responseText);
                            if (res.result == 0) {
                                t.setValue(res.data + 1)
                            }
                        }
                    })
                }
            },
            'usermanage button[action=up]': {
                // todo 上移

            },
            'usermanage button[action=down]': {
                // todo 下移
            },
            'useradd textfield[name=useraccount]': {
                //用户帐号加上不可重复验证
                initComponent: function (t) {
                    t.textValid = false;
                },
                'change': function (textfield, newValue, oldValue) {
                    if (newValue != textfield.originalValue) {
                        Ext.Ajax.request({
                            url: '/api/users/checkUseraccountAvailable',
                            params: {'useraccount': newValue},
                            scope: textfield,
                            success: function (response) {
                                var res = Ext.decode(response.responseText);
                                if (res.result == 0) {
                                    this.clearInvalid();
                                    this.textValid = true;
                                }
                                else {
                                    this.textValid = res.message;
                                    this.markInvalid(this.textValid);
                                    this.focus();
                                }
                            },
                            failure: function (response) {
                                if (response.status == 200) {
                                    var res = Ext.decode(response.responseText);
                                    this.textValid = res.message;
                                    this.markInvalid(this.textValid);
                                }
                                else
                                    Ext.Msg.alert("错误", '账户重复验证发生错误');
                                this.focus();
                            }
                        });
                    }
                    else {
                        textfield.clearInvalid();
                        textfield.textValid = true;
                    }
                }
            },
            'useredit[name=modifyUserWindow] button[action=enter]': {
                click: function (button, evt) {
                    var _window = button.findParentByType('window');
                    var form = _window.query('form')[0];
                    this.modifyUser(form, this.getUserStore(), _window);
                }
            },
            'usermanage departmenttree':{//用户管理下的机构树
                render: function (p) {
                    p.on('select', function (node, record, index, opts) { //在选择的时候查询用户
                        this.queryUsers(this)
                    },this)
                }
            }
        });
    },
    queryUsers: function (controller) {
        //查询用户
        var params = controller.getQueryForm().getForm().getValues();
        var tree=controller.getDepartmentTree();
        if(tree.getSelectionModel().getCount()==0)
            tree.getSelectionModel().select(tree.getRootNode())
        params.departid = tree.getSelectionModel().getSelection()[0].data.id;
        Ext.apply(controller.getUserStore().getProxy().extraParams, params);
        controller.getUserStore().load();
    },
    addUser: function (controller, window) {
        //添加用户
        var form = window.down('form:first')
        if (form.getForm().isValid()) {
            form.submit({
                url: "/api/users",
                method: 'POST',
                params: form.getForm().getValues(),
                waitMsg: '正在添加用户...',
                success: function (form, action) {
                    //成功
                    if (action.result.result == 0) {
                        controller.queryUsers(controller);
                        window.close();
                    }
                },
                failure: function (form, action) {
                    //失败
                    if (action.response.status == 200)
                        Ext.Msg.alert("错误", "错误代码:" + action.result.result + ";" + action.result.message);
                    else if (action.response.status == 400)
                        Ext.Msg.alert("错误", "错误的请求");
                    else if (action.response.status == 500)
                        Ext.Msg.alert("错误", "服务器发生错误");
                }
            });
        }

    },
    modifyUser: function (form, store, window) {
        // 更新用户
        if (form.getForm().isValid()) {
            var record = form.getForm().getRecord();
            if(typeof record.data.userType == "string")
                record.data.userType = record.data.userType.split(',');
            record.data.userType = record.data.userType.filter(function (v) {
                return v;
            });
            form.getForm().updateRecord();
            var extraParams = store.getProxy().extraParams;
            store.getProxy().extraParams = {};
            store.sync({
                success: function (batch, option) {
                    store.commitChanges();
                    window.close();
                },
                failure: function (batch, option) {
                    store.rejectChanges();
                }
            });
            store.getProxy().extraprams = extraParams;
        }

    },
    removeUser: function (grid, controller) {
        //  删除用户
        var store = grid.getStore();
        var selection = grid.getSelectionModel().getSelection();
        if (selection.length > 0) {
            store.remove(selection);
            store.sync({
                success: function (batch, options) {
                    store.commitChanges();
                    controller.queryUsers(controller);
                },
                failure: function (batch, options) {
                    store.rejectChanges();
                }
            });
        }
        else
            Ext.Msg.alert("提示", "请选择需要删除的数据");
    },
    toModifyUser: function (grid, editView, dictItemStore) {

        var selection = Ext.Array.map(grid.getSelectionModel().getSelection(), function (record) {
            var window = editView.create({name: 'modifyUserWindow', record: record});
            var form = window.query('form:first')[0];

            window.show();
        });
    }
});
