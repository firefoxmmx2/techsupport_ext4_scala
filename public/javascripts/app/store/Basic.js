/**
 * Created by hooxin on 14-10-5.
 */
Ext.define('Techsupport.store.Basic', {
    extend: 'Ext.data.Store',
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
    },
    remoteSort: true,
    sorters: 'nodeorder'
})
