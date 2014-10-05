/**
 * Created by hooxin on 14-9-17.
 */
Ext.define('Techsupport.view.sysadmin.department.Edit', {
//    extend:'Techsupport.view.base.BaseDetail',
    extend: 'Techsupport.view.sysadmin.department.Add',
    alias: 'widget.departmentedit',
    title: '机构修改',
    initComponent: function () {
        this.callParent(arguments);
        this.down('form panel').insert(0, {
            xtype: 'textfield', name: 'id', fieldLabel: '用户ID', allowBlank: false, blankText: '用户ID不能为空', readOnly: true
        });
    }
});
