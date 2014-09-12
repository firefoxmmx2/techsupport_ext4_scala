/**
 * Created by hooxin on 14-5-8.
 */
Ext.define('Techsupport.view.sysadmin.user.Add', {
    extend: 'Ext.window.Window',
    title: '新增用户',
    width: 630,
    alias: 'widget.useradd',
    bodyPadding: 2,
    items: [
        {xtype: 'form',
            layout: 'column',
            defaults: {
                xtype: 'textfield',
                bodyPadding: 2,
                margin: {right: 10, left: 5, top: 2, bottom: 2}
            },
            items: [
                {fieldLabel: '帐号', name: 'useraccount', allowBlank: false, blankText: '不能为空'},
                {fieldLabel: '密码', name: 'password', inputType: 'password',
                    allowBlank: false, blankText: '不能为空',
                    maxLength: 16, maxLengthText: '最长16位'},
                {fieldLabel: '重复密码', name: 'password2', inputType: 'password',
                    inputType: 'password', allowBlank: false, blankText: '不能为空',
                    maxLength: 16, maxLengthText: '最长16位'},
                {fieldLabel: '用户名称', name: 'username', allowBlank: false, blankText: '不能为空'},
                {fieldLabel: '身份证', name: 'idnum'},
                {fieldLabel: '邮箱', name: 'email'},
                {fieldLabel: '手机', name: 'mobilePhone'},
                {fieldLabel: '序号', name: 'userorder'},
                {fieldLabel: '所属机构', name: 'departname', allowBlank: false, blankText: '不能为空', readOnly: true},
                {fieldLabel: '所属机构id', name: 'departid', allowBlank: false, blankText: '不能为空', hidden: true},
                {fieldLabel: '用户类别', name: 'usertype', xtype: 'checkboxgroup', columns: 2, vertical: true, items: [
                    { boxLabel: 'Item 1', name: 'rb', inputValue: '1' },
                    { boxLabel: 'Item 2', name: 'rb', inputValue: '2', checked: true },
                    { boxLabel: 'Item 3', name: 'rb', inputValue: '3' },
                    { boxLabel: 'Item 4', name: 'rb', inputValue: '4' },
                    { boxLabel: 'Item 5', name: 'rb', inputValue: '5' },
                    { boxLabel: 'Item 6', name: 'rb', inputValue: '6' }
                ]}
            ]}
    ],
    buttons: [
        {xtype: 'button', text: '确定', action: 'enter'},
        {xtype: 'button', text: '取消', action: 'cancel'}
    ]
});