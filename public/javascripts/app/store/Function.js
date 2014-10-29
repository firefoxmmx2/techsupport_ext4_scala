/**
 *  功能存储
 */
Ext.define('Techsupport.store.Function', {
    extend: 'Techsupport.store.Basic',
    proxy: {
        type: 'ajax',
        url: '/api/functions',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
})