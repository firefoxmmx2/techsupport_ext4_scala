/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.store.Menu',{
    extend:"Ext.data.Store",
    model:'Techsupport.model.Menu',
    proxy:{
        type:'ajax',
        method:'GET',
        url: "/menu/list",
        reader: {
            type: "json",
            root: "data"
        }
    }
})
