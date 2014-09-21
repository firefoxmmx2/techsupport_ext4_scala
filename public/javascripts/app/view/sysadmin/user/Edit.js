/**
 * Created by hooxin on 14-5-8.
 */
Ext.define('Techsupport.view.sysadmin.user.Edit', {
    extend: 'Techsupport.view.sysadmin.user.Add',
    title: '用户修改',
    initComponent: function (w) {
        this.callParent(arguments);
        this.query('form panel[layout=column]').map(function (p) {
            p.insert(0, {
                xtype: 'textfield', name: 'id', fieldLabel: '用户ID', allowBlank: false, blankText: '用户ID不能为空'
            })
        })

    }
});