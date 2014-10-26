/**
 * 登录日志控制器
 */
Ext.define('Techsupport.controller.sysadmin.LoginLog',{
    extend:'Ext.app.Controller',
    stores:['LoginLog'],
    views:[
        "sysadmin.loginlog.Manage",
        "sysadmin.loginlog.List",
        "sysadmin.loginlog.Detail"
    ],
    models:["LoginLog"],
    refs:[
        {ref:'queryForm',selector:'loginlogmanage panel[region=center] form'},
        {ref:'loginlogListGrid',selector:'loginlogmanage loginloglist'}
    ],
    init: function () {
        this.control({
            "loginlogmanage button[action=query]":{
                //查询按钮
                click:this.queryLoginLog
            },
            "loginlogdetail button[action=close]":{
                click: function (button) { //关闭
                    button.up('window').close()
                }
            },
            "loginlogmanage loginloglist":{
                itemdblclick:this.seeDetail
            }
        })
    },
    queryLoginLog:function (button) {
        //查询登录日志

        if (this.getQueryForm().isValid()) {
            var grid=this.getLoginlogListGrid()
            var store=grid.getStore()
            Ext.apply(store.getProxy().extraParams,this.getQueryForm().getValues())
            store.load()
        }
    },
    seeDetail: function (grid, record) {
        //显示详情
        Ext.Array.each(grid.getSelectionModel().getSelection(), function (r) {
            var _window=this.getView("sysadmin.loginlog.Detail").create({
                title:"登录日志详情"
            })
            var form=_window.down('form')
            form.loadRecord(r)
            _window.show()
        },this)
    }
})
