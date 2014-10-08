/**
 * Created by hooxin on 14-10-8.
 */
/**
 * 字典详情
 */
Ext.define('Techsupport.view.dict.Detail', {
    extend: 'Techsupport.view.base.BaseDetail',
    alias: 'widget.dictDetail',
    initComponent: function () {
        this.callParent(arguments)

        this.down('form panel').add([
            {xtype: 'textfield', name: 'id', fieldLabel: '字典ID', readOnly: true, hidden: true},
            {xtype: 'textfield', name: 'dictcode', fieldLabel: '字典代码',
                allowBlank: false,
                blankText: '字典代码不能为空', },
            {xtype: 'textfield', name: 'dictname', fieldLabel: '字典名称',
                allowBlank: false,
                blankText: '字典名称不能为空'},
            {xtype: 'textfield', name: 'sibOrder', fieldLabel: '序列',
                allowBlank: false,
                blankText: '序列不能为空',
                vtype: 'number'},
            {xtype: 'combobox', name: 'maintFlag', fieldLabel: '维护标志',
                allowBlank: false,
                blankText: '维护标志不能为空',
                store: {
                    type: 'array',
                    fields: ['text', 'value'],
                    data: [
                        {text: '维护', value: '0'},
                        {text: '停止维护', value: '1'}
                    ]
                },
                textField: 'text',
                valueField: 'value',
                triggerAction: 'all',
                queryMode: 'local'
            },
            {xtype: 'combobox', name: 'dictType', fieldLabel: '字典类型',
                allowBlank: false,
                blankText: '字典类型不能为空',
                store: {
                    type: 'array',
                    fields: ['text', 'value'],
                    data: [
                        {text: '简单', value: '01'},
                        {text: '树形', value: '02'}
                    ]
                },
                textField: 'text',
                valueField: 'value',
                triggerAction: 'all',
                queryMode: 'local'
            },
            {xtype: 'textfield', name: 'dictSimplePin', fieldLabel: '字典简拼'},
            {xtype: 'textfield', name: 'dictAllPin', fieldLabel: '字典全拼'},
            {xtype: 'textfield', name: 'dictVersion', fieldLabel: '字典版本',
                allowBlank: false,
                blankText: '字典版本不能为空',
                vtype: 'number'},
            {xtype: 'textfield', name: 'createTime', fieldLabel: '创建时间',
                readOnly: true, editable: false}
        ])
    }
})
