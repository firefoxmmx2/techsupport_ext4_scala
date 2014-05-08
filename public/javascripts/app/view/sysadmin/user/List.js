/**
 * Created by hooxin on 14-5-8.
 */
Ext.define('Techsupport.view.sysadmin.user.List', {
    extend: 'Ext.grid.Panel',
    store: 'User',
    columns: {
        items: [
            {text: '帐号', dataIndex: 'useraccount'},
            {text: '名称', dataIndex: 'username'},
            {dataIndex: 'password', text: '密码'},
            {dataIndex: 'idnum', text: '身份证'},
            {dataIndex: 'mobilePhone', text: '移动电话'},
            {dataIndex: 'userorder', text: '序号'},
            {dataIndex: 'isVaild', text: '是否有效'},
            {dataIndex: 'userType', text: '用户类别'},
            {dataIndex: 'email', text: '邮箱'}
        ],
        defaults: {
            flex: 1
        }
    },
    dockedItems: [
        {xtype: 'toolbar', dock: 'top', items: [
            {text: '添加', xtype: 'button', action: 'add'} ,
            '-',
            {xtype: 'button', text: '删除', action: 'remove'}
        ]},
        {xtype: 'pagingtoolbar', dock: 'bottom', store: 'User', displayInfo: true}
    ]
});