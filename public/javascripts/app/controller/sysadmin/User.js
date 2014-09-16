/**
 * Created by hooxin on 14-5-12.
 */
Ext.define('Techsupport.controller.sysadmin.User', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.user.Add', 'sysadmin.user.List', 'sysadmin.user.Manage',
        'sysadmin.department.DepartmentTree'],
    stores: ['User', 'OneZero', 'DepartmentTree', 'DictItem'],
    refs: [
        {ref: 'queryForm', selector: 'panel buttongroup[dock=top] form'},
        {ref: 'departmentTree', selector: 'departmenttree'}
    ],
    init: function () {
        this.control({
            'userlist': {
                afterlayout: function (g, layout, opts) {
                    var headerHeight = g.headerCt.down('[id*=gridcolumn]').getHeight();
                    var pagesize = Math.round(g.getHeight() / headerHeight);
                    g.getStore().pageSize = pagesize;
                    g.getStore().trailingBufferZone = pagesize;
                    g.getStore().getProxy().setExtraParam('limit', pagesize);
                },
                render: function (g) {
                    g.getStore().removeAll();
                }
            },
            'usermanage button[action=add]': {
//                添加按钮
                click: function () {
                    var _departmentTree = this.getDepartmentTree();
                    var _window = Ext.create('Techsupport.view.sysadmin.user.Add');
                    _window.query('form:first').map(function (f) {
                        if (_departmentTree.cdata.departid && _departmentTree.cdata.departid != 0) {
                            f.getForm().setValues({
                                departid: _departmentTree.cdata.departid,
                                departname: _departmentTree.cdata.departname
                            });
                            _window.title += "[" + _departmentTree.cdata.departname + "]";
                            _window.show();
                        }
                        else {
                            Ext.Msg.alert('提示', '请选择机构');
                        }
                    });
                }
            },
            'usermanage button[action=remove]': {
//                删除按钮
                click: function (b, evt) {
                    var controller = this;
                    var grid = b.findParentByType('usermanage').query('grid')[0];
                    var store = grid.getStore();
                    var selection = grid.getSelectionModel().getSelection();
                    if (selection.length > 0) {
                        store.remove(selection);
                        store.sync({
                            success: function (batch, options) {
                                store.commitChanges();
                                controller.querylist(controller);
                            },
                            failure: function (batch, options) {
                                store.rejectChanges();
                            }
                        });
                    }
                    else
                        Ext.Msg.alert("提示", "请选择需要删除的数据");
                }
            },
            'usermanage button[action=query]': {
//                查询按钮
                click: function () {
                    this.querylist(this);
                }
            },
            'useradd button[action=cancel]': {
                //添加窗口取消按钮
                click: function (button, evt) {
                    button.findParentByType('window').close();
                }
            },
            'useradd button[action=enter]': {
                //添加窗口确认按钮
                click: function (button, evt) {
                    var controller = this;
                    var _window = button.findParentByType('window');
                    _window.query('form:first').map(function (form) {
                        if (form.getForm().isValid()) {
                            form.submit({
                                url: "/api/users",
                                method: 'POST',
                                params: form.getForm().getValues(),
                                waitMsg: '正在添加用户...',
                                success: function (form, action) {
                                    //成功
                                    if (action.result.result == 0) {
                                        controller.querylist(controller);
                                        _window.close();
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
                    });
                }
            },
            'useradd checkboxgroup': {
                render: function (cg) {
                    var store = this.getDictItemStore();
                    store.removeAll();
                    store.on('load', function (s, records, successful, eOpts) {
                        if (successful) {
                            records.forEach(function (r) {
                                cg.add({boxLabel: r.raw.displayName, name: 'userType', inputValue: r.raw.factValue});
                            });
                        }
                    });
                    store.load({
                        params: {'dictcode': 'dm_yhlb', page: 1, limit: 999}
                    });
                }
            },
            'useradd textfield[name=userorder]': {
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

            },
            'usermanage button[action=down]': {

            },
            'useradd textfield[name=useraccount]': {
                //用户帐号加上不可重复验证
                initComponent: function (t) {
                    t.textValid = false;
                    t.validator = function (value) {
                        return t.textValid;
                    }
                },
                'change': function (textfield, newValue, oldValue) {
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
                                this.markInvalid(res.message);
                                this.textValid = false;
                                this.focus();
                            }
                        },
                        failure: function (response) {
                            Ext.Msg.alert("错误",'账户重复验证发生错误');
                            this.focus();
                        }
                    });
                }
            }
        });
    },
    querylist: function (controller) {
        var params = controller.getQueryForm().getForm().getValues();
        params.departid = controller.getDepartmentTree().cdata.departid;
        Ext.apply(controller.getUserStore().getProxy().extraParams, params);
        controller.getUserStore().load();
    }
})
