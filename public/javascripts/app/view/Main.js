/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.view.Main', {
    extend: 'Ext.panel.Panel',
    layout: 'border',
    alias: 'widget.main',
    width: '100%',
    height: '100%',
    items: [
        { xtype: 'systemMenuAccordion', region: 'west', width: '20%'},
        { xtype: 'theader', region: 'north'},
        { xtype: 'tabpanel', region: 'center', activeTab: 0, defaults: {
            bodyPadding: 10,
            closable: true
        }, items: [
            {
                title: 'Tab 1',
                html: 'Free-standing tab panel',
                closable: false
            }
        ] },
        { xtype: "copyright", region: 'south'}
    ]
})
