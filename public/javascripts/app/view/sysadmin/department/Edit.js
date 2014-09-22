/**
 * Created by hooxin on 14-9-17.
 */
Ext.define('Techsupport.view.sysadmin.department.Edit',{
//    extend:'Techsupport.view.base.BaseDetail',
    extend:'Techsupport.view.sysadmin.department.Add',
    alias:'widget.departmentedit',
    title:'机构修改',
    initComponent: function () {
        this.callParent(arguments);
        this.query('form panel').map(function (p) {
            p.insert(0, {
                xtype: 'textfield', name: 'id', fieldLabel: '用户ID', allowBlank: false, blankText: '用户ID不能为空',readOnly:true
            });
//            p.add({fieldLabel: '机构代码', name: 'departcode', allowBlank: false, blankText: '机构代码不能为空',vtype:'alphanum'},
//            {fieldLabel: '机构名称', name: 'departname', allowBlank: false, blankText: '机构名称不能为空'},
//            {fieldLabel: '机构级别', name: 'departlevel', allowBlank: false, blankText: '机构级别不能为空',vtype:'number'},
//            {fieldLabel: '机构全码', name: 'departfullcode', allowBlank: false, blankText: '机构全码不能为空',readOnly:true,vtype:'alphanum'},
//            {fieldLabel: '上级机构id', name: 'parentDepartid', allowBlank: false, blankText: '上级机构id不能为空', hidden: true,readOnly:true,vtype:'number'},
//            {fieldLabel: '上级机构名称', name: 'parentDepartname', allowBlank: false, blankText: '上级机构名称不能为空',readOnly:true},
//            {fieldLabel: '序号', name: 'nodeOrder', allowBlank: false, blankText: '序号不能为空',vtype:'number'},
//            {fieldLabel: '是否为下级', name: 'isLeaf', allowBlank: false, blankText: '不能为空', hidden: true, xtype: 'combobox',readOnly:true,
//                store: 'YN',
//                queryMode: 'local',
//                displayField: 'text',
//                valueField: 'value',
//                value: 'Y'});
        });
    }
});
