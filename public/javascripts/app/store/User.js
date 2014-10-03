/**
 * Created by hooxin on 14-4-2.
 */
Ext.define("Techsupport.store.User", {
    extend: "Ext.data.Store",
    model: "Techsupport.model.User",
    proxy: {
        type: "rest",
        url: "/api/users",
        reader: {
            type: "json",
            root: "datas",
            totalProperty: 'total',
            successProperty: 'success'
        }
    },
    listeners: {
        beforeload: function (store, oper, options) {
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
    sorters: ['userorder']
});
