/**
 * Created by hooxin on 14-5-22.
 */
Ext.define('Techsupport.view.sysadmin.department.List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.departmentlist',
    store: 'Department',
    loadMask: true,
    selType: 'checkboxmodel',
    selModel: {
        flex: 0,
        showHeaderCheckbox: true,
        width: 16
    },
    columns: {
        items: [
            {text: 'ID', dataIndex: 'id',flex:1},
            {text: '机构代码', dataIndex: 'departcode',flex:1},
            {text: '机构名称', dataIndex: 'departname',flex:1},
            {text: '机构级别', dataIndex: 'departlevel',flex:1},
            {text: '机构全码', dataIndex: 'departfullcode',flex:1},
            {text: '序号', dataIndex: 'nodeOrder',flex:1},
            {text: '是否有下级', dataIndex: 'isLeaf',flex:1},
            {text: '机构简拼', dataIndex: 'departsimplepin',flex:1},
            {text: '机构全拼', dataIndex: 'departallpin',flex:1},
            {text: '机构省级代码', dataIndex: 'departbrevitycode',flex:1}
        ]
    },
    dockedItems: [
        {xtype: 'pagingtoolbar',
            store: 'Department',
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
