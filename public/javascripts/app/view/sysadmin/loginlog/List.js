/**
 * 登录日志列表
 */
Ext.define('Techsupport.view.sysadmin.loginlog.List', {
    extend: 'Techsupport.view.base.BaseList',
    alias: 'widget.loginloglist',
    columns: [
        {text: "登录ID", dataIndex: "id", flex: 1},
        {text: "登录账户", dataIndex: "useraccount", flex: 1},
        {text: "用户名称", dataIndex: "username", flex: 1},
        {text: "登录单位名称", dataIndex: "loginunitname", flex: 1},
        {text: "登录时间", dataIndex: "logintiime", flex: 1,
            xtype: 'datecolumn',
            format: 'Y-m-d H:i:s'},
        {text: "注销时间", dataIndex: "quittime", flex: 1,
            xtype: 'datecolumn',
            format: 'Y-m-d H:i:s'}
    ],
    initComponent: function () {
        this.store = 'LoginLog'
        this.callParent()
    }
})
