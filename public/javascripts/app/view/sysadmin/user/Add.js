/**
 * Created by hooxin on 14-5-8.
 */
Ext.define('Techsupport.view.sysadmin.user.Add', {
    extend: 'Ext.window.Window',
    title: '添加用户',
    layout: 'form',
    alias:'widget.useradd',
    defaults: {
        xtype: 'textfield'
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
        {fieldLabel: '身份证', name: 'idnum', allowBlank: false, blankText: '不能为空'},
        {fieldLabel: '邮箱', name: 'email'},
        {fieldLabel: '手机', name: 'mobilePhone'},
        {fieldLabel: '序号', name: 'userorder'}
    ]
});