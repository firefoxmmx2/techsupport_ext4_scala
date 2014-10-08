/**
 * Created by hooxin on 14-10-2.
 */
/**
 * 系统管理(只做平面结构)
 */
Ext.define('Techsupport.view.sysadmin.system.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.systemmanage',
    initComponent: function () {
        this.items = [this.superclass.items[1]]
        this.callParent(arguments);
//        this.down('panel[region=west]').remove();
        this.down('form').add([
            {xtype: 'textfield', fieldLabel: '系统代码', name: 'id'},
            {xtype: 'textfield', fieldLabel: '系统名称', name: 'systemname'},
            {xtype: 'textfield', fieldLabel: '系统描述', name: 'systemdefine'}
        ]);
        this.down('panel[region=center]').add({xtype: 'systemlist'});
    }
});