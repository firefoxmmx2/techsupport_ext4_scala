/**
 * 用户管理
 */
Ext.define('Techsupport.view.sysadmin.user.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.usermanage',
    initComponent: function () {
        this.callParent(arguments);

        this.down('panel[region=west]').add({xtype: 'departmenttree'});
        this.down('panel[region=center]').add({xtype: 'userlist'});
        this.down('form').add([
            {xtype: 'textfield', name: 'useraccount', fieldLabel: '帐号'},
            {xtype: 'textfield', name: "username", fieldLabel: '用户姓名'},
            {xtype: 'combobox',
                name: 'isValid',
                store: 'OneZero',
                queryMode: 'local',
                displayField: 'text',
                valueField: 'value',
                fieldLabel: '是否可用',
                editable: false
            },
            {xtype: 'hidden', name: 'departid', fieldLabel: '机构ID'}
        ]);
    }
});
