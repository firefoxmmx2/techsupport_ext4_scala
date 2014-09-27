/**
 * 菜单新增
 */
Ext.define('Techsupport.view.menu.Add',{
    extend:'Techsupport.view.base.BaseDetail',
    initComponent: function () {
        this.callParent(arguments);
        this.query('form panel').map(function (p) {
            p.add([
                {xtype:'textfield',fieldLabel:'菜单代码'},
                {xtype:'textfield',fieldLabel:'菜单名称'},
                {xtype:'textfield',fieldLabel:'菜单地址'},
                {xtype:'textfield',fieldLabel:'菜单'},
                {xtype:'textfield',fieldLabel:'父菜单代码'},
                {xtype:'textfield',fieldLabel:'菜单全码'},
                {xtype:'textfield',fieldLabel:'排序号'}
            ]);
        });
    }
});