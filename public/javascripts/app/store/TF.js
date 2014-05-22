/**
 * Created by hooxin on 14-5-22.
 */

Ext.define('Techsupport.store.TF', {
    extend: 'Ext.data.Store',
    fields: ['value', 'text'],
    data: [
        {value: true, text: '是'},
        {value: false, text: '否'}
    ]
});
