/**
 * Created by hooxin on 14-10-10.
 */
/**
 * 字典类型
 */
Ext.define('Techsupport.store.DictType',{
    extend:'Ext.data.Store',
    fields: ['text', 'value'],
    data: [
        {text: '简单', value: '01'},
        {text: '树形', value: '02'}
    ]
})