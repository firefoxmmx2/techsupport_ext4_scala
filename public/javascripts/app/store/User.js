/**
 * Created by hooxin on 14-4-2.
 */
Ext.define("Techsupport.store.User", {
    extend: "Ext.data.Store",
    model: "Techsupport.model.User",
    proxy: {
        type: "ajax",
        url: "/api/users.json",
        method: "GET",
        reader: {
            type: "json",
            root: "datas",
            totalProperty: 'total'
        }
    }
})
