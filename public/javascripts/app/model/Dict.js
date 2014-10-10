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
        {name: "sibOrder", type: "int", defaultValue: 0},
        {name: "isleaf", type: "string", defaultValue: 'Y'},
        {name: "maintFlag", type: "int", defaultValue: 0},
        {name: "dictType", type: "string", defaultValue: '01'},
        {name: "dictSimplePin", type: "string"},
        {name: "dictAllPin", type: "string"},
        {name: "dictItemTableName", type: "string"},
        {name: "dictVersion", type: "string", defaultValue: '1'},
        {name: "createTime", type: "date", convert: function (v, record) {
            return new Date(v.millis)
        }}

    ]
})
