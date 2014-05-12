/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.controller.Main', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.SystemMenu', 'Main', 'Copyright', 'THeader'],
    stores: ['UserMenuNode'],
    refs: [
        {ref: 'viewport', selector: 'viewport'}
    ],
    init: function () {
//        身份验证
        var loginController = this.getController('sysadmin.Login');
        loginController.heartCheck();

        this.control({
            'panel > tabpanel': {
                beforeadd: function (tp, component, index, eOpts) {
                    if (tp.queryById(component.getId())) {
                        return false;
                    }
                    return true;
                }
            }
        });
    }
})
