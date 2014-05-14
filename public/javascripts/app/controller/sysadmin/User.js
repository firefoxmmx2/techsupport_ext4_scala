/**
 * Created by hooxin on 14-5-12.
 */
Ext.define('Techsupport.controller.sysadmin.User', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.user.Add', 'sysadmin.user.List'],
    stores: ['User', 'YN'],
//    refs:[
//        {ref:'userlist',selector:''}
//    ],
    init: function () {
        this.control({
            'gridpanel': {
                afterrender: function (g, eOpts) {
                    g.setHeight(g.ownerCt.ownerCt.body.getHeight() -
                        g.ownerCt.bodyPadding * 2 - g.getEl().getMargin('tb'));
                }
            }
        });
    }
})
