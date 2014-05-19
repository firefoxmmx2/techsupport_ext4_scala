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
                    g.setHeight(g.ownerCt.ownerCt.body.getHeight() -
                        g.ownerCt.bodyPadding * 2 - g.getEl().getMargin('tb'));

                    var dockedItemsHeight = g.getDockedItems().filter(function (x) {
                        if (x.id.indexOf("headerHeight") == -1)
                            return x;
                    }).map(function (x) {
                        return x.getHeight();
                    }).reduce(function (x, y) {
                        return x + y;
                    });
                    alert(dockedItemsHeight)
                    var headerHeight = g.getDockedItems('[id*=headercontainer]').map(function (x) {
                        return x.getHeight();
                    }).reduce(function (x, y) {
                        return x + y;
                    }, 0);
                    alert(g.getHeight())
                    var pagesize = (g.getHeight() - dockedItemsHeight) / headerHeight;
                    alert("pagesize = " + pagesize + ", g.getHeight() - dockedItemsHeight  = " + (g.getHeight() - dockedItemsHeight));
                    g.getStore().pageSize = pagesize;
                    g.getStore().trailingBufferZone = pagesize;
                    g.getStore().getProxy().setExtraParam('limit', pagesize);
                }
            }
        });
    }
})
