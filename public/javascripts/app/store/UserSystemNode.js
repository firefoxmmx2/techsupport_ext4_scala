/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.store.UserSystemNode', {
    extend: "Ext.data.Store",
    fields: ['id', 'text'],
    proxy: {
        type: 'ajax',
        method: 'GET',
        url: "/system/userSystemNode",
        reader: {
            type: "json",
            root: "data"
        }
    }
})
