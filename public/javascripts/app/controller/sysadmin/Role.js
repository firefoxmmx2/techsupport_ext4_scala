/**
 *
 */
Ext.define('Techsupport.controller.sysadmin.Role', {
    extend: 'Ext.app.Controller',
    stores: ['Role'],
    views: [
        "sysadmin.role.List",
        "sysadmin.role.Manage",
        "sysadmin.role.Detail",
        "sysadmin.role.RelateFunc"
    ],
    models: ['Role', 'RoleFunc'],
    refs: [
        {ref: 'queryForm', selector: 'rolemanage panel[region=center] form'},
        {ref: 'roleListGrid', selector: "rolemanage panel[region=center] rolelist"}
    ],
    init: function () {
        this.control({
            'rolemanage rolelist': {
                itemdblclick: function (grid, record) { //双击打开编辑窗口
                    this.toModifyRole(record)
                }
            },
            'rolemanage panel[region=center] button[action=query]': {//管理界面查询按钮
                click: function (button) {
                    this.queryRole()
                }
            },
            'rolemanage panel[region=center] button[action=add]': {//管理界面添加按钮
                click: function (button) {
                    this.toAddRole()
                }
            },
            'rolemanage panel[region=center] button[action=modify]': {//管理界面修改按钮
                click: function (button) {
                    var grid = this.getRoleListGrid()
                    Ext.Array.each(grid.getSelectionModel().getSelection(), function (record) {
                        this.toModifyRole(record)
                    }, this)
                }
            },
            'rolemanage panel[region=center] button[action=remove]': {//管理界面删除按钮
                click: function (button) {
                    this.removeRole()
                }
            },
            'rolemanage panel[region=center] button[action=relateMenu]': {//管理界面关联菜单按钮
                click: function (button) {

                }
            },
            'rolemanage panel[region=center] button[action=relateFunction]': {//关联操作按钮
                click: function (button) {

                }
            }
        })
    },
    toAddRole: function () {//打开新增
        var _window = this.getView("sysadmin.role.Detail").create({
            title: '角色新增',
            _type: 'add'
        })
        var form = _window.down('form')
        form.loadRecord(this.getModel("Role").create())
        _window.show()
    },
    addRole: function () {//新增

    },
    toModifyRole: function (record) { //打开修改
        var _window = this.getView("sysadmin.role.Detail").create({
            title: '角色修改',
            _type: 'modify'
        })
        var form = _window.down('form')
        form.loadRecord(record)
        _window.show()
    },
    modifyRole: function () { //修改

    },
    removeRole: function () { //删除
        var grid = this.getRoleListGrid()
        var store = grid.getStore()
        var selection = Ext.Array.map(grid.getSelectionModel().getSelection(), function (record) {
            store.remove(record)
            return record
        }, this)
        if (selection.length > 0) {
            store.sync({
                success: function (batch, options) {
                    store.commitChanges()
                    if (store.getProxy().pageSize > store.getTotalCount()) {
                        this.queryRole()
                    }
                },
                failure: function (batch, options) {
                    store.rejectChanges()
                    Ext.Msg.alert("错误", "删除角色发生错误")
                },
                scope: this
            })
        }
    },
    queryRole: function () { //查询
        var form = this.getQueryForm()
        if (form.isValid()) {
            var store = this.getRoleStore()
            Ext.apply(store.getProxy().extraParams, form.getValues())
            store.load()
        }
    },
    relateRoleMenu: function () { //角色关联菜单

    },
    relateRoleFunc: function () {//角色关联功能

    }
})