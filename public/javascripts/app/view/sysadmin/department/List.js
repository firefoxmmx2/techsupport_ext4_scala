/**
 * Created by hooxin on 14-5-22.
 */
Ext.define('Techsupport.view.sysadmin.department.List', {
    extend: 'Techsupport.view.base.BaseList',
    alias: 'widget.departmentlist',
    store: 'Department',
    loadMask: true,
    columns: {
        items: [
            {text: 'ID', dataIndex: 'id', flex: 1},
            {text: '机构代码', dataIndex: 'departcode', flex: 1},
            {text: '机构名称', dataIndex: 'departname', flex: 1},
            {text: '机构级别', dataIndex: 'departlevel', flex: 1},
            {text: '机构全码', dataIndex: 'departfullcode', flex: 1},
            {text: '序号', dataIndex: 'nodeOrder', flex: 1},
            {text: '是否有下级', dataIndex: 'isLeaf', flex: 1, renderer: function (value, metaData, record) {
                return record.raw.isLeaf == "Y" ? "否" : "是";
            }},
            {text: '机构简拼', dataIndex: 'departsimplepin', flex: 1},
            {text: '机构全拼', dataIndex: 'departallpin', flex: 1},
            {text: '机构省级代码', dataIndex: 'departbrevitycode', flex: 1}
        ]
    },
    initComponent: function () {
        Ext.apply(this.dockedItems,this.superclass.dockedItems)
        this.dockedItems[0].store="Department"
        this.callParent(arguments)
    }
});
