/**
 * Created by hooxin on 14-10-9.
 */
Ext.define('Techsupport.store.DictItemTree', {
    extend: 'Ext.tree.Store',
    model: 'Techsupport.model.DictItem',
    root: {
        text: '根节点',
        id: '0',
    },
    proxy: {
        type: 'ajax',
        url: '/api/dictitems/treenode',
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success',
            totalProperty: 'total'
        }
    }
})