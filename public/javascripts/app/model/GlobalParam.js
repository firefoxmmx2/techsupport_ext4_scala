/**
 * Created by hooxin on 14-4-1.
 */
Ext.define("Techsupport.model.GlobalParam", {
    extend: "Ext.data.Model",
    idProperty: "id",
    fields: [
        {name: "id", type: "string"},
        {name: "globalparname", type: "string"},
        {name: "globalparvalue", type: "string"}
    ]
})
