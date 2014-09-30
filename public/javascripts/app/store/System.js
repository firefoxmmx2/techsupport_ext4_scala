/**
 * Created by hooxin on 14-4-2.
 */
/**
 * 系统存储
 */
Ext.define("Techsupport.store.SystemStore", {
    extend: "Ext.data.Store",
    model: "Techsupport.model.System",
    proxy: {
        type: "rest",
        url: "/api/systems",
        reader: {
            type: "json",
            root: "data",
            totalProperty: 'total',
            successProperty: 'success'
        }
    },
    listeners:{
        beforeload: function (store, oper, options) {
            if(store.sorters && store.sorters.getCount()){
                var sorter = store.sorters.getAt(0);
                var obj={};
                obj[store.getProxy().sortParam]=sorter.property;
                obj[store.getProxy().directionParam]=sorter.direction;
                Ext.apply(store.getProxy().extraParams, obj);
            }
        }
    },
    remoteSort:true,
    sorters:['nodeorder']
});
