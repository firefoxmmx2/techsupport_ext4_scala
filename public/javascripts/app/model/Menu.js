/**
 * 菜单模型
 */
Ext.define('Techsupport.model.Menu', {
    extend: 'Ext.data.Model',
    idProperty: 'id',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'menuname', type: 'string'},
        {name: 'funcentry', type: 'string'},
        {name: 'menulevel', type: 'int'},
        {name: 'parentmenucode', type: 'string'},
        {name: 'menufullcode', type: 'string'},
        {name: 'nodeorder', type: 'int'},
        {name: 'isleaf', type: 'string'},
        {name: 'systemcode', type: 'string'},
        {name: 'parentMenufullcode', type: 'string'}
    ]
});
