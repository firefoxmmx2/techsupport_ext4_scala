/**
 *  功能管理
 */
Ext.define('Techsupport.view.sysadmin.function.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    initComponent: function () {
        this.alias = "widget.functionmanage"
        Ext.clone(this.items, this.superclass.items)
        this.items.splice(0, 1)
        this.callParent(arguments)
        this.down('panel[region=center] toolbar[dock=top] form').add([
            {xtype: 'textfield', name: 'id', fieldLabel: '功能代码'},
            {xtype: 'textfield', name: 'systemcode', fieldLabel: '系统代码'},
            {xtype: 'textfield', name: 'funcname', fieldLabel: '功能名称'},
            {xtype: 'textfield', name: 'funcdefine', fieldLabel: '功能描述'},
            {xtype: 'combobox', name: 'functype', fieldLabel: '功能类别',
                queryMode: 'local',
                triggerAction: 'all',
                displayField: 'text',
                valueField: 'value',
                editable: false,
                emptyText: '请选择',
                store: Ext.create('Ext.data.ArrayStore', {
                    fields: ['value', 'text'],
                    data: [
                        {value: '0', text: '管理类型'},
                        {value: '1', text: '普通类型'}
                    ]
                })
            }
        ])
        this.down('panel[region=center]').add({xtype: 'functionlist'})
    }
})