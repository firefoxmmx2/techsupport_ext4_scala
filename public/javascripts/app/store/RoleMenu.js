/**
 * 角色菜单
 */
Ext.define('Techsupport.store.RoleMenu', {
    extend: 'Techsupport.store.Basic',
    model: 'Techsupport.model.RoleMenu',
    proxy: {
        type: 'ajax',
        url: '/api/roleMenus',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
})