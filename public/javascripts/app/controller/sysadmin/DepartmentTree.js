/**
 * Created by hooxin on 14-9-18.
 */
Ext.define('Techsupport.controller.sysadmin.DepartmentTree', {
    extend: 'Ext.app.Controller',
    stores: ['DepartmentTree'],
    views: ['sysadmin.department.DepartmentTree'],
    init: function () {
        this.control({
            'departmenttree': {
                beforeitemexpand: function (n, opts) {
                    var tree = n.getOwnerTree();
                    tree.cdata.departcode = n.raw.departcode;
                    tree.cdata.departid = n.raw.id;
                    tree.cdata.departname = n.raw.text;
                    tree.cdata.departlevel = n.raw.departlevel;
                    tree.cdata.departfullcode = n.raw.departfullcode;
                },
                render: function (t) {
                    t.cdata = {departid: 0, departcode: "", departname: "", departfullcode: '', departlevel: 1};
                },
                afterrender: function (t, opts) {
                    t.getRootNode().expand();
                },
                beforeload: function (store, operation, opts) {
                    var tree = store.getRootNode().getOwnerTree();
                    if (!tree.cdata.departid) {
                        tree.cdata.departid = tree.getRootNode().id;
                        tree.cdata.departcode = tree.getRootNode().departcode;
                        tree.cdata.departname = tree.getRootNode().text;
                        tree.cdata.departlevel = tree.getRootNode().departlevel;
                        tree.cdata.departfullcode = tree.getRootNode().departfullcode;
                    }
                    store.getProxy().setExtraParam("parentDepartid", tree.cdata.departid);
                },
                itemclick: function (v, record, item, index, e, eOpts) {
                    var tree = v.ownerCt;
                    tree.cdata.departid = record.raw.id;
                    tree.cdata.departcode = record.raw.departcode;
                    tree.cdata.departname = record.raw.text;
                    tree.cdata.departfullcode = record.raw.departfullcode;
                    tree.cdata.departlevel = record.raw.departlevel;
                },
                itemcontextmenu: function (view, record, item, index, e, eOpts) {
                    //右键菜单
                    e.preventDefault();
                    e.stopEvent();          //屏蔽浏览器右键菜单

                    var menu = Ext.create('Ext.menu.Menu', {
                        floating: true,
                        items: [
                            {text: '刷新', handler: function () {
                                var tree = view.ownerCt;
                                var selectedNode=tree.getSelectionModel().selected.items[0];
                                tree.getStore().load({
                                    node: tree.getRootNode(),
                                    callback: function () {
                                        tree.expandPath(selectedNode.getPath("id"),"id");
                                    }
                                });
                            }}
                        ]
                    });
                    menu.showAt(e.getXY()); //在点击处显示
                }
            }
        });
    }
});