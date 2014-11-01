/**
 * Created by hooxin on 14-10-29.
 */
Ext.define('Techsupport.view.sysadmin.function.Detail', {
    extend: 'Techsupport.view.base.BaseDetail',
    alias: 'widget.functiondetail',
    initComponent: function () {
        this.callParent(arguments)
        this.down('form > panel').add([
            {name: 'id', fieldLabel: '功能代码'},
            {name: 'systemcode', fieldLabel: '系统代码'},
            {name: 'funcname', fieldLabel: '功能名称'},
            {xtype: 'combobox', name: 'functype', fieldLabel: '功能类别',
                allowBlank: false,
                blankText: '功能类别不能为空',
                queryMode: 'local',
                triggerAction: 'all',
                displayField: 'text',
                valueField: 'value',
                editable: false,
                emptyText: '请选择',
                store: Ext.create('Ext.data.Store', {
                    fields: ['value', 'text'],
                    data: [
                        {value: '0', text: '管理类型'},
                        {value: '1', text: '普通类型'}
                    ],
                    autoLoad: true
                })
            },
            {xtype: 'textareafield', name: 'funcdefine', fieldLabel: '功能描述',
                columnWidth: 1,
                grow: true,
                height: 40
            }
        ])

    }
})
