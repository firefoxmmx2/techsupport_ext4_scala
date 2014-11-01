/**
 *  功能列表
 */
Ext.define('Techsupport.view.sysadmin.function.List', {
    extend: 'Techsupport.view.base.BaseList',
    alias: 'widget.functionlist',
    initComponent: function () {
        this.store = "Function"
        this.columns = [
            {dataIndex: 'id', text: '功能代码', flex: 1},
            {dataIndex: 'systemcode', text: '系统代码', flex: 1},
            {dataIndex: 'funcname', text: '功能名称', flex: 1},
            {dataIndex: 'funcdefine', text: '功能描述', flex: 1},
            {dataIndex: 'functype', text: '功能类别', flex: 1}
        ]
        this.callParent(arguments)
    }
})