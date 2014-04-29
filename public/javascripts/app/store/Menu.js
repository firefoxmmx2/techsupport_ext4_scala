/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.store.Menu',{
    extend:"Ext.data.Store",
    model:'Techsupport.model.Menu',
    proxy:{
        type:'ajax',
        method:'GET',
        url: "/api/menus.json",
        reader: {
            type: "json",
            root: "data"
        }
    }
})
