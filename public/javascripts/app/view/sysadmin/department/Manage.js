/**
 * 机构管理
 */
Ext.define('Techsupport.view.sysadmin.department.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.departmentManage',
    initComponent: function () {
        this.callParent(arguments);
        this.query('panel[region=west]').map(function (p) {
            p.add({xtype: 'departmenttree'});
        });
        this.query('panel[region=center]').map(function (p) {
            p.add({xtype: 'departmentlist'});
        });
        this.query('form').map(function (f) {
            f.add([
                {xtype: 'textfield', fieldLabel: '机构代码', name: 'departcode'},
                {xtype: 'textfield', fieldLabel: '机构名称', name: 'departname'},
                {xtype: 'textfield', fieldLabel: '上级机构ID', name: 'parentDepartid', hidden: true}
            ])
        });
    }
});
