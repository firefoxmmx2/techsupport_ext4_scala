/**
 * Created by hooxin on 14-2-20.
 */
Ext.define('Techsupport.views.sysadmin.Login', {
    extend: 'Ext.window.Window',
    title: '技术支持单用户登录',
    layout: 'vbox',
    items: [
        {xtype: 'image', width: 200, height: 300},
        {xtype: 'form', items: [
            {xtype: 'textfield', label: '帐号', name: 'useraccount', maxLength: 20, blank: false},
            {xtype: 'textfield', label: '密码', name: 'password', maxLength: 20, blank: false}
        ]}
    ],
    buttons: [
        {xtype: 'button', label: '确定', handle: function () {
            Ext.example.msg('hello');
        }},
        '-',
        {xtype: 'button', label: '关闭', handle: function () {
            Ext.example.msg('close');
        }}
    ]
})