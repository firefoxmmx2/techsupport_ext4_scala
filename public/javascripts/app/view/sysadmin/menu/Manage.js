/**
 * 菜单管理
 */
Ext.define('Techsupport.view.sysadmin.menu.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.menumanage',
    initComponent: function () {
        this.callParent(arguments);
        this.query('panel[region=west]').map(function (p) {
            p.add({xtype: 'menutree'});
        });
        this.query('panel[region=center]').map(function (p) {
            p.add({xtype: 'menulist'});
        });
        this.query('buttongroup>form').map(function (p) {
            p.add([
                {xtype: 'textfield', fieldLabel: '菜单代码', name: 'id'},
                {xtype: 'textfield', fieldLabel: '菜单名称', name: 'menuname'},
                {xtype: 'textfield', fieldLabel: '菜单内容', name: 'funcentry'},
                {xtype: 'textfield', fieldLabel: '父菜单代码', name: 'parentmenucode', readOnly: true, hidden: true}
            ]);
        });
    }
});