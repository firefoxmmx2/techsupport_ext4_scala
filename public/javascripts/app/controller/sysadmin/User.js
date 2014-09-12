/**
 * Created by hooxin on 14-5-12.
 */
Ext.define('Techsupport.controller.sysadmin.User', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.user.Add', 'sysadmin.user.List', 'sysadmin.user.Manage',
        'sysadmin.department.DepartmentTree'],
    stores: ['User', 'OneZero', 'DepartmentTree'],
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
                    var _window  = Ext.create('Techsupport.view.sysadmin.user.Add');
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
                click: function () {
                }
            },
            'usermanage button[action=query]': {
//                查询按钮
                click: function () {
                    var params = this.getQueryForm().getForm().getValues();
                    params.departid = this.getDepartmentTree().cdata.departid;
                    this.getUserStore().load({
                        params: params
                    });
                }
            },
            'useradd button[action=cancel]': {
                //添加窗口取消按钮
                click: function (button,evt) {
                    button.findParentByType('window').close();
                }
            },
            'useradd button[action=enter]':{
                //添加窗口确认按钮
                click: function (button,evt) {
                    var _window=Ext.getCmp(Ext.get(Ext.EventObject.getTarget()).id).findParentByType('window');
                    _window.query('form:first').map(function (form) {
                        if(form.getForm().isValid){
                            form.submit({
                                url:"/api/users",
                                method:'POST',
                                params:form.getForm().getValues(),
                                waitMsg:'正在添加用户...',
                                success: function (form, action) {
                                    //成功
                                    if(action.result.result == 0){
                                        _window.close();
                                    }
                                },
                                failure: function (form, action) {
                                    //失败
                                    Ext.Msg.alert("错误","错误代码:"+action.result.result+";"+action.result.message);
                                }
                            });
                        }
                    });
                }
            }
        });
    }
})
