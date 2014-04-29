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
//    dockedItems: [
//        {xtype: 'panel', items: [
//            {xtype: 'button', text: '按钮1'},
//            {xtype: 'button', text: '按钮2'}
//        ]}
//    ],
//    items: [
//        {title: '系统', html: '系统'},
//        {title: '命令', html: '命令'},
//        {title: '支持单', html: '支持单'}
//    ]
})
