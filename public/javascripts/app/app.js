Ext.application({
    requries: ['Ext.container.Viewport'],
    name: 'Techsupport',
    appFolder: 'assets/javascripts/app',
    controllers: ["sysadmin.Login"],
    launch: function () {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [
                {xtype: 'image', src: "assets/images/favicon.png"},
                {xtype: 'login', autoShow: true}
            ]
        })
    }
});