/**
 * Created by hooxin on 14-9-13.
 */
Ext.define('Techsupport.store.DictItem', {
    extend: 'Techsupport.store.Basic',
    model: 'Techsupport.model.DictItem',
    proxy: {
        type: 'rest',
        url: '/api/dictitems',
        reader: {
            type: "json",
            root: "data",
            successProperty: 'success',
            totalProperty: 'total'
        }
    },
    sorters: ['sibOrder']
});