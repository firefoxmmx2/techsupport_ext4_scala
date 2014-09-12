/**
 * Created by hooxin on 14-5-22.
 */
Ext.define('Techsupport.view.sysadmin.department.DepartmentTree', {
    extend: 'Ext.tree.Panel',
    store: 'DepartmentTree',
    border: true,
    alias: 'widget.departmenttree',
    listeners:{
        beforeitemexpand: function (n, opts) {
            var tree = n.getOwnerTree();
            tree.cdata.departcode = n.raw.departcode;
            tree.cdata.departid = n.raw.id;
            tree.cdata.departname= n.raw.text;
        },
        render: function (t) {
            t.cdata = {departid: 0, departcode: "",departname:""};
        },
        afterrender: function (t, opts) {
            t.getRootNode().expand();
        },
        beforeload: function (store, operation, opts) {
            var tree = store.getRootNode().getOwnerTree();
            if (!tree.cdata.departid) {
                alert(tree.getRootNode().text);
                tree.cdata.departid = tree.getRootNode().id;
                tree.cdata.departcode = tree.getRootNode().departcode;
                tree.cdata.departname=tree.getRootNode().text;
            }
            store.getProxy().setExtraParam("parentDepartid", tree.cdata.departid);
        },
        itemclick: function (v, record, item, index, e, eOpts) {
            var tree = v.ownerCt;
            tree.cdata.departid = record.raw.id;
            tree.cdata.departcode = record.raw.departcode;
            tree.cdata.departname=record.raw.text;
        }
    }
});