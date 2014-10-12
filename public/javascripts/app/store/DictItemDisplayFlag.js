/**
 * Created by hooxin on 14-10-12.
 */
Ext.define('Techsupport.store.DictItemDisplayFlag', {
    extend: 'Ext.data.Store',
    fields: ['text', 'value'],
    data: [
        {text: '显示', value: '1'},
        {text: '不显示', value: '0'}
    ]
})
