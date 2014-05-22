/**
 * Created by hooxin on 14-5-22.
 */
Ext.define('Techsupport.view.sysadmin.department.List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.department.list',
    store: 'Department',
    loadMask: true,
    columns: {
        items: [
            {text: 'ID', dataIndex: 'id'},
            {text: '机构代码', dataIndex: 'departcode'},
            {text: '机构名称', dataIndex: 'departname'},
            {text: '机构级别', dataIndex: 'departlevel'},
            {text: '机构全码', dataIndex: 'departfullcode'},
            {text: '序号', dataIndex: 'nodeOrder'},
            {text: '是否有下级', dataIndex: 'isLeaf'},
            {text: '机构简拼', dataIndex: 'departsimplepin'},
            {text: '机构全拼', dataIndex: 'departallpin'},
            {text: '机构省级代码', dataIndex: 'departbrevitycode'}
        ],
        defaults: {
            flex: 1
        }
    },
    dockedItems: [
        {xtype: 'toolbar', dock: 'top', ui: 'footer', items: [
            {text: '添加', xtype: 'button', action: 'add'} ,
            '-',
            {xtype: 'button', text: '删除', action: 'remove'}
        ]},
        {xtype: 'toolbar', dock: 'top', items: [
            {xtype: 'form', layout: 'column', defaults: {bodyPadding: 2, margin: {top: 0, bottom: 0, left: 0, right: 20}}, border: false, items: [
                {xtype: 'textfield', name: 'departcode', fieldLabel: '机构代码'},
                {xtype: 'textfield', name: "departname", fieldLabel: '机构名称'},
                {xtype: 'combobox',
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
        ]},
        {xtype: 'pagingtoolbar',
            store: 'Department',
            dock: 'bottom',
            pageSize: 10}
    ]
});
