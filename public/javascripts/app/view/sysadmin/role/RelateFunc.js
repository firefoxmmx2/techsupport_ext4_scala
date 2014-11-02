/**
 * 角色关联功能
 */
Ext.define('Techsupport.view.sysadmin.role.RelateFunc', {
    extend: 'Techsupport.view.base.BaseDetail',
    initComponent: function () {
        this.callParent()
        this.down('form > panel')
            .add([
                {xtype: 'panel',
                    layout: 'vbox',
                    items: [
                        {
                            xtype: 'rolelist',
                            height:80,
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
                            items: [
                                {
                                    xtype: 'combobox',
                                    multiSelect: true,
                                    queryMode: 'local',
                                    displayField:'funcname',
                                    valueField:'funccode',
                                    store: Ext.create('Techsupport.store.RoleFunc', {
                                        autoLoad: false
                                    })
                                },
                                {
                                    xtype: 'panel',
                                    layout: 'vbox',
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
                                    xtype: 'combobox',
                                    multiSelect: true,
                                    queryMode: 'local',
                                    displayField:'funcname',
                                    valueField:'funccode',
                                    store: Ext.create('Techsupport.store.RoleFunc', {
                                        autoLoad: false
                                    })
                                }
                            ]
                        }
                    ]}
            ])
    }
})
