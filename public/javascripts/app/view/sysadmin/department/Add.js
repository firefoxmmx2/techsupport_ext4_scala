/**
 * Created by hooxin on 14-5-22.
 */
Ext.define('Techsupport.view.sysadmin.department.Add', {
    extend: 'Techsupport.view.base.BaseDetail',
    title: '机构新增',
    alias: 'widget.departmentadd',
    initComponent: function () {
        this.callParent(arguments);
        this.query('form panel').map(function (p) {
            p.add({fieldLabel: '机构代码', name: 'departcode', allowBlank: false,
                    blankText: '机构代码不能为空', vtype: 'alphanum',
                    validator: function (value) {
                        return this.textValid;
                    },
                    textValid: true
                },
                {fieldLabel: '机构名称', name: 'departname', allowBlank: false, blankText: '机构名称不能为空'},
                {fieldLabel: '机构级别', name: 'departlevel', allowBlank: false, blankText: '机构级别不能为空', vtype: 'number'},
                {fieldLabel: '机构全码', name: 'departfullcode', allowBlank: false, blankText: '机构全码不能为空', readOnly: true, vtype: 'alphanum'},
                {fieldLabel: '上级机构id', name: 'parentDepartid', allowBlank: false, blankText: '上级机构id不能为空', hidden: true, readOnly: true, vtype: 'number'},
                {fieldLabel: '上级机构名称', name: 'parentDepartname', allowBlank: false, blankText: '上级机构名称不能为空', readOnly: true},
                {fieldLabel: '序号', name: 'nodeOrder', allowBlank: false, blankText: '序号不能为空', vtype: 'number'},
                {fieldLabel: '是否为下级', name: 'isLeaf', allowBlank: false, blankText: '不能为空', hidden: true, xtype: 'combobox', readOnly: true,
                    store: 'YN',
                    queryMode: 'local',
                    displayField: 'text',
                    valueField: 'value',
                    value: 'Y'});
        });
    }
});