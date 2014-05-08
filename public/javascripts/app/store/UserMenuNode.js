/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.store.UserMenuNode', {
    extend: "Ext.data.TreeStore",
    fields: ['id', 'leaf', 'text', 'funcentry', 'parentId'],
    idProperty: 'id',
    root: {
        id: "0",
        text: "根节点",
        expanded: false
    },
    proxy: {
        type: 'ajax',
        method: 'GET',
        url: "/api/menus/userMenuNode.json",
        reader: {
            type: "json",
            root: "root"
        }
    }
})

