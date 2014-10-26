/**
 * 角色存储
 */
Ext.define("Techsupport.store.Role", {
    extend: "Techsupport.store.Basic",
    model: 'Techsupport.model.Role',
    proxy: {
        type: 'rest',
        url: '/api/roles',
        reader: {
            type: 'json',
            root: 'data'
        }
    },
    sorters: ['id']
})
