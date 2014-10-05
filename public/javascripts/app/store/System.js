/**
 * Created by hooxin on 14-4-2.
 */
/**
 * 系统存储
 */
Ext.define("Techsupport.store.System", {
    extend: "Techsupport.store.Basic",
    model: "Techsupport.model.System",
    proxy: {
        type: "rest",
        url: "/api/systems",
        reader: {
            type: "json",
            root: "data",
            totalProperty: 'total',
            successProperty: 'success'
        }
    },
    sorters: ['nodeorder']
});
