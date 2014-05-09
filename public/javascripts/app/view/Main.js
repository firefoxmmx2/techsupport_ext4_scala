/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.view.Main', {
    extend: 'Ext.panel.Panel',
    layout: 'border',
    alias: 'widget.main',
    items: [
        {xtype: 'systemMenuAccordion', region: 'west', width: '20%'},
        {xtype: 'panel', region: 'north', html: '标题logo'},
        {xtype: 'tabpanel', region: 'center', activeTab: 0, defaults: {
            bodyPadding: 10,
            closable: true
        }, items: [
            {
                title: 'Tab 1',
                html: 'Free-standing tab panel'
            }
        ] },
        {xtype: "panel", region: 'south', html: '底部版权声明'}
    ]
})
