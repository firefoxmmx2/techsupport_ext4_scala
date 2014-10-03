/**
 * Created by hooxin on 14-4-1.
 */
Ext.define("Techsupport.model.System", {
    extend: "Ext.data.Model",
    idProperty: "id",
    fields: [
        {name: "id", type: "string"},
        {name: "systemname", type: "string"},
        {name: "systemdefine", type: "string"},
        {name: "picturepath", type: "string"},
        {name: "parentsystemcode", type: "string"},
        {name: "nodeorder", type: "int"},
        {name: "isleaf", type: "string"},
        {name: "fullcode", type: "string"},
        {name: "parentFullcode", type: "string"}
    ]
});
