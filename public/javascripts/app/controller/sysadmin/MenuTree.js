/**
 * 菜单树控制器
 */
Ext.define('Techsupport.controller.sysadmin.MenuTree', {
    extend: 'Ext.app.Controller',
    stores: ['MenuTree'],
    views: ['sysadmin.menu.MenuTree'],
    models: ['MenuTree'],
    refs: [],
    init: function () {

        this.control({
            'menutree': {
                beforeitemexpand: function (n, opts) {
                    var tree = n.getOwnerTree();
                    tree.cdata.menucode = n.raw.id;
                    tree.cdata.menuname = n.raw.menuname;
                    tree.cdata.menulevel = n.raw.menulevel;
                    tree.cdata.systemcode = n.raw.systemcode;
                    tree.cdata.menufullcode = n.raw.menufullcode;
                },
                render: function (tree) {
                    tree.cdata = {menucode: 0, menuname: "", systemcode: '', menulevel: 0, menufullcode: ''};

                    tree.refresh = function (nodeId,callback) {
                        //刷新
                        var node = this.getStore().getNodeById(nodeId);
                        this.getStore().load({
                            node: node,
                            callback:callback
                        });
                    };
                },
                afterrender: function (t, opts) {
                    t.getRootNode().expand();
                },
                beforeload: function (store, operation, opts) {
                    var tree = store.getRootNode().getOwnerTree();
                    if (!tree.cdata.menucode) {
                        tree.cdata.menucode = tree.getRootNode().raw.id;
                        tree.cdata.menuname = tree.getRootNode().raw.menuname;
                        tree.cdata.menulevel = tree.getRootNode().raw.menulevel;
                        tree.cdata.menufullcode = tree.getRootNode().raw.menufullcode;
                        tree.cdata.systemcode = tree.getRootNode().raw.systemcode
                    }
                    store.getProxy().setExtraParam("parentmenucode", tree.cdata.menucode);
                },
                //itemclick: function (v, record, item, index, e, eOpts) {
                //    var tree = v.ownerCt;
                //    tree.cdata.menucode = record.raw.id;
                //    tree.cdata.menuname = record.raw.text;
                //    tree.cdata.menulevel = record.raw.menulevel;
                //    tree.cdata.menufullcode = record.raw.menufullcode;
                //    tree.cdata.systemcode = record.raw.systemcode;
                //},
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
                },
                select: function (model, record, index, eOpts) {
                    var tree = model.treeStore.ownerTree
                    tree.cdata.menucode = record.raw.id;
                    tree.cdata.menuname = record.raw.text;
                    tree.cdata.menulevel = record.raw.menulevel;
                    tree.cdata.menufullcode = record.raw.menufullcode;
                    tree.cdata.systemcode = record.raw.systemcode;

                    //当选择模式为多选的时候启用
                    if(tree.getSelectionModel().getSelectionMode() == 'MULTI'){
                        var node = tree.getStore().getNodeById(record.data.id)
                        if(!record.data.leaf){
                            if(node.data.expandable){
                                node.expand(false, function () {
                                    tree.getSelectionModel().select(node.childNodes,true)
                                })
                            }
                        }
                        else {
                            tree.getSelectionModel().select(node.parentNode,true,true)
                        }
                    }
                },
                deselect: function (model, record, index, eOpts) {
                    var tree=model.treeStore.ownerTree
                    var node=tree.getStore().getNodeById(record.data.id)
                    if(tree.getSelectionModel().getSelectionMode() == 'MULTI'){
                        if(!record.data.leaf){
                            if(node.hasChildNodes()){
                                tree.getSelectionModel().deselect(node.childNodes)
                            }
                        }
                        else{
                            //当子菜单不再被选中的时候,也清理掉父菜单的选中状态.
                            if(node.parentNode ){
                                var selectedResults=Ext.Array.map(node.parentNode.childNodes, function (r) {
                                    return !tree.getSelectionModel().isSelected(r)
                                })
                                var result=true;
                                Ext.Array.each(selectedResults, function (r) {
                                    result=result && r
                                })
                                if(result)
                                    tree.getSelectionModel().deselect(node.parentNode)
                            }
                        }
                    }
                }
            }

        });
    }
});