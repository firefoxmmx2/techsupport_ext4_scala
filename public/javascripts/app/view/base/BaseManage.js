/**
 * Created by hooxin on 14-9-18.
 */
Ext.define('Techsupport.view.base.BaseManage', {
    extend: 'Ext.panel.Panel',
    layout: {
        type: 'border',
        align: 'stretch',
        pack: 'start'
    },
    border: false,
    items: [
        {xtype: 'panel', region: 'west', width: '20%', split: true, items: [
            //fit west part
        ]},
        {xtype: 'panel', region: 'center', border: false, layout: 'fit', items: [
            //fit main content
        ],
            dockedItems: [
                {xtype: 'toolbar', dock: 'top', ui: 'footer', items: [
                    {text: '添加', xtype: 'button', action: 'add'} ,
                    '-',
                    {xtype: 'button', text: '修改', action: 'modify'},
                    '-',
                    {xtype: 'button', text: '删除', action: 'remove'},
                    '-',
                    {xtype: 'button', text: '上移', action: 'up'},
                    '-',
                    {xtype: 'button', text: '下移', action: 'down'}
                ]},
                {xtype: 'buttongroup', dock: 'top', items: [
                    {xtype: 'form', layout: 'column', defaults: {margin: {top: 2, bottom: 2, left: 0, right: 20}}, border: false, items: [
                        // fit query condition

                    ]},
                    {xtype: 'button', text: '查询', action: 'query', margin: {top: 2, bottom: 2}}
                ]}
            ]}
    ]
});