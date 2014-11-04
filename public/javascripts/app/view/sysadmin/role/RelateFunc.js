/**
 * 角色关联功能
 */
Ext.define('Techsupport.view.sysadmin.role.RelateFunc', {
    extend: 'Techsupport.view.base.BaseDetail',
    title:'角色功能关联',
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
                                    xtype: 'grid',
                                    border:false,
                                    store: Ext.create('Techsupport.store.RoleFunc', {
                                        autoLoad: false
                                    }),
                                    columns:[
                                        {
                                            dataIndex:'funcname',text:'功能名称'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'panel',
                                    layout: {
                                        type:'vbox',
                                        align:'center'
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
                                    border:false,
                                    store: Ext.create('Techsupport.store.RoleFunc', {
                                        autoLoad: false
                                    }),
                                    columns:[
                                        {
                                            dataIndex:'funcname',text:'功能名称'
                                        }
                                    ]
                                }
                            ]
                        }
                    ]}
            ])
    }
})
