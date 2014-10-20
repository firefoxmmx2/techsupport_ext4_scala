/**
 * Created by hooxin on 14-10-19.
 */
/**
 * 字典项管理界面
 */
Ext.define('Techsupport.view.sysadmin.dictItem.Manage', {
    extend: "Techsupport.view.base.BaseManage",
    alias: "widget.dictItemManage",
    initComponent: function () {
        this.callParent(arguments)

        this.down('panel[region=west]').add({
            xtype: 'dictList',
            border: false,
            selModel: {},
            selType: "rowmodel",
            columns: [
                {text: '字典名称', dataIndex: 'dictname', flex: 1},
                {text: '字典代码', dataIndex: 'dictcode', flex: 1}
            ]
        })
        var dictListGrid = this.down('dictList')
        dictListGrid.addDocked({
            xtype: 'toolbar',
            dock: 'top',
            border: false,
            layout: {
                type: 'column'
            },
            items: [
                {
                    xtype: 'form',
                    border: false,
                    layout: 'column',
                    columnWidth: .7,
                    items: [
                        {xtype: 'textfield', name: 'queryfield', columnWidth: 1, anchor: '100%'}
                    ]
                },
                {xtype: 'button', action: 'query', text: '查询', iconCls: 'icon-query', anchor: '100%', columnWidth: .3}
            ]
        })

        //查询条件
        this.down('panel[region=center] form').add(
            [
                {
                    xtype: 'textfield',
                    fieldLabel: '显示值',
                    name: 'displayName'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: '实际值',
                    name: 'factValue'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: '字典代码',
                    name: 'dictcode',
                    hidden: true
                }
            ]
        )

        //添加简单列表和树形列表
        this.down('panel[region=center]').add([
            {xtype: 'dictItemSimpleList', hidden: true},
            {xtype: 'dictItemTreeList', hidden: true}
        ])
    }
})
