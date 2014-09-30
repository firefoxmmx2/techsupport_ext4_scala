/**
 * Created by hooxin on 14-9-30.
 */
Ext.define('Techsupport.model.MenuTree',{
    extend:'Ext.data.Model',
    idProperty:'id',
    fields:[
        {name:'id',type:'string'},
        {name:'text',type:'string'},
        {name:'menuname',type:'string'},
        {name:'menulevel',type:'int'},
        {name:'parentId',type:'string'},
        {name:'menufullcode',type:'string'},
        {name:'leaf',type:'boolean'},
        {name:'systemcode',type:'string'}
    ]
});
