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
                    var dockedHeight = g.getDockedItems().map(function (x) {
                        return x.getHeight();
                    }).reduce(function (x, y) {
                        return x + y;
                    });

                    g.setHeight(g.ownerCt.ownerCt.body.getHeight()
                        - dockedHeight - g.getEl().getMargin('tb'));
                }
            }
        });
    }
})
