/**
 * Created by hooxin on 14-5-12.
 */
Ext.define('Techsupport.controller.sysadmin.User', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.user.Add', 'sysadmin.user.List'],
    stores: ['User', 'YN'],
    init: function () {
        this.control({
            'gridpanel': {
                afterrender: function (g, eOpts) {
                    var gridHeight = g.ownerCt.ownerCt.body.getHeight() -
                        g.ownerCt.bodyPadding * 2 - g.getEl().getMargin('tb');
                    g.setHeight(gridHeight);

                    var dockedItemsHeight = g.getDockedItems("[id!=headercontainer]").map(function (x) {
                        return x.getHeight();
                    }).reduce(function (x, y) {
                        return x + y;
                    });
                    var headerHeight = g.headerCt.down('[id*=gridcolumn]').getHeight();
                    var pagesize = Math.floor((gridHeight - dockedItemsHeight) / headerHeight);
                    g.getStore().pageSize = pagesize;
                    g.getStore().trailingBufferZone = pagesize;
                    g.getStore().getProxy().setExtraParam('limit', pagesize);
                }
            }
        });
    }
})
