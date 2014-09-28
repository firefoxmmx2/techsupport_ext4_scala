/**
 * 菜单详情
 */
Ext.define('Techsupport.view.menu.Detail',{
    extend:'Techsupport.view.base.BaseDetail',
    alias:'wigdet.menudetail',
    initComponent: function () {
        this.callParent(arguments);
        this.query('form panel').map(function (p) {
            p.add([
                {xtype:'textfield',fieldLabel:'菜单代码',name:'id'},
                {xtype:'textfield',fieldLabel:'菜单名称',name:'menuname'},
                {xtype:'textfield',fieldLabel:'菜单地址',name:'funcentry'},
                {xtype:'textfield',fieldLabel:'菜单级别',name:'menulevel'},
                {xtype:'textfield',fieldLabel:'父菜单代码',name:'parentmenucode'},
                {xtype:'textfield',fieldLabel:'菜单全码',name:'menufullcode'},
                {xtype:'textfield',fieldLabel:'排序号',name:'nodeorder'},
                {xtype:'textfield',fieldLabel:'是否为子菜单',name:'isleaf'},
                {xtype:'textfield',fieldLabel:'系统代码',name:'systemcode'}
            ]);
        });
    }
});