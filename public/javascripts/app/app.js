Ext.application({
    name: 'Techsupport',
    appFolder: 'assets/javascripts/app',
    controllers: ["sysadmin.Login","Main","sysadmin.SystemMenu"],
    launch: function () {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [
                {xtype: 'main', autoShow: true}
            ]
        })

        //心跳检查，当登录验证不通过的返回登录页面
        var loginController = this.getController('sysadmin.Login');
        var heartCheckTask = Ext.TaskManager.start({
            run: loginController.heartCheck,
            interval: 3000,
            scope:this
        })
    }
});