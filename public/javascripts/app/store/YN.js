/**
 * Created by hooxin on 14-5-9.
 */
Ext.define('Techsupport.store.YN', {
    extend: 'Ext.data.Store',
    fields: ['value', 'text'],
    data: [
        {value: '1', text: '是'},
        {value: '0', text: '否'}
    ]
});
