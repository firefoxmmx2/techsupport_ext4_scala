/**
 * Created by hooxin on 14-9-18.
 */
Ext.define('Techsupport.controller.DepartmentTree', {
    extend: 'Ext.app.Controller',
    stores:['DepartmentTree'],
    views: ['sysadmin.department.DepartmentTree'],
    init: function () {
        console.log("aaaaaaaaaaaaaa");
        this.control({
            'departmenttree': {
                beforeitemexpand: function (n, opts) {
                    var tree = n.getOwnerTree();
                    tree.cdata.departcode = n.raw.departcode;
                    tree.cdata.departid = n.raw.id;
                    tree.cdata.departname = n.raw.text;
                },
                render: function (t) {
                    alert(1)
                    t.cdata = {departid: 0, departcode: "", departname: ""};
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
                    }
                    store.getProxy().setExtraParam("parentDepartid", tree.cdata.departid);
                },
                itemclick: function (v, record, item, index, e, eOpts) {
                    var tree = v.ownerCt;
                    tree.cdata.departid = record.raw.id;
                    tree.cdata.departcode = record.raw.departcode;
                    tree.cdata.departname = record.raw.text;
                }
            }
        });
    }
});