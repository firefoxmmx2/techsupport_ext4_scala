Ext.define('Techsupport.view.sysadmin.department.Manage', {
    extend: 'Techsupport.view.sysadmin.user.Manage',
    alias: 'widget.departmentManage',
    initComponent: function (d) {
        this.callParent(arguments);
        this.query('panel[region=center]').map(function (p) {
            p.removeAll();
            p.add({xtype:'departmentlist'});
        });
        this.query('form').map(function (f) {
            f.removeAll();
            f.add([
                {xtype:'textfield',fieldLabel:'机构代码',name:'departcode'},
                {xtype:'textfield',fieldLabel:'机构名称',name:'departname'},
                {xtype:'textfield',fieldLabel:'上级机构ID',name:'parentDepartid',hidden:true}
            ])
        });
    }
})
