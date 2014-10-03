/**
 * Created by hooxin on 14-9-13.
 */
Ext.define('Techsupport.store.DictItem', {
    extend: 'Ext.data.Store',
    model: 'Techsupport.model.DictItem',
    proxy: {
        type: 'rest',
        url: '/api/dictitems',
        reader: {
            type: "json",
            root: "data",
            successProperty: 'success',
            totalProperty: 'total'
        },
        listeners: {
            beforeload: function (store, operation, eOpts) {
                if (store.sorters && store.sorters.getCount()) {
                    var sorter = store.sorters.getAt(0);
                    var obj = {};
                    obj[store.getProxy().sortParam] = sorter.property;
                    obj[store.getProxy().directionParam] = sorter.direction;
                    Ext.apply(store.getProxy().extraParams, obj);
                }
            }
        }
    },
    remoteSort: true,
    sorters: ['sibOrder']
});