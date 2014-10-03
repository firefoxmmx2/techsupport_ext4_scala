/**
 * 机构树模型
 */
Ext.define('Techsupport.model.DepartmentTree', {
    extend: 'Ext.data.Model',
    idProperty: 'id',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'text', type: 'string'},
        {name: 'parentId', type: 'int'},
        {name: 'departcode', type: 'string'},
        {name: 'departlevel', type: 'int'},
        {name: 'departfullcode', type: 'string'},
        {name: 'leaf', type: 'boolean'}
    ]
});