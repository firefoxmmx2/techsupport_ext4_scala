/**
 * Created by hooxin on 14-5-8.
 */
Ext.define('Techsupport.view.sysadmin.user.List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.userlist',
    store: 'User',
    loadMask: true,
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
        {xtype: 'pagingtoolbar',
            store: 'User',
            dock: 'bottom',
            pageSize: 10,
            listeners:{
                afterrender: function (p, eOpts) {
                    p.query('button:last').map(function (b) {
                        p.remove(b);
                    });
                    p.query('tbseparator:last').map(function (s) {
                        p.remove(s);
                    })
                }
            }
        }
    ]
});