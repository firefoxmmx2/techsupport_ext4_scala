/**
 * Created by hooxin on 14-10-2.
 */
Ext.define('Techsupport.view.sysadmin.system.Detail', {
    extend: 'Techsupport.view.base.BaseDetail',
    alias: 'widget.systemdetail',
    initComponent: function () {
        this.callParent(arguments);
        this.down('form>panel').add([
            {xtype: 'textfield', fieldLabel: '系统代码', name: 'id', allowBlank: false, blankText: '系统代码不能为空',
                textValid: true,
                validator: function (value) {
                    return this.textValid
                }},
            {xtype: 'textfield', fieldLabel: '系统名称', name: 'systemname', allowBlank: false, blankText: '系统名称不能为空'},
            {xtype: 'textfield', fieldLabel: '系统描述', name: 'systemdefine'},
            {xtype: 'textfield', fieldLabel: '系统图标路径', name: 'picturepath'},
            {xtype: 'textfield', fieldLabel: '父级系统代码', name: 'parentsystemcode',
                allowBlank: false,
                blankText: '父级系统代码不能为空',
                hidden: true,
                value: '0',
                readOnly: true},
            {xtype: 'textfield', fieldLabel: '序号', name: 'nodeorder', allowBlank: false, blankText: '序号不能为空'},
            {xtype: 'combobox', fieldLabel: '是否为子系统', name: 'isleaf',
                allowBlank: false,
                blankText: '是否为子菜单不能为空',
                value: 'Y',
                store: 'YN',
                queryMode: 'local',
                triggerAction: 'all',
                displayField: 'text',
                valueField: 'value',
                editable: false,
                emptyText: '请选择'
            },
            {xtype: 'textfield', fieldLabel: '系统全码', name: 'fullcode',
                allowBlank: false,
                blankText: '系统全码不能为空',
                readOnly: true}
        ])
    }
});