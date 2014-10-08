/**
 * Created by hooxin on 14-10-5.
 */
/**
 * 全局参数列表
 */
Ext.define('Techsupport.view.sysadmin.globalParam.List', {
    extend: 'Techsupport.view.base.BaseList',
    store: 'GlobalParam',
    alias:'widget.globalParamList',
    columns: [
        {text: '全局参数代码', dataIndex: 'id', flex: 1},
        {text: '全局参数名称', dataIndex: 'globalparname', flex: 1},
        {text: '全局参数值', dataIndex: 'globalparvalue', flex: 1}
    ],
    initComponent: function () {
        Ext.apply(this.dockedItems,this.superclass.dockedItems)
        this.dockedItems[0].store="GlobalParam"
        this.callParent(arguments)
    }
})