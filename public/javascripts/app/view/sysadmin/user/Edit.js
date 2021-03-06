/**
 * Created by hooxin on 14-5-8.
 */
Ext.define('Techsupport.view.sysadmin.user.Edit', {
    extend: 'Techsupport.view.sysadmin.user.Add',
    title: '用户修改',
    alias: 'widget.useredit',
    initComponent: function (w) {
        this.callParent(arguments);
        this.down('form panel').insert(0, {
            xtype: 'textfield', name: 'id', fieldLabel: '用户ID', allowBlank: false, blankText: '用户ID不能为空',
            readOnly: true
        });
        this.down('form textfield[name=isValid]').show()
    }
});