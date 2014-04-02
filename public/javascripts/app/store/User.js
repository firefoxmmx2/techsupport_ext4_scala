/**
 * Created by hooxin on 14-4-2.
 */
Ext.define("Techsupport.store.UserStore", {
    extend: "Ext.data.Store",
    model: "Techsupport.model.User",
    proxy: {
        type: "ajax",
        url: "/user/list",
        method: "GET",
        reader: {
            type: "json",
            root: "data"
        }
    }
})
