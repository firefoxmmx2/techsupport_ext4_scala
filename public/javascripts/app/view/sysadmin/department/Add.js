/**
 * Created by hooxin on 14-5-22.
 */
Ext.define('Techsupport.view.sysadmin.department.Add', {
    extend: 'Ext.window.Window',
    title: '机构新增',
    alias: 'widget.departmentadd',
    width: 630,
    bodyPadding: 2,
    items: [
        {xtype: 'form',
            formBind:true,
            defaults: {
            margin: {right: 10, left: 5, top: 5, bottom: 5}
        }, items: [
            {xtype: 'panel',
                layout: 'column',
                defaults: {
                    xtype: 'textfield',
                    margin: {left: 5, top: 0, bottom: 5, right: 5}
                },
                border: false,
                items: [
                    {fieldLabel: '机构代码', name: 'departcode', allowBlank: false, blankText: '机构代码不能为空',vtype:'alphanum'},
                    {fieldLabel: '机构名称', name: 'departname', allowBlank: false, blankText: '机构名称不能为空'},
                    {fieldLabel: '机构级别', name: 'departlevel', allowBlank: false, blankText: '机构级别不能为空',vtype:'number'},
                    {fieldLabel: '机构全码', name: 'departfullcode', allowBlank: false, blankText: '机构全码不能为空',readOnly:true,vtype:'alphanum'},
                    {fieldLabel: '上级机构id', name: 'parentDepartid', allowBlank: false, blankText: '上级机构id不能为空', hidden: true,readOnly:true,vtype:'number'},
                    {fieldLabel: '上级机构名称', name: 'parentDepartname', allowBlank: false, blankText: '上级机构名称不能为空',readOnly:true},
                    {fieldLabel: '序号', name: 'nodeOrder', allowBlank: false, blankText: '序号不能为空',vtype:'number'},
                    {fieldLabel: '是否为下级', name: 'isLeaf', allowBlank: false, blankText: '不能为空', hidden: true, xtype: 'combobox',readOnly:true,
                        store: 'YN',
                        queryMode: 'local',
                        displayField: 'text',
                        valueField: 'value',
                        value: 'Y'}
                ]}
        ]}
    ],
    buttons: [
        {xtype: 'button', text: '确定', action: 'enter'},
        {xtype: 'button', text: '取消', action: 'cancel'}
    ]
});