/**
 * 菜单列表
 */
Ext.define('Techsupport.view.sysadmin.menu.List', {
    extend: 'Ext.grid.Panel',
    store: 'Menu',
    selType: 'checkboxmodel',
    selModel: {
        flex: 0,
        showHeaderCheckbox: true,
        width: 16
    },
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
    dockedItems: [
        {xtype: 'pagingtoolbar',
            store: 'Menu',
            dock: 'bottom',
            pageSize: 10,
            listeners: {
                afterrender: function (p, eOpts) {
                    p.query('button:last').map(function (b) {
                        p.remove(b);
                    });
                    p.query('tbseparator:last').map(function (s) {
                        p.remove(s);
                    })
                }
            }
        }
    ]
});