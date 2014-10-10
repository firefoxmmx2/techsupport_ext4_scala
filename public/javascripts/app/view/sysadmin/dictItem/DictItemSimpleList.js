/**
 * Created by hooxin on 14-10-10.
 */
/**
 * 字典项简单列表
 */
Ext.define('Techsupport.view.sysadmin.dictItem.DictItemSimpleList', {
    extend: 'Techsupport.view.base.BaseList',
    alias: 'widget.dictItemSimplePanel',
    store: 'DictItem',
    columns: [
        { text: '显示值', dataIndex: 'displayName', flex: 1 },
        {text: '实际值', dataIndex: 'factValue', flex: 1 },
        {text: '附加值', dataIndex: 'appendValue', flex: 1 },
        {text: '序列', dataIndex: 'sibOrder', flex: 1 },
        {text: '显示标志', dataIndex: 'displayFlag', flex: 1},
        {text: '是否可用', dataIndex: 'isValid', flex: 1},
        {text: '简拼', dataIndex: 'itemSimplePin', flex: 1},
        {text: '全拼', dataIndex: 'itemAllPin', flex: 1}
    ]
})