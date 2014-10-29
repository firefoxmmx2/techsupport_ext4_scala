/**
 * 角色功能
 */
Ext.define('Techsupport.store.RoleFunc', {
    extend: 'Techsupport.store.Basic',
    model: 'Techsupport.model.RoleFunc',
    proxy: {
        type: 'rest',
        url: '/api/roleFuncs',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
})