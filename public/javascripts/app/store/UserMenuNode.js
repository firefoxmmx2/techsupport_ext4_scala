/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.store.UserMenuNode', {
    extend: "Ext.data.TreeStore",
    fields: ['id', 'leaf', 'text', 'funcentry'],
    idProperty: 'id',
    proxy: {
        type: 'ajax',
        method: 'GET',
        url: "/menu/userMenu",
        reader: {
            type: "json",
            root: "root"
        }
    }
})

