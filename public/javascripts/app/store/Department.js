/**
 * Created by hooxin on 14-4-1.
 */
Ext.define("Techsupport.store.DepartmentStore", {
    extend: "Ext.data.Store",
    model: "Techsupport.model.Department",
    proxy: {
        type: "rest",
        url:'/api/departments',
        reader: {
            type: "json",
            root: "data"
        }
    }
})
