/**
 * Created by hooxin on 14-5-22.
 */
Ext.define('Techsupport.view.sysadmin.department.List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.department.list',
    store: 'Department',
    loadMask: true,
    columns: {
        items: [
            {text: 'ID', dataIndex: 'id'},
            {text: '机构代码', dataIndex: 'departcode'},
            {text: '机构名称', dataIndex: 'departname'},
            {text: '机构级别', dataIndex: 'departlevel'},
            {text: '机构全码', dataIndex: 'departfullcode'},
            {text: '序号', dataIndex: 'nodeOrder'},
            {text: '是否有下级', dataIndex: 'isLeaf'},
            {text: '机构简拼', dataIndex: 'departsimplepin'},
            {text: '机构全拼', dataIndex: 'departallpin'},
            {text: '机构省级代码', dataIndex: 'departbrevitycode'}
        ],
        defaults: {
            flex: 1
        }
    },
    dockedItems: [
        {xtype: 'pagingtoolbar',
            store: 'Department',
            dock: 'bottom',
            pageSize: 10}
    ]
});
