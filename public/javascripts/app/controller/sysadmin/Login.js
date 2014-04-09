/**
 * Created by hooxin on 14-2-20.
 */
Ext.define('Techsupport.controller.sysadmin.Login', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.Login'],
    models: ['User'],
    refs: [
        {ref: 'loginForm', selector: 'login form'}
    ],
    init: function () {
        this.control({
            'login button[action=login]': {
                click: function () {
                    this.login();
                }
            },
            'login button[action=reset]': {
                click: function () {
                    this.getLoginForm().reset();
                }
            },
            'login textfield': {
                keydown:function(){
                    alert(1);
                },
                keyup:function(){
                    alert(2)
                }
            }
        })
    },
    login: function () {
        var form = this.getLoginForm();
        var url = "/login";
        if (form.getForm().isValid) {
            form.submit({
                url: url,
                params: form.getForm().getValues(),
                onSuccess: function (result, request) {
                    var res = Ext.decode(result.responseText);
                    if (res.result == 0) {
                        Ext.Msg.alert("提示", "登录成功");
                    }
                    else
                        Ext.Msg.alert("提示", "登录失败");
                }
            })
        }

    }
});