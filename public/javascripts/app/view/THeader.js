/**
 * Created by hooxin on 14-5-12.
 */
Ext.define('Techsupport.view.THeader', {
    extend: 'Ext.panel.Panel',
    layout: 'fit',
    alias: 'widget.theader',
    items: [
        {
            xtype:'panel',
            layout:{
                type:'hbox',
                pack:'end'
            },
            border:false,
            defaults:{
                border:false
            },
            items:[
                {html: '<center><h2>标题logo</h2></center>',width:"70%"},
                {
                    xtype:'panel',
                    height:60,
                    layout:{
                        type:'hbox',
                        flex:1,
                        align:'bottom'
                    },
                    defaults:{
                        flex:1
                    },
                    items:[
                        {
                            xtype:'button',
                            text:'首页'
                        },
                        {
                            xtype:'button',
                            text:'注销'
                        },
                        {
                            xtype:'button',
                            text:'关闭'
                        },
                        {
                            xtype:'button',
                            text:'控制面板'
                        }
                    ]
                }
            ]
        }

    ]
})
