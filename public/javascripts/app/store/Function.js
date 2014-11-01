/**
 *  功能存储
 */
Ext.define('Techsupport.store.Function', {
    extend: 'Techsupport.store.Basic',
    model: 'Techsupport.model.Function',
    proxy: {
        type: 'rest',
        url: '/api/functions',
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success',
            totalProperty: 'total'
        }
    },
    sorters: ['id']
})