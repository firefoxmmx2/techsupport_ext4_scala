/**
 * Created by hooxin on 14-2-20.
 */
Ext.define('Techsupport.view.sysadmin.Login', {
    extend: 'Ext.window.Window',
    alias: 'widget.login',
    closable: false,
    modal: true,
    title: '技术支持单用户登录',
    layout: 'hbox',
    items: [
        {xtype: 'image', width: 160, height: 200, src: 'assets/images/favicon.png'},
        {xtype: 'form',anchor:"100% 90%", items: [
            {xtype: 'textfield', fieldLabel: '帐号', name: 'useraccount', maxLength: 20, allowBlank: false, anchor: "25%"},
            {xtype: 'textfield', fieldLabel: '密码', name: 'password', maxLength: 20, allowBlank: false, anchor: "25%"}
        ]}
    ],
    buttons: [
        {action: "login", xtype: 'button', text: '确定'},
        '-',
        {action: "reset", xtype: 'button', text: '重置'}
    ]
});