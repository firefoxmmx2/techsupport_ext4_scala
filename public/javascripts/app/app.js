Ext.application({
    name: 'Techsupport',
    appFolder: 'assets/javascripts/app',
    controllers: [
        "sysadmin.Login",
        "Main",
        "sysadmin.SystemMenu",
        "sysadmin.DepartmentTree",
        "sysadmin.User",
        "sysadmin.Department"
    ],
    refs: [
        {ref: 'viewport', selector: 'viewport'}
    ],
    launch: function () {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [
                {xtype: 'main'}
            ]
        });
        //心跳检查，当登录验证不通过的返回登录页面
        var loginController = this.getController('sysadmin.Login');
        var heartCheckTask = Ext.TaskManager.start({
            run: function () {
                loginController.heartCheck();
            },
            interval: 15000,
            scope: this
        })
    }
});