/**
 * Created by hooxin on 14-9-17.
 */
Ext.define('Techsupport.view.sysadmin.department.Edit',{
    extend:'Techsupport.view.sysadmin.department.Add',
    alias:'widget.departmentedit',
    title:'机构修改',
    initComponent: function (w) {
        this.callParent(arguments);
        this.insert(0,{xtype:'textfield',filedLabel:'机构ID',name:'id',
            allowBlank:false,blankText:'机构ID不能为空'});
    }
});
