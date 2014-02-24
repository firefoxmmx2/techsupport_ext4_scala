Ext.application({
    requrie:['Ext.container.Viewport'],
    name: 'Techsupport',
    appFolder: 'assets/javascripts/app',
    controllers:["sysadmin.Login"],
    launch:function(){
        Ext.create('Ext.container.Viewport',{
            layout:'fit',
            title:'head',
            items:[{xtype:'login'}],
            init:function(){
                this.callParent(arguments);
            }
        })
    }
});