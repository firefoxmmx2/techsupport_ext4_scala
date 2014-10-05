/**
 * 机构管理
 */
Ext.define('Techsupport.view.sysadmin.department.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.departmentManage',
    initComponent: function () {
        this.callParent(arguments);
        this.down('panel[region=west]').add({xtype: 'departmenttree'});
        this.down('panel[region=center]').add({xtype: 'departmentlist'});
        this.down('form').add([
            {xtype: 'textfield', fieldLabel: '机构代码', name: 'departcode'},
            {xtype: 'textfield', fieldLabel: '机构名称', name: 'departname'},
            {xtype: 'textfield', fieldLabel: '上级机构ID', name: 'parentDepartid', hidden: true}
        ])
    }
});
