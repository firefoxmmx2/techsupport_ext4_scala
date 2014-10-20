/**
 * Created by hooxin on 14-10-12.
 */
Ext.define('Techsupport.controller.sysadmin.DictItem', {
    extend: 'Ext.app.Controller',
    stores: [
        'DictItem',
        'DictItemTree'
    ],
    views: [
        'sysadmin.dictItem.Detail',
        'sysadmin.dictItem.DictItemSimpleList',
        'sysadmin.dictItem.DictItemTreeList',
        'sysadmin.dict.List',
        'sysadmin.dictItem.Manage'
    ],
    models: [
        'DictItem',
        'Dict'
    ],
    refs: [
        {ref: 'queryForm', selector: 'dictItemManage panel[region=center] form'},
        {ref: "dictListGrid", selector: 'dictItemManage panel[region=west] dictList'},
        {ref: 'simpleListGrid', selector: 'dictItemManage dictItemSimpleList'},
        {ref: 'treeListGrid', selector: 'dictItemManage dictItemTreeList'},
        {ref: 'manage', selector: 'dictItemManage'}
    ],
    init: function () {
        this.control({
            'dictItemManage dictList': {
                select: function (rowModel, record, index, eOpts) {
                    if (record.data.dictType == "01") {
                        this.getSimpleListGrid().show()
                        this.getTreeListGrid().hide()
                        this.queryDictItem()
                    }
                    else if (record.data.dictType == "02") {
                        this.getSimpleListGrid().hide()
                        this.getTreeListGrid().show()
                        this.queryDictItem()
                    }
                }
            },
            'dictItemManage dictList textfield[name=queryfield]': {//左边侧栏快捷查询输入框
                specialkey: function (field, evt) {
                    if (evt.getKey() == evt.ENTER) {
                        var button = field.up('dictList').down('button[action=query]')
                        button.fireEvent('click', button)
                    }
                }
            },
            'dictItemManage dictList button[action=query]': {//左边侧栏查询按钮
                click: function (button) {
                    var form = button.up("dictList").down('form')
                    if (form.isValid()) {
                        var store = form.up('dictList').getStore()
                        Ext.apply(store.getProxy().extraParams, form.getValues())
                        store.load()
                    }
                }
            },
            'dictItemManage dictItemSimpleList': { //简单类型列表
                itemdblclick: function (g, record) { //双击打开修改界面
                    this.toModifyDictItem(record)
                }
            },
            'dictItemManage dictItemTreeList': {//树形类型列表
                render: function (tree) {
                    tree.refresh = function (nodeId) {
                        //刷新
                        var node = this.getStore().getNodeById(nodeId);
                        this.getStore().load({
                            node: node
                        });
                    }
                },
                beforeload: function (store, operation, opts) {
                    var tree = store.getRootNode().getOwnerTree();
                    if (tree.getSelectionModel().getSelection().length > 0)
                        store.getProxy().setExtraParam("superItemId", tree.getSelectionModel().getSelection()[0].data.id);
                },
                beforeitemexpand: function (n, opts) {
                    var tree = n.getOwnerTree();
                    tree.getSelectionModel().select(n)
                },
                itemdblclick: function (t, record, item, index, e, eOpts) { //双击打开修改界面
                    this.toModifyDictItem(record)
                }
            },
            'dictItemManage button[action=add]': { //管理界面添加按钮
                click: function (button) {
                    this.toAddDictItem()
                }
            },
            'dictItemManage button[action=modify]': {//管理界面修改按钮
                click: function (button) {
                    var selection = this.getDictListGrid().getSelectionModel().getSelection()
                    if (selection.length == 0) {
                        Ext.Msg.alert("提示", '请在左侧选择一个字典')
                    }
                    else {
                        var dictRecord = selection[0]
                        var grid = null
                        if (dictRecord.data.dictType == "01") {
                            grid = this.getSimpleListGrid()
                        }
                        else if (dictRecord.data.dictType == "02") {
                            grid = this.getTreeListGrid()
                        }
                        Ext.Array.each(grid.getSelectionModel().getSelection(),
                            function (record) {
                                this.toModifyDictItem(record)
                            }, this)
                    }

                }
            },
            'dictItemManage button[action=remove]': {//管理界面删除按钮
                click: function (button) {
                    var dictSelection = this.getDictListGrid().getSelectionModel().getSelection()
                    if (dictSelection.length == 0) {
                        Ext.Msg.alert("提示", '请在左侧选择一个字典')
                    }
                    else {
                        var dictRecord = dictSelection[0]
                        var grid = null
                        if (dictRecord.data.dictType == "01") {
                            grid = this.getSimpleListGrid()
                        }
                        else if (dictRecord.data.dictType == "02") {
                            grid = this.getTreeListGrid()
                        }

                        this.removeDictItem(grid)
                    }
                }
            },
            'dictItemManage button[action=up]': {//管理界面上移按钮
                click: function (button) {

                }
            },
            'dictItemManage button[action=down]': {//管理界面下移按钮

            },
            'dictItemManage panel[region=center] button[action=query]': {//管理界面查询按钮
                click: function (button) {
                    this.queryDictItem()
                }
            },
            'dictItemDetail button[action=cancel]': {//详情窗口取消
                click: function (button) {
                    button.up('window').close()
                }
            },
            'dictItemDetail[name=addDictItemWindow] button[action=enter]': { //添加字典项窗口的确认
                click: function (button) {
                    var _window = button.up('window')
                    var form = _window.down('form')
                    this.addDictItem(form)
                }
            },
            'dictItemDetail[name=modifyDictItemWindow] button[action=enter]': {//修改字典项窗口的确认
                click: function (button) {
                    var _window = button.up('window')
                    var form = _window.down('form')
                    this.modifyDictItem(form)
                }
            }
        })

        //跳转到添加页面
        this.toAddDictItem = function () {
            var dictListGrid = this.getDictListGrid()
            var selection = dictListGrid.getSelectionModel().getSelection()
            if (selection.length == 0) {
                Ext.Msg.alert("提示", '请在左侧选择一个字典')
            }
            else {
                var dictRecord = selection[0]

                var _window = this.getView('sysadmin.dictItem.Detail').create({
                    title: '字典项新增',
                    name: 'addDictItemWindow'
                })
                var form = _window.down('form')
                var config = {dictcode: dictRecord.data.dictcode}
                if (!this.getTreeListGrid().isHidden()) {
                    var selection = this.getTreeListGrid().getSelectionModel().getSelection()
                    if (selection.length > 0)
                        config.superItemId = selection[0].data.id
                }
                var record = this.getDictItemModel().create(config)
                Ext.Ajax.request({
                    url: '/api/dictitems/maxDictItemOrder/' + record.data.dictcode + '/' + record.data.superItemId,
                    success: function (response) {
                        var res = Ext.decode(response.responseText)
                        if (Ext.isNumber(res.data)) {
                            record.data.sibOrder = res.data + 1
                            form.loadRecord(record)
                        }
                    },
                    failure: function (response) {
                        Ext.Msg.alert('错误', '获取字典项最大序列发生错误')
                    },
                    scope: this
                })
                _window.show()
                form.loadRecord(record)
            }

        }
        //添加字典项方法
        this.addDictItem = function (form) {
            if (form.isValid()) {
                var simpleListGrid = this.getManage().down('dictItemSimpleList')
                var treeListGrid = this.getManage().down('dictItemTreeList')
                if (!simpleListGrid.isHidden()) {
                    form.updateRecord()
                    var store = simpleListGrid.getStore()
                    store.add(form.getRecord())

                    store.sync({
                        success: function (batch, options) {
                            store.commitChanges()
                        },
                        failure: function (batch, options) {
                            store.rejectChanges()
                            Ext.Msg.alert("错误", "新增字典项发生错误")
                        },
                        scope: this
                    })
                }
                else if (!treeListGrid.isHidden()) {
                    form.submit({
                        url: '/api/dictitems/0',
                        method: 'POST',
                        params: form.getValues(),
                        success: function (basicForm, action) {
                            if (treeListGrid.getSelectionModel().getSelection().length > 0) {
                                var node = treeListGrid.getStore().getNodeById(treeListGrid.getSelectionModel().getSelection()[0].data.id)
                                node.data.leaf = false
                                treeListGrid.refresh(treeListGrid.getSelectionModel().getSelection()[0].data.id)
                            }

                            form.up('window').close()
                        },
                        failure: function (basicForm, action) {
                            Ext.Msg.alert("错误", "新增字典项发生错误")
                        },
                        scope: this
                    })
                }

            }
        }

        //跳转修改页面
        this.toModifyDictItem = function (record) {
            var _window = this.getView("sysadmin.dictItem.Detail").create({
                title: "字典项修改[" + record.data.displayName + "]",
                name: "modifyDictItemWindow"
            })

            var form = _window.down('form')
            _window.show()
            form.loadRecord(record)
        }
        //修改字典项
        this.modifyDictItem = function (form) {
            if (form.isValid()) {
                var simpleListGrid = this.getManage().down('dictItemSimpleList')
                var treeListGrid = this.getManage().down('dictItemTreeList')

                if (!simpleListGrid.isHidden()) {
                    var store = simpleListGrid.getStore()
                    form.updateRecord()
                    store.sync({
                        success: function (batch, opts) {
                            store.commitChanges()
                        },
                        failure: function (batch, opts) {
                            store.rejectChanges()
                            Ext.Msg.alert("错误", "字典项修改发生错误")
                        }
                    })
                }
                else if (!treeListGrid.isHidden()) {
                    form.submit({
                        url: '/api/dictitems/' + form.down('textfield[name=id]').getValue(),
                        method: 'PUT',
                        success: function (basicForm, action) {
                            if (this.getDictListGrid().getSelectionModel().getSelection().length > 0) {
                                treeListGrid.refresh(this.getDictListGrid().getSelectionModel().getSelection()[0].data.id)
                            }
                            form.up('window').close()
                        },
                        failure: function (basicForm, action) {
                            Ext.Msg.alert("错误", "字典项修改发生错误")
                        },
                        scope: this
                    })
                }
            }
        }

        //删除字典项
        this.removeDictItem = function (grid) {
            if (grid) {
                var selection = Ext.Array.map(grid.getSelectionModel().getSelection(),
                    function (record) {
                        if (grid.xtype == "dictItemSimpleList") {
                            grid.getStore().remove(record)
                        }
                        else if (grid.xtype == "dictItemTreeList") {
                            var store = grid.getStore()
                            Ext.Ajax.request({
                                url: '/api/dictitems/' + record.data.id,
                                method: 'DELETE',
                                success: function (response) {
                                    var parentNode = store.getNodeById(record.data.superItemId)
                                    var node = store.getNodeById(record.data.id)
                                    if (parentNode.childNodes.length == 1) {
                                        grid.getSelectionModel().select(parentNode)
                                        grid.refresh(parentNode.data.id)
                                    }
                                    else {
                                        parentNode.removeChild(node)
                                    }
                                },
                                failure: function (response) {
                                    Ext.Msg.alert("错误", "删除字典项[" + record.data.displayName + "]")
                                },
                                scope: this
                            })
                        }
                        return record
                    })
                if (selection.length > 0) {
                    if (grid.xtype == "dictItemSimpleList") {
                        grid.getStore().sync({
                            success: function (batch, options) {
                                grid.getStore().commitChanges()
                                if (grid.getStore().pageSize > grid.getStore().getTotalCount())
                                    this.queryDictItem()
                            },
                            failure: function (batch, options) {
                                grid.getStore().rejectChanges()
                                Ext.Msg.alert('错误', '删除字典项发生错误')
                            },
                            scope: this
                        })
                    }
                }
            }
        }

        //查询字典项
        this.queryDictItem = function () {
            var form = this.getQueryForm()
            var store = this.getSimpleListGrid().getStore()
            var treeStore = this.getTreeListGrid().getStore()

            var dictSelection = this.getDictListGrid().getSelectionModel().getSelection()
            if (dictSelection.length == 0) {
                Ext.Msg.alert("提示", '请在左侧选择一个字典')
            }
            else {
                var dictRecord = dictSelection[0]
                form.down('textfield[name=dictcode]').setValue(dictRecord.data.dictcode)
                if (dictRecord.data.dictType == "01") {
                    if (form.isValid()) {
                        Ext.apply(store.getProxy().extraParams, form.getValues())
                        store.load()
                    }
                }
                else if (dictRecord.data.dictType == "02") {
                    if (form.isValid()) {
                        Ext.apply(treeStore.getProxy().extraParams, form.getValues())
                        treeStore.load()
                    }
                }
            }
        }
    }
})
