/**
 * Created by hooxin on 14-4-1.
 */
Ext.define("Techsupport.store.DepartmentStore", {
    extend: "Ext.data.Store",
    model:"Techsupport.model.Department",
    proxy:{
        type:"ajax",
        url:"/api/departments.json",
        method:"GET",
        reader:{
            type:"json",
            root:"data"
        }
    }
})