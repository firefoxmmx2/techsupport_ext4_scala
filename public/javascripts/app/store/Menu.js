/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.store.Menu', {
    extend: "Ext.data.Store",
    model: 'Techsupport.model.Menu',
    proxy: {
        type: 'rest',
        url: "/api/menus",
        reader: {
            type: "json",
            root: "data",
            successProperty: 'success',
            totalProperty: 'total'
        }
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
    },
    remoteSort: true,
    sorters: 'nodeorder'
});
