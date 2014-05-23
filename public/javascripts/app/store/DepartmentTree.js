/**
 * Created by hooxin on 14-5-23.
 */
Ext.define('Techsupport.store.DepartmentTree', {
    extend: 'Ext.data.TreeStore',
    fields: ['id', 'leaf', 'text', 'parentId'],
    idProperty: 'id',
    root: {
        id: '0',
        text: '根节点',
        expanded: false
    },
    proxy: {
        type: 'ajax',
        method: 'GET',
        url: '/api/departments/departmentTreeNode',
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});
