/**
 * Created by hooxin on 14-4-1.
 */
Ext.define("Techsupport.store.Department", {
    extend: "Ext.data.Store",
    model: "Techsupport.model.Department",
    proxy: {
        type: "rest",
        url:'/api/departments',
        reader: {
            type: "json",
            root: "data",
            successProperty:"success",
            totalProperty:'total'
        }
    },
    listeners: {
        beforeload: function (store, operation, eOpts) {
            if (store.sorters && store.sorters.getCount()) {
                var sorter = store.sorters.getAt(0);
                var obj={};
                obj[store.getProxy().sortParam]=sorter.property;
                obj[store.getProxy().directionParam]=sorter.direction;
                Ext.apply(store.getProxy().extraParams, obj);
            }
        }
    },
    remoteSort:true,
    sorters:['nodeOrder']
});
