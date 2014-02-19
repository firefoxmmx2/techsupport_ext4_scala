Ext.application({
    name: 'Techsupport',
    appFolder: 'public/javascripts/app',
    launch: function () {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [
                {xtype: 'panel', html: '技术支持单'}
            ]
        })
    }
})