/**
 * Created by hooxin on 14-2-20.
 */
Ext.define('Techsupport.controller.sysadmin.Login', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.Login'],
    stores: ['User'],
    models: ['User'],
    refs: [
        {ref: 'loginForm', selector: 'Login form'}
    ],
    init: function () {

        this.control({
            'Login button[action=login]': {
                click: function () {
                    alert(1);
                }
            }
        })
    },
    login: function () {
        var form = this.getLoginForm();
        var store = this.getUserStore();
        var url = "/user";
        form.submit({
            url:url
        })
    }
});