/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.view.Main', {
    extend: 'Ext.panel.Panel',
    layout: 'border',
    alias: 'widget.main',
    items: [
        { xtype: 'systemMenuAccordion', region: 'west', width: '20%'},
        { xtype: 'theader', region: 'north'},
        { xtype: 'tabpanel', layout: 'fit',
            region: 'center', activeTab: 0, defaults: {
            bodyPadding: '0 0 0 0',
            width: '100%',
            height: '100%',
            closable: true
        }, items: [
            {
                title: 'Tab 1',
                html: 'Free-standing tab panel',
                layout:'fit',
                closable: false
            }
        ] },
        { xtype: "copyright", region: 'south'}
    ]
})
