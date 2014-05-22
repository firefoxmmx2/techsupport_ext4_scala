/**
 * Created by hooxin on 14-5-8.
 */
Ext.define('Techsupport.view.sysadmin.user.List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.user.list',
    store: 'User',
    loadMask: true,
    height: '100%',
    columns: {
        items: [
            {text: 'ID', dataIndex: 'id', sortable: false},
            {text: '帐号', dataIndex: 'useraccount', sortable: false},
            {text: '姓名', dataIndex: 'username', sortable: false},
            {text: '密码', dataIndex: 'password', sortable: false},
            {text: '身份证', dataIndex: 'idnum', sortable: false},
            {text: '移动电话', dataIndex: 'mobilePhone', sortable: false},
            {text: '序号', dataIndex: 'userorder', sortable: false},
            {text: '是否有效', dataIndex: 'isVaild', sortable: false},
            {text: '用户类别', dataIndex: 'userType', sortable: false},
            {text: '邮箱', dataIndex: 'email', sortable: false}
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
        ]},
        {xtype: 'pagingtoolbar',
            store: 'User',
            dock: 'bottom',
            pageSize: 10}
    ]
});