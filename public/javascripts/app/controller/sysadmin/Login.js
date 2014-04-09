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
            'viewport login': {
                afterrender: function (gp) {
                    gp.down('button[action=login]').on('click', function () {
                        this.login();
                    }, this)
                    gp.down('button[action=reset]').on('click', function () {
                        this.getLoginForm().reset();
                    }, this)
                }
            },
            'login > form': {
                afterrender: function (gp) {
                    var me = this;
                    gp.query('textfield').forEach(function (el, idx, items) {
                        el.on('specialkey', function (o,e) {
                            if(e.ENTER == e.getKey()){
                                this.login();
                            }
                        }, me)
                    })
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