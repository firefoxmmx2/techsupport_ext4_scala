/**
 * Created by hooxin on 14-5-12.
 */
Ext.define('Techsupport.controller.sysadmin.User', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.user.Add', 'sysadmin.user.List', 'sysadmin.user.Manage',
        'sysadmin.department.DepartmentTree'],
    stores: ['User', 'OneZero', 'DepartmentTree','DictItem'],
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
                click: function (b,evt) {
                    var grid= b.findParentByType('grid');
                    var store=b.findParentByType('grid').getStore();

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
                    var _window=button.findParentByType('window');
                    _window.query('form:first').map(function (form) {
                        if(form.getForm().isValid()){
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
                                    if(action.response.statusCode == 200)
                                        Ext.Msg.alert("错误","错误代码:"+action.result.result+";"+action.result.message);
                                    else if(action.response.statusCode == 400)
                                        Ext.Msg.alert("错误","错误的请求");
                                    else if(action.response.statusCode == 500)
                                        Ext.Msg.alert("错误","服务器发生错误");
                                }
                            });
                        }
                    });
                }
            },
            'useradd checkboxgroup':{
                render: function (cg) {
                    var store=this.getDictItemStore();
                    store.removeAll();
                    store.on('load', function (s, records, successful, eOpts) {
                        if(successful){
                            records.forEach(function (r) {
                                cg.add({boxLabel: r.raw.displayName, name: 'userType', inputValue: r.raw.factValue});
                            });
                        }
                    });
                    store.load({
                        params:{'dictcode':'dm_yhlb',page:1,limit:999}
                    });
                }
            },
            'useradd textfield[name=userorder]':{
                //自动获取最大用户序号
                render:function(t){
                    Ext.Ajax.request({
                        url:'/api/users/maxUserOrder',
                        params:{
                            departid: t.findParentByType('panel').query('textfield[name=departid]')[0].getValue()
                        },
                        success: function (response) {
                            var res=Ext.decode(response.responseText);

                            if(res.result==0){
                                t.setValue(res.data + 1)
                            }
                        }
                    })
                }
            }
        });
    }
})
