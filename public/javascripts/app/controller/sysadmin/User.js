/**
 * Created by hooxin on 14-5-12.
 */
Ext.define('Techsupport.controller.sysadmin.User', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.user.Add', 'sysadmin.user.List','sysadmin.user.Manage'],
    stores: ['User', 'YN'],
    refs:[
        {ref:'queryForm',selector:'panel toolbar[dock=top] form'}
    ],
    init: function () {
        this.control({
            'usermanage > panel':{
                afterrender: function (p, opts) {
                    var height = p.ownerCt.ownerCt.body.getHeight() -
                        p.ownerCt.bodyPadding * 2;
                }
            },
            'userlist': {
                afterrender: function (g, eOpts) {
//                    var gridHeight = g.ownerCt.ownerCt.body.getHeight() -
//                        g.ownerCt.bodyPadding * 2 - g.getEl().getMargin('tb');
//                    g.setHeight(gridHeight);
//
//                    var dockedItemsHeight = g.getDockedItems("[id!=headercontainer]").map(function (x) {
//                        return x.getHeight();
//                    }).reduce(function (x, y) {
//                        return x + y;
//                    });
//                    var headerHeight = g.headerCt.down('[id*=gridcolumn]').getHeight();
//                    var pagesize = Math.floor((gridHeight - dockedItemsHeight) / headerHeight);
//                    g.getStore().pageSize = pagesize;
//                    g.getStore().trailingBufferZone = pagesize;
//                    g.getStore().getProxy().setExtraParam('limit', pagesize);
                },
                render: function (g) {
                    g.getStore().removeAll();
                }
            },
            'usermanage button[action=add]': {
//                添加按钮
                click: function () {

                }
            },
            'usermanage button[action=remove]': {
//                删除按钮
                click: function () {
                }
            },
            'usermanage button[action=query]': {
//                查询按钮
                click: function () {
                    this.getUserStore().load({
                        params: this.getQueryForm().getForm().getValues()
                    });
                }
            }
        });
    }
})
