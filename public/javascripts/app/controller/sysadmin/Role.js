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
        "sysadmin.role.RelateFunc"  ,
        "sysadmin.role.RelateMenu"
    ],
    models: ['Role', 'RoleFunc'],
    refs: [
        {ref: 'queryForm', selector: 'rolemanage panel[region=center] form'},
        {ref: 'roleListGrid', selector: "rolemanage panel[region=center] rolelist"}
    ],
    init: function () {
        this.control({
            'rolemanage' : {
              afterrender: function (p) {
                  var queryBtn = p.down('panel[region=center] button[action=query]')
                 setTimeout(function () {
                     queryBtn.fireEvent('click', queryBtn)
                 },50)
              }
            },
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
                    this.toRelateMenu()
                }
            },
            'rolemanage panel[region=center] button[action=relateFunction]': {//关联操作按钮
                click: function (button) {
                    this.toRelateFunction()
                }
            },
            'roledetail[usertype=add] button[action=enter]': { //添加窗口添加角色
                click: function (button) {
                    this.addRole(button.up('window').down('form'))
                }
            },
            'roledetial[usertype=modify] button[action=enter]': {//修改窗口保存角色
                click: function (button) {
                    var form = button.up('window').down('form')
                    this.modifyRole(form)
                }
            },
            'roledetail button[action=cancel]': {
                click: function (button) {
                    button.up('window').close()
                }
            }
        })
    },
    toAddRole: function () {//打开新增
        var _window = this.getView("sysadmin.role.Detail").create({
            title: '角色新增',
            usertype: 'add'
        })
        var form = _window.down('form')
//        _window.down('button[action=enter]').on('click', function (button) {
//
//        },this)
        form.loadRecord(this.getModel("Role").create())
        _window.show()
    },
    addRole: function (form) {//新增
        var store = this.getRoleListGrid().getStore()
        if (form.isValid()) {
            form.updateRecord()
            var record = form.getRecord()
            store.add(record)
            var extraParams = store.getProxy().extraParams
            store.getProxy().extraParams = {}
            store.sync({
                success: function (batch, opts) {
                    store.commitChanges()
                    form.up('window').close()
                    this.queryRole()
                },
                failure: function (batch, opts) {
                    store.rejectChanges()
                    Ext.Msg.alert("错误", "角色新增发生错误")
                },
                scope: this
            })
            store.getProxy().extraParams = extraParams
        }
    },
    toModifyRole: function (record) { //打开修改
        var _window = this.getView("sysadmin.role.Detail").create({
            title: '角色修改[' + record.data.rolename + ']',
            usertype: 'modify'
        })
        var form = _window.down('form')
        form.loadRecord(record)
        _window.show()
    },
    modifyRole: function (form) { //修改
        if (form.isValid()) {
            var store = this.getRoleListGrid().getStore()
            form.updateRecord()
            var extraParams = store.getProxy().extraParams
            store.getProxy().extraParams = {}
            store.sync({
                success: function (batch, opts) {
                    store.commitChanges()
                    form.up('window').close()
                },
                failure: function (batch, opts) {
                    store.rejectChanges()
                    Ext.Msg.alert("错误", '角色修改发生错误')
                },
                scope: this
            })
            store.getProxy().extraParams = extraParams
        }
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
    toRelateMenu: function () { //打开角色关联菜单窗口
        var _window = this.getView("sysadmin.role.RelateMenu").create()
        _window.show()
    },
    toRelateFunction: function () {//打开角色关联功能窗口
        var _window = this.getView("sysadmin.role.RelateFunc").create()
        _window.show()
    },
    relateMenu: function () { //关联菜单操作

    },
    relateFunction: function () { //关联功能操作

    }
})