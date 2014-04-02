/**
 * Created by hooxin on 14-4-1.
 */
Ext.define("Techsupport.model.DictItem", {
    extend: "Ext.data.Model",
    idProperty: "id",
    fields: [
        {name: "id", type: "int"},
        {name: "dictcode", type: "string"},
        {name: "displayName", type: "string"},
        {name: "factValue", type: "string"},
        {name: "appendValue", type: "string"},
        {name: "superItemId", type: "int"},
        {name: "sibOrder", type: "int"},
        {name: "isleaf", type: "string"},
        {name: "displayFlag", type: "string"},
        {name: "isValid", type: "string"},
        {name: "itemSimplePin", type: "string"},
        {name: "itemAllPin", type: "string"}
    ]
})
