/**
 * Created by hooxin on 14-10-10.
 */
/**
 * 字典项树形列表
 */
Ext.define('Techsupport.view.sysadmin.dictItem.DictItemTreeList', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.dictItemTreeList',
    selType: 'checkboxmodel',
    selModel: {
        flex: 0,
        showHeaderCheckbox: true,
        width: 16
    },
    loadMask: true,
    useArrows: true,
    rootVisible: false,
    animate: true,
    columns: [
        { xtype: 'treecolumn', text: '显示值', dataIndex: 'displayName' },
        {text: '实际值', dataIndex: 'factValue', flex: 1 },
        {text: '附加值', dataIndex: 'appendValue', flex: 1 },
        {text: '序列', dataIndex: 'sibOrder', flex: 1 },
        {text: '显示标志', dataIndex: 'displayFlag', flex: 1},
        {text: '是否可用', dataIndex: 'isValid', flex: 1},
        {text: '简拼', dataIndex: 'itemSimplePin', flex: 1},
        {text: '全拼', dataIndex: 'itemAllPin', flex: 1}
    ],
    initComponent: function () {
        this.store = Ext.create('Techsupport.store.DictItemTree',{autoLoad:false})
        this.callParent(arguments)
    }
})