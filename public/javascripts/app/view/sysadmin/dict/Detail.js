/**
 * Created by hooxin on 14-10-8.
 */
/**
 * 字典详情
 */
Ext.define('Techsupport.view.sysadmin.dict.Detail', {
    extend: 'Techsupport.view.base.BaseDetail',
    alias: 'widget.dictDetail',
    initComponent: function () {
        this.callParent(arguments)

        this.down('form panel').add([
            {xtype: 'textfield', name: 'id', fieldLabel: '字典ID', readOnly: true, hidden: true},
            {xtype: 'textfield', name: 'dictcode', fieldLabel: '字典代码',
                allowBlank: false,
                blankText: '字典代码不能为空',
                vtype: 'alphanum' },
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
            {xtype: 'textfield', name: 'dictSimplePin', fieldLabel: '字典简拼'},
            {xtype: 'textfield', name: 'dictAllPin', fieldLabel: '字典全拼'},
            {xtype: 'textfield', name: 'dictVersion', fieldLabel: '字典版本',
                allowBlank: false,
                blankText: '字典版本不能为空',
                vtype: 'number'},
            {xtype: 'textfield', name: 'createTime', fieldLabel: '创建时间',
                readOnly: true, editable: false},
            {
                xtype: 'panel',
                border: false,
                name: 'typeSimplePanel',
                items: [
                    {
                        xtype: 'grid',
                        border: false,
                        store: 'DictItem',
                        selType: 'checkboxmodel',
                        selModel: {
                            flex: 0,
                            showHeaderCheckbox: true,
                            width: 16
                        },
                        loadMask: true,
                        dockedItems: [
                            {
                                xtype: 'pagingtoolbar',
                                store: 'DictItem',
                                dock: 'bottom',
                                pageSize: 10,
                                listeners: {
                                    afterrender: function (p, eOpts) {
                                        p.remove(p.down('button:last'))
                                        p.remove(p.down('tbseparator:last'))
                                    }
                                }
                            }
                        ],
                        listeners: {
                            render: function (g) {
                                g.getStore().removeAll()
                            },
                            afterlayout: function (g, layout, opts) {
                                var headerHeight = g.headerCt.down('[id*=gridcolumn]').getHeight();
                                var pagesize = Math.round(g.getHeight() / headerHeight);
                                g.getStore().pageSize = pagesize;
                                g.getStore().trailingBufferZone = pagesize;
                                g.getStore().getProxy().setExtraParam('limit', pagesize);
                            }
                        }
                    }
                ]
            },
            {
                xtype: 'panel',
                border: false,
                name: 'typeTreePanel',
                items: [
                    {
                        xtype: 'treepanel',
                        selType: 'checkboxmodel',
                        selModel: {
                            flex: 0,
                            showHeaderCheckbox: true,
                            width: 16
                        },
                        loadMask: true,
                        useArrows: true,
                        rootVisible: false,
                        store: 'DictItemTree',
                        animate: true,
                        columns: [
                            { xtype: 'treecolumn', text: '显示值', dataIndex: 'displayName' },
                            {text: '实际值', dataIndex: 'factValue', flex: 1 },
                            {text: '附加值', dataIndex: 'appendValue', flex: 1 },
                            {text: '序列', dataIndex: 'sibOrder', flex: 1 },
                            {text: '显示标志', dataIndex: 'displayFlag', flex: 1},
                            {text: '是否可用', dataIndex: 'isValid', flex: 1},
                            {text: '简拼', dataIndex: 'itemSimplePin', flex: 1},
                            {text: '全拼', dataIndex: 'itemAllPin', flex: 1}
                        ]
                    }
                ]
            }
        ])
    }
})
