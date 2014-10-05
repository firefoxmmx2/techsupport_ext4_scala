/**
 * Created by hooxin on 14-10-5.
 */
Ext.define('Techsupport.view.sysadmin.globalParam.List',{
    extend:'Techsupport.view.base.BaseList',
    store:'GlobalParam',
    columns:[
        {name:'id',dataIndex:'id',flex:1},
        {name:'globalparname',dataIndex:'globalparname',flex:1},
        {name:'globalparvalue',dataIndex:'globalparvalue',flex:1}
    ]
})