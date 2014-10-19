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
        {ref: 'queryForm', selector: 'dictItemManage form:first'},
        {ref: "dictListGrid", selector: 'dictItemManage panel[west] dictList'},
        {ref: 'simpleListGrid', selector: 'dictItemManage dictItemSimpleList'},
        {ref: 'treeListGrid', selector: 'dictItemManage dictItemTreeList'}
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
            'dictItemManage dictItemSimpleList': { //简单类型列表

            },
            'dictItemManage dictItemTreeList': {//树形类型列表

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
            'dictItemManage button[action=query]': {//管理界面查询按钮
                click: function (button) {
                    this.queryDictItem()
                }
            }
        })

        //跳转到添加页面
        this.toAddDictItem = function () {
            var _window = this.getView('sysadmin.dictItem.Detail').create({
                title: '字典项新增'
            })

        }
        //添加字典项方法
        this.addDictItem = function (form) {

        }

        //跳转修改页面
        this.toModifyDictItem = function (record) {
        }
        //修改字典项
        this.modifyDictItem = function (form) {

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
                            grid.getView().getStore().remove(record)
                            Ext.Ajax.request({
                                url: '/api/dictitems/' + record.data.id,
                                method: 'DELETE',
                                success: function (response) {

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
            var store = this.getDictItemStore()
            var treeStore = this.getDictItemTreeStore()

            var dictSelection = this.getDictListGrid().getSelectionModel().getSelection()
            if (dictSelection.length == 0) {
                Ext.Msg.alert("提示", '请在左侧选择一个字典')
            }
            else {
                var dictRecord = dictSelection[0]
                if (dictRecord.data.dictType == "01") {
                    if (form.getForm().isValid()) {
                        Ext.apply(store.getProxy().extraParams, form.getForm().getValues())
                        store.load()
                    }
                }
                else if (dictRecord.data.dictType == "02") {
                    if (form.getForm().isValid()) {
                        Ext.apply(treeStore.getProxy().extraParams, form.getForm().getValues())
                        treeStore.load()
                    }
                }
            }
        }
    }
})
