/**
 * Created by hooxin on 14-4-1.
 */
Ext.define("Techsupport.model.Dict", {
    extend: "Ext.data.Model",
    idProperty: "id",
    fields: [
        {name: "id", type: "int"},
        {name: "dictcode", type: "string"},
        {name: "dictname", type: "string"} ,
        {name: "superDictcode", type: "string"},
        {name: "sibOrder", type: "int"},
        {name: "isleaf", type: "string"},
        {name: "maintFlag", type: "int"},
        {name: "dictType", type: "string"},
        {name: "dictSimplePin", type: "string"},
        {name: "dictAllPin", type: "string"},
        {name: "dictItemTableName", type: "string"},
        {name: "dictVersion", type: "string"},
        {name: "createTime", type: "string"}

    ]
})
