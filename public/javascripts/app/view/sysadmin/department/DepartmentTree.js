/**
 * Created by hooxin on 14-5-22.
 */
Ext.define('Techsupport.view.sysadmin.department.DepartmentTree', {
    extend: 'Ext.tree.Panel',
    store: Ext.create('Techsupport.store.DepartmentTree'),
    border: true,
    alias: 'widget.departmenttree'
});