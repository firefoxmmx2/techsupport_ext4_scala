/**
 * 角色管理
 */
Ext.define('Techsupport.view.sysadmin.role.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.rolemanage',
    initComponent: function () {
        this.items = Ext.clone(this.superclass.items)
        //去掉左边的面板
        this.items.splice(0, 1)
        this.callParent(arguments)
        //添加角色列表
        this.down("panel[region=center]").add(
            {xtype: 'rolelist'}
        )
        //添加查询字段
        this.down("panel[region=center] form").add([
            {xtype: 'textfield', name: 'rolename', fieldLabel: "角色名称"},
            {xtype: 'textfield', name: 'roleDescription', fieldLabel: "角色描述"},
            {
                xtype: 'combobox', name: 'roleType', fieldLabel: "角色类型",
                valueField: 'value',
                displayField: 'text',
                emptyText: '请选择',
                queryMode: 'local',
                triggerAction: 'all'
            }
        ])
        //添加关联菜单和关联功能
        this.down("panel[region=center] toolbar[dock=top][ui=footer]")
            .add([
                '-',
                {xtype: 'button', text: '关联菜单', action: 'relateMenu'},
                {xtype: 'button', text: '关联功能', action: 'relateFunction'}
            ])
    }
})