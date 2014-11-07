/**
 * 角色详情
 */
Ext.define('Techsupport.view.sysadmin.role.Detail', {
    extend: 'Techsupport.view.base.BaseDetail',
    alias: 'widget.roledetail',
    initComponent: function () {
        this.callParent()
        this.down('form > panel')
            .add([
                {name: 'id', fieldLabel: 'ID',
                    allowBlank: false,
                    blankText: 'ID不能为空',
                    vtype:'number',
                    readOnly:true,
                    hidden:true
                },
                {name: 'rolename',
                    fieldLabel: '角色名称',
                    allowBlank: false,
                    blankText: '角色名称不能为空'
                },
                {name: 'roleDescription',
                    fieldLabel: '角色描述'
                },
                {xtype: 'combobox', name: 'roleType',
                    fieldLabel: '角色类型',
                    allowBlank: false,
                    blankText: '角色类型不能为空',
                    valueField: 'value',
                    displayField: 'text',
                    emptyText: '请选择',
                    queryMode:'local',
                    triggerAction:'all',
                    store: 'RoleType'
                },
                {name: 'jzlbdm', fieldLabel: '类别代码',
                    vtype: 'alphanum'
                },
                {name: 'jzlbmc', fieldLabel: '类别名称'
                },
                {name: 'departid', fieldLabel: '机构ID',
                    allowBlank: false,
                    blankText: '机构ID不能为空',
                    hidden: true,
                    readOnly:true,
                    vtype: 'number'
                }
            ])
    }
})