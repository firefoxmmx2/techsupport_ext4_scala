/**
 * Created by hooxin on 14-5-22.
 */
Ext.define('Techsupport.view.sysadmin.department.Add',{
    extend:'window',
    title:'机构新增',
    layout:'form',
    alias:'widget.departmentadd',
    defaults:{
        xtype:'textfield'
    },
    items:[
        {fieldLabel:'机构代码',name:'departcode',allowBlank:false,blankText:'机构代码不能为空'},
        {fieldLabel:'机构名称',name:'departname',allowBlank:false,blankText:'机构名称不能为空'},
        {fieldLabel:'机构级别',name:'departlevel',allowBlank:false,blankText:'机构级别不能为空'},
        {fieldLabel:'机构全码',name:'departfullcode',allowBlank:false,blankText:'机构全码不能为空'},
        {fieldLabel:'上级机构id',name:'parentDepartid',allowBlank:false,blankText:'上级机构id不能为空',hidden:true},
        {fieldLabel:'上级机构名称',name:'parentDepartname',allowBlank:false,blankText:'上级机构名称不能为空'},
//        {fieldLabel:'省级机构编码',name:'parentDepartname',allowBlank:false,blankText:'上级机构名称不能为空'},
        {fieldLabel:'序号',name:'nodeOrder',allowBlank:false,blankText:'序号不能为空'}
    ],
    buttons:[
        {xtype:'button',text:'确定',action:'enter'},
        {xtype:'button',text:'取消',action:'cancel'}
    ]
})