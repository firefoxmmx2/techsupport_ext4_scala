/**
 * 菜单列表
 */
Ext.define('Techsupport.view.sysadmin.menu.List', {
    extend: 'Techsupport.view.base.BaseList',
    store: 'Menu',
    alias: 'widget.menulist',
    columns: {
        items: [
            {text: '菜单代码', dataIndex: 'id', flex: 1},
            {text: '菜单名称', dataIndex: 'menuname', flex: 1},
            {text: '菜单地址', dataIndex: 'funcentry', flex: 1},
//            {text:'菜单级别',dataIndex:'menulevel',flex:1},
//            {text:'父菜单代码',dataIndex:'parentmenucode',flex:1},
            {text: '菜单全码', dataIndex: 'menufullcode', flex: 1},
            {text: '排序号', dataIndex: 'nodeorder', flex: 1},
            {text: '是否有下级', dataIndex: 'isleaf', flex: 1, renderer: function (value, metaData, record) {
                return record.raw.isLeaf == "Y" ? "否" : "是";
            }},
            {text: '系统代码', dataIndex: 'systemcode', flex: 1}
        ]
    },
    initComponent: function () {
        Ext.apply(this.dockedItems,this.superclass.dockedItems)
        this.dockedItems[0].store="Menu"
        this.callParent(arguments)
    }
});