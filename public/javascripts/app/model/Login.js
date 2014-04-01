/**
 * 登录实体
 */
Ext.define('Techsupport.model.Login', {
    extend: 'Ext.data.Model',
    idProperty: "useraccount",
    fields: [
        {name: 'useraccount', type: 'string'},
        {name: "password", type: 'string'}
    ],
    validations: [
        {field: 'useraccount', type: 'length', min: 1, max: 20},
        {field: 'password', type: 'length', min: 1, max: 40}
    ]
})