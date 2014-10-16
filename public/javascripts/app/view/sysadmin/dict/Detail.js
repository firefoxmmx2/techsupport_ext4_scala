/**
 * Created by hooxin on 14-10-8.
 */
/**
 * 字典详情
 */
Ext.define('Techsupport.view.sysadmin.dict.Detail', {
    extend: 'Techsupport.view.base.BaseDetail',
    height:530,
    alias: 'widget.dictDetail',
    initComponent: function () {
        Ext.apply(this.items, this.superclass.items)
        this.items[0].items = []
        this.callParent(arguments)

        this.down('form').add({
            xtype: 'panel',
            border: false,
            items: [
                {
                    xtype: 'fieldset',
                    title: '字典信息',
                    layout: 'column',
                    defaults: {
                        margin: {top: 2, bottom: 2, right: 20, left: 0},
                        columnWidth: 0.33,
                        anchor: '100%'
                    },
                    items: [
                        {xtype: 'textfield', name: 'id', fieldLabel: '字典ID', readOnly: true, hidden: true},
                        {xtype: 'textfield', name: 'dictcode', fieldLabel: '字典代码',
                            allowBlank: false,
                            blankText: '字典代码不能为空',
                            vtype: 'alphanum',
                            textValid: false,
                            validator: function (v) {
                                return this.textValid
                            }

                        },
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
                            store: 'DictMaintFlag',
                            textField: 'text',
                            valueField: 'value',
                            triggerAction: 'all',
                            queryMode: 'local',
                            emptyText: '请选择'
                        },
                        {xtype: 'combobox', name: 'dictType', fieldLabel: '字典类型',
                            allowBlank: false,
                            blankText: '字典类型不能为空',
                            store: 'DictType',
                            textField: 'text',
                            valueField: 'value',
                            triggerAction: 'all',
                            queryMode: 'local',
                            emptyText: '请选择'
                        },
                        {xtype: 'textfield', name: 'dictSimplePin', fieldLabel: '字典简拼', hidden: true},
                        {xtype: 'textfield', name: 'dictAllPin', fieldLabel: '字典全拼', hidden: true},
                        {xtype: 'textfield', name: 'dictVersion', fieldLabel: '字典版本',
                            allowBlank: false,
                            blankText: '字典版本不能为空',
                            vtype: 'number'},
                        {xtype: 'datefield', name: 'createTime', fieldLabel: '创建时间',
                            editable: false,
                            readOnly: true,
                            format: 'Y-m-d H:i:s'
                        }
                    ]
                },
                {
                    xtype: 'fieldset',
                    title: '字典项信息',
                    defaults: {
                        margin: {
                            top: 2,
                            bottom: 2,
                            left: 2,
                            right: 2
                        }
                    },
                    items: [
                        {xtype: 'dictItemSimpleList', hidden: true, height: 300},
                        {xtype: 'dictItemTreeList', hidden: true}
                    ]
                }
            ]
        })
    }
})
