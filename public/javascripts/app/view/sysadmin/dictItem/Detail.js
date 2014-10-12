/**
 * Created by hooxin on 14-10-12.
 */
Ext.define('Techsupport.view.sysadmin.dictItem.Detail', {
    extend: 'Techsupport.view.base.BaseDetail',
    alias: 'widget.dictItemDetail',
    initComponent: function () {
        this.callParent(arguments)
        this.down('form').add({
            xtype: 'panel',
            border: false,
            layout: 'column',
            defaults: {
                margin: {left: 5, top: 0, bottom: 5, right: 5}
            }
        })
        Ext.apply(this.down('form>panel').defaults, {columnWidth: .33, anchor: '100%', labelWidth: '50%'})
        this.down('form>panel').add([
            {xtype: 'textfield',
                name: 'id',
                fieldLabel: 'ID',
                hidden: true
            },
            {xtype: 'textfield',
                name: 'dictcode',
                fieldLabel: '字典代码',
                allowBlank: false,
                blankText: '字典代码不能为空',
                vtype: 'alphanum'
            },
            {xtype: 'textfield',
                name: 'displayName',
                fieldLabel: '显示值',
                allowBlank: false,
                blankText: '显示值不能为空'
            },
            {xtype: 'textfield',
                name: 'factValue',
                fieldLabel: '真实值',
                allowBlank: false,
                blankText: '真实值不能为空'
            },
            {xtype: 'textfield',
                name: 'appendValue',
                fieldLabel: '附加值'
            },
            {xtype: 'textfield',
                name: 'superItemId',
                fieldLabel: '上级ID',
                hidden: true
            },
            {xtype: 'textfield',
                name: 'sibOrder',
                fieldLabel: '序号',
                allowBlank: false,
                blankText: '序号不能为空'
            },
            {xtype: 'combobox',
                store: 'YN',
                displayField: 'text',
                valueField: 'value',
                triggerAction: 'all',
                editable: false,
                emptyText: '请选择',
                queryMode: 'local',
                name: 'isleaf',
                fieldLabel: '子节点',
                allowBlank: false,
                blankText: '子节点不能为空',
                hidden: true
            },
            {xtype: 'combobox',
                store: 'DictItemDisplayFlag',
                displayField: 'text',
                valueField: 'value',
                triggerAction: 'all',
                editable: false,
                emptyText: '请选择',
                queryMode: 'local',
                name: 'displayFlag',
                fieldLabel: '显示标志',
                allowBlank: false,
                blankText: '显示标志不能为空'
            },
            {xtype: 'combobox',
                store: 'OneZero',
                displayField: 'text',
                valueField: 'value',
                triggerAction: 'all',
                editable: false,
                emptyText: '请选择',
                queryMode: 'local',
                name: 'isValid',
                fieldLabel: '是否可用',
                allowBlank: false,
                blankText: '是否可用不能为空'
            },
            {xtype: 'textfield',
                name: 'itemSimplePin',
                fieldLabel: '简拼'
            },
            {xtype: 'textfield',
                name: 'itemAllPin',
                fieldLabel: '全拼'
            }
        ])
    }
})