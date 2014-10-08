/**
 * Created by hooxin on 14-10-8.
 */
/**
 * 字典管理
 */
Ext.define('Techsupport.view.sysadmin.dict.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    initComponent: function () {
        this.items = this.superclass.items[1]
        this.callParent(arguments)

        this.down('form').add([
            {xtype: 'textfield', name: 'dictcode', fieldLabel: '字典代码'},
            {xtype: 'textfield', name: 'dictname', fieldLabel: '字典名称'},
            {xtype: 'textfield', name: 'dictType', fieldLabel: '字典类型'}
        ])

        this.down('panel[region=center]').add({xtype: 'dictList'})
    }
})
