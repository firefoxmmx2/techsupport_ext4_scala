/**
 * Created by hooxin on 14-5-23.
 */
Ext.define('Techsupport.store.DepartmentTree', {
    extend: 'Ext.data.TreeStore',
    model: 'Techsupport.model.DepartmentTree',
    root: {
        id: '0',
        text: '根节点',
        departcode: '',
        departfullcode: '',
        departlevel: 1,
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
