/**
 * 登录日志管理
 */
Ext.define('Techsupport.view.sysadmin.loginlog.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.loginlogmanage',
    initComponent: function () {
        this.items = Ext.clone(this.superclass.items)
        //移除左边的面板
        this.items.splice(0,1)
        //移除上面的工具栏
        this.items[0].dockedItems.splice(0,1)
        this.callParent()
        //添加查询条件
        this.down('form').add([
            {xtype: 'textfield', name: '', fieldLabel: '用户帐号', maxLength: 20, maxLengthText: '用户帐号不能超过20个字符'},
            {xtype: 'textfield', name: '', fieldLabel: '机构名称', maxLength: 20, maxLengthText: '机构名称不能超过20个字符'},
            {xtype: 'datefield', name: '', fieldLabel: '登录起始时间', editable: false},
            {xtype: 'datefield', name: '', fieldLabel: '登录结束时间', editable: false}
        ])
        //装入登录日志列表
        this.down('panel[region=center]').add({xtype: 'loginloglist'})
    }
})
