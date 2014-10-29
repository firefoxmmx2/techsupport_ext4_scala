/**
 * 功能
 */
Ext.define('Techsupport.model.Function', {
    extend: 'Ext.data.Model',
    idProperty: 'id',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'systemcode', type: 'string'},
        {name: 'funcname', type: 'string'},
        {name: 'funcdefine', type: 'string'},
        {name: 'functype', type: 'string', defaultValue: '0'}
    ]
})
