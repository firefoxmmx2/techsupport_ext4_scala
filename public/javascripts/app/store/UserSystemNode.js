/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.store.UserSystemNode', {
    extend: "Ext.data.Store",
    model:"Techsupport.model.System",
    proxy: {
        type: 'ajax',
        method: 'GET',
        url: "/api/systems/userSystemNode.json",
        reader: {
            type: "json",
            root: "systems"
        }
    }
})
