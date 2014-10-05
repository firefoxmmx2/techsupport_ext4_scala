/**
 * Created by hooxin on 14-4-1.
 */
Ext.define("Techsupport.store.Department", {
    extend: "Techsupport.store.Basic",
    model: "Techsupport.model.Department",
    proxy: {
        type: "rest",
        url: '/api/departments',
        reader: {
            type: "json",
            root: "data",
            successProperty: "success",
            totalProperty: 'total'
        }
    },

    sorters: ['nodeOrder']
});
