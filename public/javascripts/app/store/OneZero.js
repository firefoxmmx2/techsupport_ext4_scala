/**
 * Created by hooxin on 14-9-11.
 */
Ext.define('Techsupport.store.OneZero', {
    extend: 'Ext.data.Store',
    fields: ['value', 'text'],
    data: [
        {value:null,text:'&nbsp;'},
        {value: '1', text: '是'},
        {value: '0', text: '否'}
    ]
});
