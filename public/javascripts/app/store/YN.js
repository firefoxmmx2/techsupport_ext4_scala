/**
 * Created by hooxin on 14-5-9.
 */
Ext.define('Techsupport.store.YN', {
    extend: 'Ext.data.Store',
    fields: ['value', 'text'],
    data: [
        {value: '', text: '&nbsp;'},
        {value: 'Y', text: '是'},
        {value: 'N', text: '否'}
    ]
});
