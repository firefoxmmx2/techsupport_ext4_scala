/**
 * Created by hooxin on 14-10-5.
 */
/**
 * 全局参数管理
 */
Ext.define('Techsupport.view.sysadmin.globalParam.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.globalParamManage',
    initComponent: function () {
        this.items = this.superclass.items[1]
        this.callParent(arguments)
        this.down('form').add([
            {xtype: 'textfield', fieldLabel: '参数代码', name: 'id'},
            {xtype: 'textfield', fieldLabel: '参数名称', name: 'globalparname'}
        ])
        this.down('panel[region=center]').add({xtype: 'globalParamList'})
    }
})