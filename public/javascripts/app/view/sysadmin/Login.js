/**
 * Created by hooxin on 14-2-20.
 */
Ext.define('Techsupport.view.sysadmin.Login', {
    extend: 'Ext.window.Window',
    alias: 'widget.login',
    closable: false,
    width: 500,
    height: 300,
    modal: true,
    title: '技术支持单用户登录',
    layout: 'border',
    items: [
        {xtype: 'image', region: 'west', split: false, width: 160, height: 200, src: 'assets/images/favicon.png'},
        {xtype: 'form', region: 'center', defaults: {margin: "5 5 0 15%", labelWidth: "25%"}, items: [
            {xtype: 'textfield', fieldLabel: '帐号', name: 'useraccount', maxLength: 20, allowBlank: false, anchor: "90%"},
            {xtype: 'textfield', inputType: "password", fieldLabel: '密码', name: 'password', maxLength: 20,
                allowBlank: false, anchor: "90%"},
            {xtype: 'panel', border: false, margin: '0 0 0 0', html: "<hr />"}
        ]}
    ],
    buttons: [
        {action: "login", xtype: 'button', text: '确定'},
        '-',
        {action: "reset", xtype: 'button', text: '重置'}
    ]
});