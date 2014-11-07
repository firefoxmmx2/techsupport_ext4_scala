/**
 * 角色关联功能
 */
Ext.define('Techsupport.view.sysadmin.role.RelateFunc', {
    extend: 'Techsupport.view.base.BaseDetail',
    title: '角色功能关联',
    modal: true,
    alias:'widget.relateFunc',
    initComponent: function () {
        this.items = Ext.clone(this.superclass.items)
        var formGtPanel = this.items[0].items[0]
        formGtPanel.defaults = {}
        formGtPanel.layout = undefined
        this.callParent(arguments)
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
                                },
                                {
                                    xtype: 'panel',
                                    border: false,
                                    width: "10%",
                                    layout: {
                                        type: 'vbox',
                                        align: 'center',
                                        pack: 'center',
                                        flex: 1,
                                        defaultMargins: {
                                            top: 2, right: 2, bottom: 2, left: 2
                                        }
                                    },
                                    defaults: {
                                        width: '100%'
                                    },
                                    items: [
                                        {
                                            xtype: 'button',
                                            text: '全左'
                                        },
                                        {
                                            xtype: 'button',
                                            text: '左'
                                        },
                                        {
                                            xtype: 'button',
                                            text: '右'
                                        },
                                        {
                                            xtype: 'button',
                                            text: '全右'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'grid',
                                    border: false,
                                    width: '45%',
                                    store: Ext.create('Techsupport.store.Function', {
                                        autoLoad: false
                                    }),
                                    columns: [
                                        {
                                            dataIndex: 'funcname', text: '已选择功能名称', flex: 1
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
