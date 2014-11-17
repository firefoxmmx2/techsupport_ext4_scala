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
                            layout: 'hbox',
                            border: false,
                            height: 130,
                            defaults: {
                                height: '100%'
                            },
                            items: [
                                {
                                    name: 'allFunctionGrid',
                                    xtype: 'gridpanel',
                                    border: false,
                                    width: "45%",
                                    store: Ext.create('Techsupport.store.Function', {
                                        autoLoad: false
                                    }),
                                    columns: [
                                        {
                                            dataIndex: 'funcname', text: '全部功能名称', flex: 1
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ])
    }
})