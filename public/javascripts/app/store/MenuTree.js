/**
 * 菜单树存储
 */
Ext.define('Techsupport.store.MenuTree', {
    extend: 'Ext.data.TreeStore',
    model: 'Techsupport.model.MenuTree',
    root: {
        id: 0,
        text: '根节点',
        expanded: false,
        menuname: '',
        menulevel: 0,
        menufullcode: '',
        systemcode: ''
    },
    proxy: {
        type: 'ajax',
        method: 'GET',
        url: '/api/menus/treeNodes',
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});