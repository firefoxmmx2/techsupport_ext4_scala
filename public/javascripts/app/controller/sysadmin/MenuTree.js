/**
 * 菜单树控制器
 */
Ext.define('Techsupport.controller.sysadmin.MenuTree',{
    extend:'Ext.app.Controller',
    stores:['MenuTree'],
    views:['MenuTree'],
    models:['Menu'],
    refs:[],
    init: function () {

        this.control({
            'menutree': {
                beforeitemexpand: function (n, opts) {
                    var tree = n.getOwnerTree();
                    tree.cdata.menucode = n.raw.id;
                    tree.cdata.menuname = n.raw.menuname;
                    tree.cdata.menulevel = n.raw.menulevel;
                    tree.cdata.systemcode = n.raw.systemcode;
                    tree.cdata.menufullcode= n.raw.menufullcode;
                },
                render: function (tree) {
                    tree.cdata = {menucode: 0, menuname: "", systemcode: '', menulevel: 0,menufullcode:''};

                    tree.refresh= function (nodeId) {
                        //刷新
                        var node=this.getStore().getNodeById(nodeId);
                        this.getStore().load({
                            node:node
                        });
                    };
                },
                afterrender: function (t, opts) {
                    t.getRootNode().expand();
                },
                beforeload: function (store, operation, opts) {
                    var tree = store.getRootNode().getOwnerTree();
                    if (!tree.cdata.menucode) {
                        tree.cdata.menucode = tree.getRootNode().id;
                        tree.cdata.menuname = tree.getRootNode().menuname;
                        tree.cdata.menulevel = tree.getRootNode().menulevel;
                        tree.cdata.menufullcode = tree.getRootNode().menufullcode;
                    }
                    store.getProxy().setExtraParam("parentmenucode", tree.cdata.menucode);
                },
                itemclick: function (v, record, item, index, e, eOpts) {
                    var tree = v.ownerCt;
                    tree.cdata.menucode = record.raw.id;
                    tree.cdata.menuname = record.raw.text;
                    tree.cdata.menulevel = record.raw.menulevel;
                    tree.cdata.menufullcode = record.raw.menufullcode;
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
                                tree.refresh(record.data.id);
                            }}
                        ]
                    });
                    menu.showAt(e.getXY()); //在点击处显示
                }
            }

        });
    }
});