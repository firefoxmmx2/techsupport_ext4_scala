/**
 * Created by hooxin on 14-10-2.
 */
/**
 * 系统数据列表
 */
Ext.define('Techsupport.view.sysadmin.system.List', {
    extend: 'Techsupport.view.base.BaseList',
    alias: 'widget.systemlist',
    store: 'System',
    columns: {
        items: [
            {text: '系统代码', dataIndex: 'id', flex: 1},
            {text: '系统名称', dataIndex: 'systemname', flex: 1},
            {text: '系统描述', dataIndex: 'systemdefine', flex: 1},
            {text: '图片路径', dataIndex: 'picturepath', flex: 1},
            {text: '排序号', dataIndex: 'nodeorder', flex: 1},
            {text: '是否有下级', dataIndex: 'isleaf', flex: 1, renderer: function (value, metaData, record) {
                return record.data.isleaf == "Y" ? "否" : "是";
            }},
            {text: '系统全码', dataIndex: 'fullcode', flex: 1}
        ]
    },
    initComponent: function () {
        Ext.apply(this.dockedItems,this.superclass.dockedItems)
        this.dockedItems[0].store="System"
        this.callParent(arguments)
    }
});
