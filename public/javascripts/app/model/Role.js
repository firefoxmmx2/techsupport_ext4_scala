/**
 * 角色
 */
Ext.define("Techsupport.model.Role", {
    extend: "Ext.data.Model",
    idProperty: "id",
    fields: [
        {name: "id", type: "int"},
        {name: "rolename", type: "string"},
        {name: "roleDescription", type: "string"},
        {name: "roleType", type: "string"},
        {name: "jzlbdm", type: "string"},
        {name: "jzlbmc", type: "string"},
        {name: "departid", type: "int"}
    ]
});
