/**
 * Created by hooxin on 14-2-20.
 */
Ext.define('Techsupport.controller.sysadmin.Login', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.Login'],
    models: ['Login'],
    init: function () {
        console.log('Initialized Users! This happens before the Application launch function is called');
        this.control({
            'viewport > panel': {
                afterrender: function (p) {
                    p.down('button[action=login]').on('click', function () {
                        alert(1);
                    }, this);
                }
            }
        })
    }
});