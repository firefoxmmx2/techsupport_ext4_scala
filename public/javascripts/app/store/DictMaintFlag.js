/**
 * Created by hooxin on 14-10-10.
 */
/**
 * 字典维护标志
 */
Ext.define('Techsupport.store.DictMaintFlag', {
    extend: 'Ext.data.Store',
    fields: ['text', 'value'],
    data: [
        {text: '维护', value: 0},
        {text: '停止维护', value: 1}
    ]
})
