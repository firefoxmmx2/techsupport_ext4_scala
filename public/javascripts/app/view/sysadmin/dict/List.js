/**
 * Created by hooxin on 14-10-8.
 */
/**
 * 字典列表
 */
Ext.define('Techsupport.view.sysadmin.dict.List', {
    extend: 'Techsupport.view.base.BaseList',
    alias: 'widget.dictList',
    store: 'Dict',
    columns: [
        {text: '字典ID', dataIndex: 'id', flex: 1},
        {text: '字典代码', dataIndex: 'dictcode', flex: 1},
        {text: '字典名称', dataIndex: 'dictname', flex: 1},
        {text: '序列', dataIndex: 'sibOrder', flex: 1},
        {text: '维护标志', dataIndex: 'maintFlag', flex: 1,
            renderer: function (v, record) {
                if (Ext.isNumber(v)) {
                    var store = Ext.data.StoreManager.lookup('DictMaintFlag')
                    var r = store.findRecord('value', v)
                    if (r)
                        return r.data.text
                    else
                        return v
                }
                else
                    return v
            }

        },
        {text: '字典类型', dataIndex: 'dictType', flex: 1,
            renderer: function (v, record) {
                if (v) {
                    var store = Ext.data.StoreManager.lookup('DictType')
                    var r = store.findRecord('value', v)
                    if (r)
                        return r.data.text
                    else
                        return v
                }
                return v
            }
        },
        {text: '字典简拼', dataIndex: 'dictSimplePin', flex: 1},
        {text: '字典全拼', dataIndex: 'dictAllPin', flex: 1},
        {text: '字典版本', dataIndex: 'dictVersion', flex: 1},
        {text: '创建时间', dataIndex: 'createTime', flex: 1, xtype: 'datecolumn', format: 'Y-m-d H:i:s'}
    ]
})