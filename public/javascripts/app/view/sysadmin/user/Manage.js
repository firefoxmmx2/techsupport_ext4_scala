/**
 * 用户管理
 */
Ext.define('Techsupport.view.sysadmin.user.Manage', {
    extend: 'Techsupport.view.base.BaseManage',
    alias: 'widget.usermanage',
    initComponent: function () {
        this.callParent(arguments);

        this.query('panel[region=west]').map(function (p) {
            p.add({xtype:'departmenttree'});
        });
        this.query('panel[region=center]').map(function (p) {
            p.add({xtype: 'userlist'});
        });
        this.query('form').map(function (f) {
            f.add([
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
            ]) ;
        });
    }
});
