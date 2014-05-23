/**
 * Created by hooxin on 14-5-23.
 */
Ext.define('Techsupport.view.sysadmin.user.Manage', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.usermanage',
    layout: 'hbox',
    height:'100%',
    border: false,
    items: [
        {xtype: 'panel',layout:'fit', width: '100%', border: false, defaults: {bodyBorder: false}, items: [
            {xtype: 'userlist'}
        ],
            dockedItems: [
                {xtype: 'toolbar', dock: 'top', ui: 'footer', items: [
                    {text: '添加', xtype: 'button', action: 'add'} ,
                    '-',
                    {xtype: 'button', text: '删除', action: 'remove'}
                ]},
                {xtype: 'toolbar', dock: 'top', items: [
                    {xtype: 'form', layout: 'column', defaults: {bodyPadding: 2, margin: {top: 0, bottom: 0, left: 0, right: 20}}, border: false, items: [
                        {xtype: 'textfield', name: 'useraccount', fieldLabel: '帐号'},
                        {xtype: 'textfield', name: "username", fieldLabel: '用户姓名'},
                        {xtype: 'combobox',
                            name: 'isValid',
                            store: 'YN',
                            queryMode: 'local',
                            displayField: 'text',
                            valueField: 'value',
                            fieldLabel: '是否可用',
                            editable: false
                        }
                    ]} ,
                    {xtype: 'button', text: '查询', action: 'query'}
                ]}
            ]}
    ]
});
