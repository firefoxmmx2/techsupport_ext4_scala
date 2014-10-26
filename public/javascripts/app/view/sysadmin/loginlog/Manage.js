/**
 * 登录日志管理
 */
Ext.define('Techsupport.view.sysadmin.loginlog.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.loginlogmanage',
    initComponent: function () {
        this.callParent()
        this.down('panel[region=west]').remove()
        this.down('split').remove()
        this.down('toolbar').remove()
        this.down('form').add([
            {xtype: 'textfield', name: '', fieldLabel: '用户帐号', maxLength: 20, maxLengthText: '用户帐号不能超过20个字符'},
            {xtype: 'textfield', name: '', fieldLabel: '机构名称', maxLength: 20, maxLengthText: '机构名称不能超过20个字符'},
            {xtype: 'datefield', name: '', fieldLabel: '登录起始时间', editable: false},
            {xtype: 'datefield', name: '', fieldLabel: '登录结束时间', editable: false}
        ])
    }
})
