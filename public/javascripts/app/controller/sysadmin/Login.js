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
                    }, this);
                    gp.down('button[action=reset]').on('click', function () {
                        this.getLoginForm().reset();
                    }, this);
                }
            },
            'login > form': {
                afterrender: function (gp) {
                    var controller = this;
                    Ext.Array.map(gp.query('textfield'), function (el) {
                        el.on('specialkey', function (o, e) {
                            if (e.ENTER == e.getKey()) {
                                this.login();
                            }
                        }, controller);
                        return el
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
                waitMsg: '正在登录中...',
                success: function (form, action) {
                    var res = action.result;
                    if (res.result == 0) {
                        me.getApplication().authCode = res.authCode;
                        me.getApplication().userInfo = res.userInfo;
                        me.getViewport().removeAll();
                        me.getViewport().add({xtype: 'main'});
                    }
                    else
                        Ext.Msg.alert("提示", "登录失败");
                },
                failure: function (form, action) {
                    var res = action.result;
                    Ext.Msg.alert("错误", "错误代码:" + res.result + "; " + res.message);
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
                    if (func) {
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