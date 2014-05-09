/**
 * Created by hooxin on 14-5-8.
 */
Ext.define('Techsupport.view.sysadmin.user.List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.user.List',
    store: 'User',
    title: '用户',
    loadMask: true,
    columns: [
        {header: 'id', dataIndex: 'id'},
        {header: '帐号', text: '帐号', dataIndex: 'useraccount'},
        {header: '姓名', text: '姓名', dataIndex: 'username'},
        { header: '密码', text: '密码', dataIndex: 'password'},
        { header: '身份证', text: '身份证', dataIndex: 'idnum'},
        {header: '移动电话', text: '移动电话', dataIndex: 'mobilePhone'},
        {header: '序号', text: '序号', dataIndex: 'userorder'},
        { header: '是否有效', text: '是否有效', dataIndex: 'isVaild'},
        {header: '用户类别', text: '用户类别', dataIndex: 'userType'},
        { header: '邮箱', text: '邮箱', dataIndex: 'email'}
    ],
    init:function(){
        alert(1);
    }
    initComponent: function () {
        alert(2);
        this.store.load();
        this.callParent(arguments)
    },
    dockedItems: [
        {xtype: 'toolbar', dock: 'top', ui: 'footer', items: [
            {text: '添加', xtype: 'button', action: 'add'} ,
            '-',
            {xtype: 'button', text: '删除', action: 'remove'}
        ]},
//        { xtype: 'toolbar', items: [
//            {layout: 'column', items: [
//                {layout: 'form', items: [
//                    {xtype: 'textfield', name: 'useraccount', fieldLabel: '帐号'}
//                ]},
//                {layout: 'form', items: [
//                    {xtype: 'textfield', name: "username", fieldLabel: '用户姓名'}
//                ]},
//                {layout: 'form', items: [
//                    {xtype: 'combobox',
//                        store: 'yn',
//                        queryMode: 'local',
//                        displayField: 'text',
//                        valueField: 'value',
//                        fieldLabel: '是否可用'
//                    }
//                ]},
//                '-',
//                {xtype: 'button', text: '查询', action: 'query'}
//            ]}
//        ]},
        '-'
    ]
});