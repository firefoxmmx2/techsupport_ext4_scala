Ext.define('Techsupport.view.sysadmin.Department.Manage', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.departmentManage',
    layout: {
        type: 'border',
        align: 'stretch',
        pack: 'start'
    },
    border: false,
    items: [
        {xtype: 'departmenttree', region: 'west', width: '20%', split: true},
        {xtype: 'panel', region: 'center', border: false, layout: 'fit', items: [
            {xtype: 'department.list'}
        ],
            dockedItems: [
                {xtype: 'toolbar', dock: 'top', ui: 'footer', items: [
                    {text: '添加', xtype: 'button', action: 'add'} ,
                    '-',
                    {xtype: 'button', text: '删除', action: 'remove'}
                ]},
                {xtype: 'buttongroup', dock: 'top', items: [
                    {xtype: 'form', layout: 'column', defaults: {bodyPadding: 2, margin: {top: 0, bottom: 0, left: 0, right: 20}}, border: false, items: [
                        {xtype: 'textfield', name: 'departcode', fieldLabel: '机构代码'},
                        {xtype: 'textfield', name: "departname", fieldLabel: '机构名称'},
                        {xtype: 'combobox',
                            blank:false,
                            name: 'isLeaf',
                            store: 'YN',
                            queryMode: 'local',
                            displayField: 'text',
                            valueField: 'value',
                            fieldLabel: '是否有下级',
                            editable: false
                        }
                    ]} ,
                    {xtype: 'button', text: '查询', action: 'query'}
                ]}
            ]
        }


    ]
})
