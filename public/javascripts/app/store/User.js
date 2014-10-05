/**
 * Created by hooxin on 14-4-2.
 */
Ext.define("Techsupport.store.User", {
    extend: "Techsupport.store.Basic",
    model: "Techsupport.model.User",
    proxy: {
        type: "rest",
        url: "/api/users",
        reader: {
            type: "json",
            root: "datas",
            totalProperty: 'total',
            successProperty: 'success'
        }
    },
    sorters: ['userorder']
});
