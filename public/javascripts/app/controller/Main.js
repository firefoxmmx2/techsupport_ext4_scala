/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.controller.Main', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.SystemMenu', 'Main'],
    stores: ['UserMenuNode'],
    init: function () {
        var loginController = this.getController('sysadmin.Login');
        loginController.heartCheck();
    }
})
