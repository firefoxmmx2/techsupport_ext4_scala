/**
 * Created by hooxin on 14-10-26.
 */
Ext.define('Techsupport.view.sysadmin.loginlog.List', {
    extend: 'Techsupport.view.base.BaseList',
    alias: 'widget.loginloglist',
    initComponent: function () {
        this.store = 'LoginLog'
        this.callParent()

    }
})
