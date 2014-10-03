Ext.define('Techsupport.model.User', {
    extend: 'Ext.data.Model',
    idProperty: "id",
    fields: [
        {name: 'id', type: 'int'},
        {name: 'useraccount', type: 'string'},
        {name: 'username', type: 'string'},
        {name: 'password', type: 'string'},
        {name: 'idnum', type: 'string'},
        {name: 'departid', type: 'int'},
        {name: 'mobilePhone', type: 'string'},
        {name: 'userorder', type: 'int'},
        {name: 'isValid', type: 'string'},
        {name: 'userType', type: 'string'},
        {name: 'jzlbdm', type: 'string'},
        {name: 'jzlbmc', type: 'string'},
        {name: 'email', type: 'string'},
        {name: 'departname', type: 'string'},
        {name: 'password2', type: 'string'}
    ]
});
