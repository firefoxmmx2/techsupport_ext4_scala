/**
 * Created by hooxin on 14-5-8.
 */
Ext.define('Techsupport.view.sysadmin.user.List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.user.list',
    store: 'User',
    loadMask: true,
    columns: [
        {header: 'ID', dataIndex: 'id', sortable: false},
        {header: '帐号', dataIndex: 'useraccount', sortable: false},
        {header: '姓名', dataIndex: 'username', sortable: false},
        {header: '密码', dataIndex: 'password', sortable: false},
        {header: '身份证', dataIndex: 'idnum', sortable: false},
        {header: '移动电话', dataIndex: 'mobilePhone', sortable: false},
        {header: '序号', dataIndex: 'userorder', sortable: false},
        {header: '是否有效', dataIndex: 'isVaild', sortable: false},
        {header: '用户类别', dataIndex: 'userType', sortable: false},
        {header: '邮箱', dataIndex: 'email', sortable: false}
    ],
    dockedItems: [
        {xtype: 'toolbar', dock: 'top', ui: 'footer', items: [
            {text: '添加', xtype: 'button', action: 'add'} ,
            '-',
            {xtype: 'button', text: '删除', action: 'remove'}
        ]},
        {xtype: 'toolbar', dock: 'top', items: [
            {xtype: 'textfield', name: 'useraccount', fieldLabel: '帐号'},
            {xtype: 'textfield', name: "username", fieldLabel: '用户姓名'},
            {xtype: 'combobox',
                store: 'YN',
                queryMode: 'local',
                displayField: 'text',
                valueField: 'value',
                fieldLabel: '是否可用',
                editable: false
            } ,
            {xtype: 'button', text: '查询', action: 'query'}
        ]},
        {xtype: 'pagingtoolbar',
            store: 'User',
            dock: 'bottom',
            displayInfo: true}
    ]
});