/**
 *  角色功能
 */
Ext.define('Techsupport.model.RoleFunc', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'roloid', type: 'int'},
        {name: 'funccode', type: 'string'}
    ],
    belongsTo: 'Function'
})