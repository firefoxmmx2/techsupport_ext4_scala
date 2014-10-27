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
        {name: "roleType", type: "string", defaultValue: '0'},
        {name: "jzlbdm", type: "string"},
        {name: "jzlbmc", type: "string"},
        {name: "departid", type: "int", defaultValue: 0},
        {name: "departname", type: 'string', defaultValue: ''}
    ]
});
