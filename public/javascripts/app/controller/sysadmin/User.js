/**
 * Created by hooxin on 14-5-12.
 */
Ext.define('Techsupport.controller.sysadmin.User', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.user.Add', 'sysadmin.user.List', 'sysadmin.user.Manage',
        'sysadmin.department.DepartmentTree'],
    stores: ['User', 'YN', 'DepartmentTree'],
    refs: [
        {ref: 'queryForm', selector: 'panel toolbar[dock=top] form'},
        {ref: 'departmentTree', selector: 'departmenttree'}
    ],
    init: function () {
        this.control({
            'userlist': {
                afterlayout: function (g, layout, opts) {
                    var headerHeight = g.headerCt.down('[id*=gridcolumn]').getHeight();
                    var pagesize = Math.round(g.getHeight() / headerHeight);
                    g.getStore().pageSize = pagesize;
                    g.getStore().trailingBufferZone = pagesize;
                    g.getStore().getProxy().setExtraParam('limit', pagesize);
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
                    var params = this.getQueryForm().getForm().getValues();
                    params.departid = this.getDepartmentTree().cdata.departid;
                    this.getUserStore().load({
                        params: params
                    });
                }
            },
            'departmenttree': {
                beforeitemexpand: function (n, opts) {
                    var tree = n.getOwnerTree();
                    tree.cdata.departcode = n.raw.departcode;
                    tree.cdata.departid = n.raw.id;
                },
                render: function (t) {
                    t.cdata = {departid: "", departcode: ""};
                },
                afterrender: function (t, opts) {
                    t.getRootNode().expand();
                },
                beforeload: function (store, operation, opts) {
                    var tree = store.getRootNode().getOwnerTree();
                    if (!tree.cdata.departid) {
                        tree.cdata.departid = tree.getRootNode().id;
                        tree.cdata.departcode = tree.getRootNode().departcode;
                    }
                    store.getProxy().setExtraParam("parentDepartid", tree.cdata.departid);
                },
                itemclick: function (v, record, item, index, e, eOpts) {
                    var tree = v.ownerCt;
                    tree.cdata.departid = record.raw.id;
                    tree.cdata.departcode = record.raw.departcode;
                }
            }
        });
    }
})
