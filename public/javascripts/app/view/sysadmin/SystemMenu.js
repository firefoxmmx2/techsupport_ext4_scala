/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.view.sysadmin.SystemMenu', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.systemMenuAccordion',
    layout: {
        type: 'accordion',
        titleCollapse: true,
        animate: true,
        activeOnTop: true
    },
    defaults: {
        bodyPadding: 15
    }
})
