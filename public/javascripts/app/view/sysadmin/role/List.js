/**
 * 角色列表
 */
Ext.define('Techsupport.view.sysadmin.role.List',{
    extend:'Techsupport.view.base.BaseList',
    alias:'widget.rolelist',
    initComponent: function () {
        this.store='Role'
        this.callParent(arguments)
    }
})
