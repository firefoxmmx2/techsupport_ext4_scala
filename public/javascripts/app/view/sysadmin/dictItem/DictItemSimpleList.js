/**
 * Created by hooxin on 14-10-10.
 */
/**
 * 字典项简单列表
 */
Ext.define('Techsupport.view.sysadmin.dictItem.DictItemSimpleList', {
    extend: 'Techsupport.view.base.BaseList',
    alias: 'widget.dictItemSimpleList',
    columns: [
        { text: '显示值', dataIndex: 'displayName', flex: 1 },
        {text: '实际值', dataIndex: 'factValue', flex: 1 },
        {text: '附加值', dataIndex: 'appendValue', flex: 1 },
        {text: '序列', dataIndex: 'sibOrder', flex: 0.7 },
        {text: '显示标志', dataIndex: 'displayFlag', flex: 0.7,
            renderer: function (v) {
                var store = Ext.data.StoreManager.lookup('DictItemDisplayFlag')
                var r = store.findRecord('value', v)
                if (r) {
                    return r.data.text
                }
                else
                    return v
            }},
        {text: '是否可用', dataIndex: 'isValid', flex: 0.7,
            renderer: function (v) {
                var store = Ext.data.StoreManager.lookup('OneZero')
                var r = store.findRecord('value', v)
                if (r)
                    return r.data.text
                else
                    return v
            }
        },
        {text: '简拼', dataIndex: 'itemSimplePin', flex: 1},
        {text: '全拼', dataIndex: 'itemAllPin', flex: 1}
    ],
    initComponent: function () {
        this.store = Ext.create('Techsupport.store.DictItem')
        this.callParent(arguments)
    }
})