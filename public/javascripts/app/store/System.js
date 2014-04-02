/**
 * Created by hooxin on 14-4-2.
 */
Ext.define("Techsupport.store.SystemStore", {
    extend: "Ext.data.Store",
    model: "Techsupport.model.System",
    proxy: {
        type: "ajax",
        method: "GET",
        url: "/system/list",
        reader: {
            type: "json",
            root: "data"
        }
    }
})
