/**
 *  角色关联菜单
 */
Ext.define('Techsupport.view.sysadmin.role.RelateMenu', {
    extend: 'Techsupport.view.base.BaseDetail',
    title: '角色关联菜单',
    alias: 'widget.relateMenu',
    initComponent: function () {
        this.items = Ext.clone(this.superclass.items)
        var formGtPanel = this.items[0].items[0]
        formGtPanel.defaults = {}
        formGtPanel.layout = undefined
        this.callParent()
        this.down('form > panel')
            .add([
                {
                    anchor: '100%',
                    border: false,
                    items: [
                        {
                            xtype: 'rolelist',
                            border: false,
                            height: 130,
                            dockedItems: [],
                            store: Ext.create('Ext.data.Store', {
                                model: 'Techsupport.model.Role'
                            }),
                            listeners: {
                                render: function (p) {

                                },
                                afterrender: function (p) {

                                }
                            }
                        },
                        {
                            xtype: 'panel',
                            border: false,
                            layout: 'fit',
                            height: Ext.getBody().getHeight()/5,
                            items: [
                                {
                                    name: 'selectedMenuGrid',
                                    xtype: 'menutree',
                                    border: false,
                                    selectedStore:Ext.create('Ext.data.Store',{
                                        model:'Techsupport.model.RoleMenu'
                                    }),
                                    selType:'checkboxmodel',
                                    listeners:{
                                        afterrender: function (p) {

                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            ])
    }
})