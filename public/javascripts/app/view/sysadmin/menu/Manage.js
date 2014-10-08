/**
 * 菜单管理
 */
Ext.define('Techsupport.view.sysadmin.menu.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.menumanage',
    initComponent: function () {
        this.callParent(arguments);
        this.down('panel[region=west]').add({xtype: 'menutree'});
        this.down('panel[region=center]').add({xtype: 'menulist'});
        this.down('form').add([
            {xtype: 'textfield', fieldLabel: '菜单代码', name: 'id'},
            {xtype: 'textfield', fieldLabel: '菜单名称', name: 'menuname'},
            {xtype: 'textfield', fieldLabel: '菜单内容', name: 'funcentry'},
            {xtype: 'textfield', fieldLabel: '父菜单代码', name: 'parentmenucode', readOnly: true, hidden: true}
        ]);
    }
});