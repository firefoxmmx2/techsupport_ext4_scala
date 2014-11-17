/**
 * 角色类型
 */
Ext.define('Techsupport.store.RoleType',{
    extend:'Ext.data.Store',
    fields: ['text', 'value'],
    data: [
        {text: '管理角色', value: '0'},
        {text: '普通角色', value: '1'}
    ]
})
