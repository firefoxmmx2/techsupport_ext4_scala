/**
 * Created by hooxin on 14-5-8.
 */
/**
 * 用户列表
 */
Ext.define('Techsupport.view.sysadmin.user.List', {
    extend: 'Techsupport.view.base.BaseList',
    alias: 'widget.userlist',
    store: 'User',
    columns: {
        items: [
            {text: 'ID', dataIndex: 'id', sortable: false, flex: 1},
            {text: '帐号', dataIndex: 'useraccount', sortable: false, flex: 1},
            {text: '姓名', dataIndex: 'username', sortable: false, flex: 1},
            {text: '密码', dataIndex: 'password', sortable: false, flex: 1},
            {text: '身份证', dataIndex: 'idnum', sortable: false, flex: 1},
            {text: '移动电话', dataIndex: 'mobilePhone', sortable: false, flex: 1},
            {text: '序号', dataIndex: 'userorder', sortable: false, flex: 1},
            {text: '是否有效', dataIndex: 'isVaild', sortable: false, flex: 1},
            {text: '用户类别', dataIndex: 'userType', sortable: false, flex: 1},
            {text: '邮箱', dataIndex: 'email', sortable: false, flex: 1}
        ],
        defaults: {
        }
    }
});