/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.store.Menu', {
    extend: "Ext.data.Store",
    model: 'Techsupport.model.Menu',
    proxy: {
        type: 'rest',
        url: "/api/menus",
        reader: {
            type: "json",
            root: "data",
            successProperty: 'success',
            totalProperty: 'total'
        }
    }
})
