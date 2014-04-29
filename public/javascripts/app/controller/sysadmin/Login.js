/**
 * Created by hooxin on 14-2-20.
 */
Ext.define('Techsupport.controller.sysadmin.Login', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.Login'],
    models: ['User'],
    refs: [
        {ref: 'loginForm', selector: 'login form'},
        {ref: 'viewport', selector: 'viewport'}
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
                        el.on('specialkey', function (o, e) {
                            if (e.ENTER == e.getKey()) {
                                this.login();
                            }
                        }, me)
                    })
                }
            }
        })
    },
//    登录
    login: function () {
        var me = this;
        var form = this.getLoginForm();
        var url = "/login";
        if (form.getForm().isValid) {
            form.submit({
                url: url,
                params: form.getForm().getValues(),
                onSuccess: function (result, request) {
                    var res = Ext.decode(result.responseText);
                    if (res.result == 0) {
                        me.getApplication().authCode = res.authCode;
                        me.getApplication().userInfo = res.userInfo;
                        me.getViewport().removeAll();
                        me.getViewport().add({xtype: 'main'})
                    }
                    else
                        Ext.Msg.alert("提示", "登录失败");
                }
            })
        }

    },
//注销
    logout: function () {
        this.getApplication().authCode = null;
        this.getApplication().userInfo = null;
        var me = this;
        Ext.Ajax.request({
            url: "/logout",
            onSuccess: function (res) {
                var res = Ext.decode(res.responseText);
                if (res.result == 0) {
                    if (res.message)
                        Ext.Msg.alert("提示", res.message);
                    me.getApplication().removeAll();
                    me.getApplication().add({xtype: 'image', src: "assets/images/favicon.png"},
                        {xtype: 'login', autoShow: true});
                }
            }
        })
    },
//心跳验证
    heartCheck: function (func) {
        var me = this;

        Ext.Ajax.request({
            url: "/heartCheck",
            success: function (res) {
                var res = Ext.decode(res.responseText);
                if (res.result == 0) {
                    me.authCode = res.authCode;
                    me.userInfo = res.userInfo;
                    if(func){
                        func();
                    }
                }
                else {
                    var viewport = me.getViewport();
                    if (viewport.query('login').length == 0) {
                        viewport.removeAll();
                        viewport.add({xtype: 'image', src: "assets/images/favicon.png"},
                            {xtype: 'login', autoShow: true});
                    }

                }
            }
        })
    }
})
;