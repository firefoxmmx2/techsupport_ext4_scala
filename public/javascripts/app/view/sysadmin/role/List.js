/**
 * 角色列表
 */
Ext.define('Techsupport.view.sysadmin.role.List', {
    extend: 'Techsupport.view.base.BaseList',
    initComponent: function () {
        this.alias='widget.rolelist'
        this.store = 'Role'
        this.columns = [
            {text: 'ID', dataIndex: 'id', flex: 1},
            {text: '角色名称', dataIndex: 'rolename', flex: 1},
            {text: '角色描述', dataIndex: 'roleDescription', flex: 1},
            {text: '角色类型', dataIndex: 'roleType', flex: 1},
            {text: '类别代码', dataIndex: 'jzlbdm', flex: 1},
            {text: '类别名称', dataIndex: 'jzlbmc', flex: 1}
        ]
        this.callParent(arguments)
    }
})
