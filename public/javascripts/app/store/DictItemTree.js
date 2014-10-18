/**
 * Created by hooxin on 14-10-9.
 */
Ext.define('Techsupport.store.DictItemTree', {
    extend: 'Ext.data.TreeStore',
    model: 'Techsupport.model.DictItem',
    root: {
        displayName: '根',
        id: '0'
    },
    proxy: {
        type: 'rest',
        url: '/api/dictitems/treenode',
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success',
            totalProperty: 'total'
        }
    }
})