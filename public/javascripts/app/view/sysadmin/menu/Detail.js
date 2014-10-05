/**
 * 菜单详情
 */
Ext.define('Techsupport.view.sysadmin.menu.Detail', {
    extend: 'Techsupport.view.base.BaseDetail',
    alias: 'widget.menudetail',
    initComponent: function () {
        this.callParent(arguments);
        this.down('form panel').add([
            {xtype: 'textfield', fieldLabel: '菜单代码', name: 'id', validator: function (value) {
                return this.textValid;
            }, allowBlank: false, blankText: '菜单代码不能为空', textValid: true},
            {xtype: 'textfield', fieldLabel: '菜单名称', name: 'menuname', allowBlank: false, blankText: '菜单名称不能为空'},
            {xtype: 'textfield', fieldLabel: '菜单地址', name: 'funcentry', allowBlank: false, blankText: '菜单地址不能为空'},
            {xtype: 'textfield', fieldLabel: '菜单级别', name: 'menulevel', allowBlank: false, blankText: ' 菜单级别不能为空', readOnly: true},
            {xtype: 'textfield', fieldLabel: '父菜单代码', name: 'parentmenucode', allowBlank: false, blankText: '父菜单代码不能为空',
                readOnly: true, hidden: true},
            {xtype: 'textfield', fieldLabel: '菜单全码', name: 'menufullcode', allowBlank: false, blankText: '菜单全码不能为空',
                readOnly: true},
            {xtype: 'textfield', fieldLabel: '排序号', name: 'nodeorder', allowBlank: false, blankText: '排序号不能为空'},
            {xtype: 'combobox', fieldLabel: '是否为子菜单', name: 'isleaf',
                allowBlank: true,
                blankText: '是否为子菜单不能为空',
                store: 'YN',
                queryMode: 'local',
                triggerAction: 'all',
                displayField: 'text',
                valueField: 'value',
                value: 'Y',
                editable: false,
                emptyText: '请选择'
            },
            {xtype: 'textfield', fieldLabel: '系统代码', name: 'systemcode', allowBlank: false, blankText: '系统代码不能为空'}
        ]);
    }
});