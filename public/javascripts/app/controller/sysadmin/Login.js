/**
 * Created by hooxin on 14-2-20.
 */
Ext.define('Techsupport.controller.sysadmin.Login', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.Login'],
    models: ['User'],
    stores: ['User'],
    refs: [
        {ref: 'loginForm', selector: 'login form'}
    ],
    init: function () {
        this.control({
            'login button[action=login]': {
                click: function () {
                    if (this.getLoginForm().isValid()) {
                        this.login();
                    }

                }
            },
            'login button[action=reset]': {
                click: function () {

                }
            }
        })
    },
    login: function () {
        var form = this.getLoginForm();
        var store = this.getUserStore();
        var url = "/user";
        form.submit({
            url: url
        })
    }
});