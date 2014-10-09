/**
 * Created by hooxin on 14-10-9.
 */
Ext.define("Techsupport.store.Dict", {
    extend: 'Techsupport.store.Basic',
    model: 'Techsupport.model.Dict',
    proxy: {
        type: 'rest',
        url: '/api/dicts',
        reader: {
            type: 'json',
            totalProperty: 'total',
            root: 'data'
        }
    },
    sorters: ['sibOrder']
})
