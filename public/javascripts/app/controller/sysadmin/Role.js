/**
 *
 */
Ext.define('Techsupport.controller.sysadmin.Role', {
    extend: 'Ext.app.Controller',
    stores: ['Role', 'RoleType', 'Function'],
    views: [
        "sysadmin.role.List",
        "sysadmin.role.Manage",
        "sysadmin.role.Detail",
        "sysadmin.role.RelateFunc",
        "sysadmin.role.RelateMenu"
    ],
    models: ['Role', 'RoleFunc'],
    refs: [
        {ref: 'queryForm', selector: 'rolemanage panel[region=center] form'},
        {ref: 'roleListGrid', selector: "rolemanage panel[region=center] rolelist"}
    ],
    init: function () {
        this.control({
            'rolemanage': {
                afterrender: function (p) {
                    var queryBtn = p.down('panel[region=center] button[action=query]')
                    setTimeout(function () {
                        queryBtn.fireEvent('click', queryBtn)
                    }, 50)
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
            },
            'relateFunc button[action=cancel]': { //关联功能取消按钮
                click: function (button) {
                    button.up('window').close()
                }
            },
            'relateFunc button[action=enter]': { //关联功能确定按钮
                click: function (button) {
                    var form = button.up('window').down('form')
                    this.relateFunction(form)
                }
            },
            'relateFunc grid[name=allFunctionGrid]': {
                render: function (p) {
                    var store = p.getStore()
                    var roleListGrid = p.up('relateFunc').down('rolelist')
                    store.load({
                        scope: this,
                        callback: function (records, operation, success) {
                            var params = {}
                            Ext.Array.each(roleListGrid.getSelectionModel().getSelection(), function (r, idx) {
                                params["roleIds[" + idx + "]"] = r.data.id
                            })
                            Ext.Ajax.request({
                                url: '/api/roles/relateFunctions',
                                method: 'GET',
                                params: params,
                                success: function (response) {
                                    var res = Ext.decode(response.responseText)
                                    if (res.data) {
                                        var selectedFunctionGrid = p.up('relateFunc').down('grid[name=selectedFunctionGrid]')
                                        var store2 = selectedFunctionGrid.getStore()
                                        store2.loadData(Ext.Array.map(res.data, function (r) {
                                            return Ext.create('Techsupport.model.RoleFunc', {
                                                funccode: r.id,
                                                funcname: r.funcname,
                                                "function": Ext.create('Techsupport.model.Function', r)
                                            })
                                        }))
                                        store.remove(Ext.Array.map(store2.getRange(), function (r) {
                                            return r.raw.function
                                        }))
                                        store.commitChanges()
                                    }
                                },
                                failure: function (response) {
                                    Ext.Msg.alert('错误', '获取角色关联的功能发生错误')
                                },
                                scope: this
                            })
                        }
                    })
                }
            },
            'relateFunc button[action=allLeft]': { //全部左移
                click: function (button) {
                    var relateFunc = button.up('window')

                    var selectedFunctionStore = relateFunc.down('grid[name=selectedFunctionGrid]').getStore()
                    if (!Ext.isEmpty(selectedFunctionStore.data)) {
                        var allFunctionStore = relateFunc.down('grid[name=allFunctionGrid]').getStore()
                        allFunctionStore.add(Ext.Array.Map(selectedFunctionStore.getRange(), function (r) {
                            return r.raw.function
                        }))
                        selectedFunctionStore.removeAll()
                    }
                }
            },
            'relateFunc button[action=left]': {//左移
                click: function (button) {
                    var relateFunc = button.up('window')
                    var selectedFunctionGrid = relateFunc.down("grid[name=selectedFunctionGrid]")
                    var selection = selectedFunctionGrid.getSelectionModel().getSelection()
                    if (selection.length > 0) {
                        var allFunctionGrid = relateFunc.down('grid[name=allFunctionGrid]')
                        allFunctionGrid.getStore().add(Ext.Array.map(selection, function (r) {
                            return r.raw.function;
                        }))
                        selectedFunctionGrid.getStore().remove(selection)
                    }
                }
            },
            'relateFunc button[action=right]': {//右移
                click: function (button) {
                    var relateFunc = button.up('window')
                    var allFunctionGrid = relateFunc.down('grid[name=allFunctionGrid]')
                    var selection = allFunctionGrid.getSelectionModel().getSelection()
                    if (selection.length > 0) {
                        var selectedFunctionGrid = relateFunc.down('grid[name=selectedFunctionGrid]')
                        selectedFunctionGrid.getStore().add(Ext.Array.map(selection, function (r) {
                            var a = Ext.create('Techsupport.model.RoleFunc', {
                                "funccode": r.data.id,
                                "funcname": r.data.funcname,
                                "function": r.data
                            })
                            return a
                        }))
                        allFunctionGrid.getStore().remove(selection)
                    }
                }
            },
            'relateFunc button[action=allRight]': { //全部右移
                click: function (button) {
                    var relateFunc = button.up('window')
                    var allFunctionGridStore = relateFunc.down('grid[name=allFunctionGrid]').getStore()
                    //  全部又移动未完
                    if (!Ext.isEmpty(allFunctionGridStore.data)) {
                        var selectedFunctionGridStore = relateFunc.down('grid[name=selectedFunctionGrid]').getStore()
                        selectedFunctionGridStore.add(Ext.Array.Map(allFunctionGridStore.getRange(), function (r) {
                            var a = Ext.create('Techsupport.model.RoleFunc', {
                                "funccode": r.data.id,
                                "funcname": r.data.funcname,
                                "function": r.data
                            })
                            return a;
                        }))
                        allFunctionGridStore.removeAll()
                    }
                }
            },
            'relateMenu button[action=enter]': {
                click: function (button) {
                    var form = button.up('window').down('form')
                    this.relateMenu(form)
                }
            }, 'relateMenu button[action=cancel]': {
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
        var selection = this.getRoleListGrid().getSelectionModel().getSelection()
        if (selection.length > 0) {
            var _window = this.getView("sysadmin.role.RelateMenu").create()
            var form = _window.down('form')
            var roleLstGrid = form.down('rolelist')
            var roleStore = roleLstGrid.getStore()
            roleStore.add(selection)
            _window.show()
            roleLstGrid.getSelectionModel().selectAll()
            //初始化角色关联菜单内容
            var menutree = form.down('menutree')
            menutree.getRootNode().expand(true, function () {
                var params = {}
                var selection = roleLstGrid.getSelectionModel().getSelection()
                Ext.Array.each(selection, function (r, idx) {
                    params['roleIds[' + idx + ']'] = r.data.id
                })
                Ext.Ajax.request({
                    url: '/api/roles/relateMenus',
                    method: 'GET',
                    params: params,
                    success: function (response) {
                        var res = Ext.decode(response.responseText)
                        if (res.data && res.data.length > 0) {
                            Ext.Array.each(res.data, function (m) {
                                var node = menutree.getStore().getNodeById(m.id)
                                menutree.getSelectionModel().select(node, true)
                            })
                        }
                    },
                    failure: function (response) {
                        Ext.Msg.alert('错误', '获取关联菜单列表发生错误')
                    }
                })
            })
        }
        else {
            Ext.Msg.alert("提示", '请选择要操作的角色记录')
        }

    },
    relateMenu: function (form) { //关联菜单操作
        if (form.isValid()) {

            var store = form.down('menutree[name=selectedMenuGrid]').getSelectedStore()
            var removedMenus = Ext.Array.map(store.getRemovedRecords(),
                function (record) {
                    return record.data.id
                })
            var addedMenus = Ext.Array.map(store.getNewRecords(),
                function (record) {
                    return record.data.id
                })

            var roleIds = Ext.Array.map(form.down('rolelist').getSelectionModel().getSelection(),
                function (record) {
                    return record.data.id
                })
            var params = {}
            Ext.Array.each(roleIds, function (r, idx) { //角色id列表
                params['roleIds[' + idx + ']'] = r;
            })
            Ext.Array.each(addedMenus, function (r, idx) { //移除的关联菜单
                params['removedMenuIds[' + idx + ']'] = r
            })
            Ext.Array.each(removedMenus, function (r, idx) {//添加的关联菜单
                params['addedMenuIds[' + idx + ']'] = r
            })
            form.submit({
                url: '/api/roles/relateMenus',
                params: params,
                method: 'POST',
                success: function (basicForm, result) {
                    form.up('window').close()
                },
                failure: function (form, result) {
                    Ext.Msg.alert("错误", "关联菜单发生错误")
                },
                scope: this
            })
        }
    },
    toRelateFunction: function () {//打开角色关联功能窗口
        var selection = this.getRoleListGrid().getSelectionModel().getSelection()
        if (selection.length > 0) {
            var _window = this.getView("sysadmin.role.RelateFunc").create()
            var form = _window.down('form')
            var rolelist = form.down('rolelist')
            var rolelistStore = rolelist.getStore()
            rolelistStore.add(selection)

            _window.show()
            rolelist.getSelectionModel().selectAll()
        }
        else {
            Ext.Msg.alert("提示", "请选择要操作的角色记录")
        }
    },
    relateFunction: function (form) { //关联功能操作
        if (form.isValid()) {
            var roleIds = Ext.Array.map(form.down('rolelist').getSelectionModel().getSelection(),
                function (record) {
                    return record.data.id
                })
            var store = form.down('grid[name=selectedFunctionGrid]').getStore()
            var removedFunctions = Ext.Array.map(store.getRemovedRecords(),
                function (record) {
                    return record.data.funccode
                })
            var addedFunctions = Ext.Array.map(store.getNewRecords(),
                function (record) {
                    return record.data.funccode
                })
            var params = {};
            Ext.each(roleIds, function (r, idx) {
                params['roleIds[' + idx + ']'] = r
            })
            Ext.each(removedFunctions, function (r, idx) {
                params['removedFunctionIds[' + idx + ']'] = r
            })
            Ext.each(addedFunctions, function (r, idx) {
                params['addedFunctionsIds[' + idx + ']'] = r
            })
            form.submit({
                url: '/api/roles/relateFunctions',
                method: 'POST',
                params: params,
                success: function (basicForm, result) {
                    store.commitChanges()
                    form.up('window').close()
                },
                failure: function (form, result) {
                    store.rejectChanges()
                    Ext.Msg.alert('错误', '关联功能发生错误')
                },
                scope: this
            })
        }
    }
})