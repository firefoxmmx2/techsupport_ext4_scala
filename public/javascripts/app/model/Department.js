/**
 * Created by hooxin on 14-2-19.
 */
Ext.define('Techsupport.model.Department', {
    extend: 'Ext.data.Model',
    idProperty: "id",
    fields: [
        {name: 'id', type: 'int'},
        {name: 'departcode', type: 'string'},
        {name: 'departname', type: "string"},
        {name: "departlevel", type: 'int'},
        {name: 'departfullcode', type: 'string'},
        {name: 'parentDepartid', type: 'int'},
        {name: 'nodeOrder', type: 'int'},
        {name: 'isLeaf', type: 'string', defaultValue: 'Y'},
        {name: 'departsimplepin', type: 'string'},
        {name: 'departallpin', type: 'string'},
        {name: 'departbrevitycode', type: 'string'},
        {name: 'parentDepartname', type: 'string'},
        {name: 'parentDepartfullcode', type: 'string'}
    ]/*,
     validations: [
     {field: 'departcode', type: 'length', min: 1, max: 12},
     {field: 'departname', type: 'length', min: 1, max: 20}
     ]*/
});

