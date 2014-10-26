/**
 * 角色管理
 */
Ext.define('Techsupport.view.sysadmin.role.Manage',{
    extend:'Techsupport.view.base.BaseManage',
    initComponent: function () {
        Ext.clone(this.items,this.superclass.items)
        //去掉左边的面板
        this.items.splice(0,1)
        this.callParent(arguments)
        //添加角色列表
        this.down("panel[region=center]").add(
            {xtype:'rolelist'}
        )
        //添加查询字段
        this.down("panel[region=center] form").add([
            {xtype:'textfield',name:'rolename',fieldLabel:"角色名称"},
            {xtype:'textfield',name:'roleDescription',fieldLabel:"角色描述"}
        ])
    }
})