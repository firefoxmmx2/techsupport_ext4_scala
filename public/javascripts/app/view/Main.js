/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.view.Main',{
    extend:'Ext.panel.Panel',
    layout:'border',
    alias:'widget.main',
    items:[
        {xtype:'systemMenuAccordion',region:'west',width:'20%'},
        {xtype:'panel',region:'north',html:'标题logo'},
        {xtype:'tabpanel',region:'center'}
    ]
})
