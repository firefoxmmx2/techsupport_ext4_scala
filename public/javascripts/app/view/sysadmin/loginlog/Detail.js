/**
 * 登录日志详情
 */
Ext.define('Techsupport.view.sysadmin.loginlog.Detail', {
    extend: 'Techsupport.view.base.BaseDetail',
    alias: 'widget.loginlogdetail',
    initComponent: function () {
        this.callParent()
        this.down('form > panel').add([
            {xtype: 'textfield', name: 'id', fieldLabel: '登录ID', readOnly: true},
            {xtype: 'textfield', name: 'loginuserid', fieldLabel: '登录用户ID', readOnly: true},
            {xtype: 'textfield', name: 'useraccount', fieldLabel: '登录账户', readOnly: true},
            {xtype: 'textfield', name: 'username', fieldLabel: '用户名称', readOnly: true},
            {xtype: 'textfield', name: 'loginunitcode', fieldLabel: '登录单位代码', readOnly: true},
            {xtype: 'textfield', name: 'loginunitname', fieldLabel: '登录单位名称', readOnly: true},
            {xtype: 'textfield', name: 'loginip', fieldLabel: '登录IP', readOnly: true},
            {xtype: 'textfield', name: 'loginmac', fieldLabel: '登录物理网卡', readOnly: true},
            {xtype: 'datefield', name: 'logintiime', fieldLabel: '登录时间', readOnly: true},
            {xtype: 'datefield', name: 'quittime', fieldLabel: '注销时间', readOnly: true}
        ])
        Ext.Array.each(this.query('button'), function (b) {
            b.remove()
        }, this)
        this.down('toolbar[dock=bottom][ui=foot]').add(
            {xtype: 'button', text: '关闭', action: 'close'}
        )
    }
})
