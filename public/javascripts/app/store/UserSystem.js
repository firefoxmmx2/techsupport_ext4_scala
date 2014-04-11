/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.store.UserSystem',{
    extend:"Ext.data.Store",
    model:'Techsupport.model.System',
    proxy:{
        type:'ajax',
        method:'GET',
        url: "/system/list",
        reader: {
            type: "json",
            root: "data"
        }
    }
})
