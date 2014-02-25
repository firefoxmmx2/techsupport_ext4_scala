Ext.application({
    requrie:['Ext.container.Viewport'],
    name: 'Techsupport',
    appFolder: 'assets/javascripts/app',
    controllers:["sysadmin.Login"],
    modals:['Login','Department','User'],
    launch:function(){
        Ext.create('Ext.container.Viewport',{
            layout:'fit',
            title:'head',
            items:[{xtype:'login'}]
        })
    }
});