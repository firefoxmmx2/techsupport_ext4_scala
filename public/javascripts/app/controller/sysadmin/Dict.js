/**
 * Created by hooxin on 14-10-9.
 */
/**
 * 字典控制器
 */
Ext.define('Techsupport.controller.sysadmin.Dict', {
    extend: 'Ext.app.Controller',
    stores: [
        'Dict',
        'DictItem',
        'DictItemTree',
        'DictMaintFlag',
        'DictType',
        'DictItemDisplayFlag',
        'YN',
        'OneZero'
    ],
    views: [
        'sysadmin.dict.Manage',
        'sysadmin.dict.List',
        'sysadmin.dict.Detail',
        'sysadmin.dictItem.DictItemSimpleList',
        'sysadmin.dictItem.DictItemTreeList',
        'sysadmin.dictItem.Detail'
    ],
    models: [
        'Dict',
        'DictItem'
    ],
    refs: [
        {ref: 'queryForm', selector: 'dictManage form:first'},
        {ref: 'dictListGrid', selector: 'dictManage dictList'}
    ],
    init: function () {
        this.control({
            'dictManage dictList': {
                itemdblclick: function (g, record) {
                    this.toEditDict(record)
                }
            },
            'dictManage button[action=query]': {
                //查询按钮
                click: function () {
                    this.queryDict()
                }
            },
            'dictManage button[action=add]': {
                click: function () {
//                    添加按钮
                    this.toEditDict()
                }
            },
            'dictManage button[action=modify]': {
                click: function (button, evt) {
                    //修改按钮
                    var selection = Ext.Array.map(button.up('dictManage').down('dictList').getSelectionModel().getSelection(),
                        function (record) {
                            this.toEditDict(record)
                            return record
                        }, this)
                }
            },
            'dictManage button[action=remove]': {
                click: function (button, evt) {
                    //删除按钮
                    this.removeDict(button.up('dictManage').down('dictList'))
                }
            },
            'dictManage button[action=up]': {
                click: function (button, evt) {

                }
            },
            'dictManage button[action=down]': {
                click: function (button, evt) {

                }
            },
            'dictDetail dictItemSimpleList': {//字典项简单列表
                afterrender: function (p) {
                    p.setHeight(p.up('window').getHeight() * 1.5 + p.down('pagingtoolbar').getHeight())
                    p.down('pagingtoolbar').add([
                        '->',
                        {xtype: 'button', text: '添加', action: 'add'},
                        {xtype: 'button', text: '删除', action: 'remove'}
                    ])
                    p.down('button[action=add]').on('click', function (button) { //字典项添加按钮
                        var dictFrom = p.up('window').down('form:first')
                        if (dictFrom.getForm().isValid()) {
                            this.toEditDictItem(null, p)
                        }

                    }, this)
                    p.down('button[action=remove]').on('click', function (button) {//字典项删除按钮
                        this.removeDictItem(p)
                    }, this)
                }
            },
            'dictDetail dictItemTreeList': { //字典项树形列表
                afterrender: function (p) {
                    p.setHeight(p.up('window').getHeight() * 2)
                }
            },
            'dictDetail button[action=enter]': { //保存或者修改
                click: function (button) {
                    var _window = button.up('window')
                    if (_window.name == "addDictWindow") {
                        this.addDict(_window.down('form'), this.getDictStore())
                    }
                    else if (_window.name == 'modifyDictWindow') {
                        this.updateDict(_window.down('form'), this.getDictStore())
                    }
                }
            },
            'dictDetail button[action=cancel]': {//点击取消按钮关闭窗口
                click: function (button) {
                    button.up('window').close()
                }
            },
            'dictDetail textfield[name=dictcode]': { //字典代码
                change: function (field, newValue, oldValue) {
//                    可用性验证
                    if (newValue && field.originalValue != newValue) {
                        Ext.Ajax.request({
                            url: '/api/dicts/checkDictCodeAvaliable/' + newValue,
                            success: function (response) {
                                var res = Ext.decode(response.responseText)
                                if (res.isAvaliable) {
                                    this.textValid = true
                                    this.clearInvalid()
                                }
                                else {
                                    this.textValid = '该字典代码不可用'
                                    this.markInvalid(this.textValid)
                                }
                            },
                            failure: function (response) {
                                this.textValid = '系统服务端字典代码验证错误'
                                this.markInvalid(this.textValid)
                            },
                            scope: field
                        })
                    }
                    else {
                        field.textValid = true
                        field.clearInvalid()
                    }
                }
            }
        })
        this.removeDictItem = function (p) {
            var store = p.getStore()
            var selection = Ext.Array.map(p.getSelectionModel().getSelection(), function (record) {
                store.remove(record)
                return record
            })
        }
        this.toEditDictItem = function (record, p) {
            //打开窗口
            var config = {}
            if (record) {
                config.title = "字典项修改"
                config.name = "modifyDictItemWindow"
            }
            else {
                config.title = "字典项新增"
                config.name = "addDictItemWindow"
            }

            var _window = this.getView('sysadmin.dictItem.Detail').create(config)
            _window.show()
            var form = _window.down('form')
            var dictCodeField = form.down('textfield[name=dictcode]').setReadOnly(true)
            //确定按钮
            _window.down('button[action=enter]').on('click', function (button) {
                if (form.getForm().isValid()) {
                    form.getForm().updateRecord()
                    if (record) {
                        _window.close()
                    }
                    else {
                        p.getStore().add(form.getForm().getRecord())
                        var _record = this.getDictItemModel().create({
                            superItemId: 0,
                            dictcode: dictWindow.down('textfield[name=dictcode]').getValue()
                        })
                        form.getForm().loadRecord(_record)
                    }

                }
            }, this)
            //取消按钮关闭窗口
            _window.down('button[action=cancel]').on('click', function (button) {
                button.up('window').close()
            }, this)
            var dictWindow = p.up('window')
            if (!record) {
                var _record = this.getDictItemModel().create({
                    superItemId: 0,
                    dictcode: dictWindow.down('textfield[name=dictcode]').getValue()
                })
                form.getForm().loadRecord(_record)
            }
            else {
                form.getForm().loadRecord(record)
            }


        }
        this.toEditDict = function (record) {
            //打开修改窗口
            var config = {width: 800}
            if (record) {
                config.title = "字典修改[" + record.data.dictname + "]"
                config.name = "modifyDictWindow"
            }
            else {
                config.title = "字典新增"
                config.name = "addDictWindow"
                record = this.getDictModel().create()
            }

            var _window = this.getView("sysadmin.dict.Detail").create(config)
            var form = _window.down('form')
            _window.show()
            if (config.name == "addDictWindow") {
                Ext.Ajax.request({
                    url: '/api/dicts/maxDictOrder',
                    success: function (response) {
                        var res = Ext.decode(response.responseText)
                        if (Ext.isNumber(res.data)) {
                            record.data.sibOrder = res.data + 1
                            form.getForm().loadRecord(record)
                        }
                    },
                    failure: function (response) {
                        Ext.Msg.alert("错误", "获取字典最大序列发生错误")
                    },
                    scope: this
                })
            }
            form.down('textfield[name=dictcode]').originalValue = record.data.dictcode
            form.getForm().loadRecord(record)
            var dictTypeField = form.down('textfield[name=dictType]')

            if (dictTypeField.getValue() == '01') {
                var dictItemSimplePanel = form.down('dictItemSimpleList')
                dictItemSimplePanel.show()
                var store = dictItemSimplePanel.getStore()
                if (record.data.dictcode) {
                    Ext.apply(store.getProxy().extraParams, {dictcode: record.data.dictcode})
                    store.load()
                }

            }
            else if (dictTypeField.getValue() == '02') {
                var dictItemTreePanel = form.down('dictItemTreeList')
                var store = dictItemTreePanel.getStore()

            }

        }
        this.addDict = function (form, store) {
            //新增字典
            if (form.getForm().isValid()) {
                form.getForm().updateRecord()
                var record = form.getForm().getRecord()
                store.add(record)
                var extraParams = store.getProxy().extraParams
                store.getProxy().extraParams = {}
                store.sync({
                    success: function (batch, options) {
                        store.commitChanges()
                        this.queryDict()
                        var _window = form.up('window')
                        var dictItemListGrid = _window.down('dictItemSimpleList')
                        var dictItemStore = dictItemListGrid.getStore()
                        var dictItemStoreExtraParams = dictItemStore.getProxy().extraParams
                        dictItemStore.getProxy().extraParams = {}
                        dictItemStore.sync({
                            success: function (batch, options) {
                                dictItemStore.commitChanges()
                            },
                            failure: function (batch, options) {
                                dictItemStore.rejectChanges()
                            },
                            scope: this
                        })
                        dictItemStore.getProxy().extraParams = dictItemStoreExtraParams
                        _window.close()

                    },
                    failure: function (batch, options) {
                        store.rejectChanges()
                    },
                    scope: this
                })
                store.getProxy().extraParams = extraParams
            }
        }
        this.updateDict = function (form, store) {
            //修改字典
            if (form.getForm().isValid()) {
                form.getForm().updateRecord()
                if (store.getUpdatedRecords().length > 0) {
                    var extraParams = store.getProxy().extraParams
                    store.getProxy().extraParams = {}
                    store.sync({
                        success: function (batch, options) {
                            store.commitChanges()
                            this.queryDict()
                            var _window = form.up('window')
                            var dictItemListGrid = _window.down('dictItemSimpleList')
                            var dictItemStore = dictItemListGrid.getStore()
                            var dictItemStoreExtraParams = dictItemStore.getProxy().extraParams
                            dictItemStore.getProxy().extraParams = {}
                            dictItemStore.sync({
                                success: function (batch, options) {
                                    dictItemStore.commitChanges()
                                },
                                failure: function (batch, options) {
                                    dictItemStore.rejectChanges()
                                },
                                scope: this
                            })
                            dictItemStore.getProxy().extraParams = dictItemStoreExtraParams
                            _window.close()

                        },
                        failure: function (batch, options) {
                            store.rejectChanges()
                        },
                        scope: this
                    })
                    store.getProxy().extraParams = extraParams
                }
                else {
                    var _window = form.up('window')
                    var dictItemListGrid = _window.down('dictItemSimpleList')
                    var dictItemStore = dictItemListGrid.getStore()
                    var dictItemStoreExtraParams = dictItemStore.getProxy().extraParams
                    dictItemStore.getProxy().extraParams = {}
                    dictItemStore.sync({
                        success: function (batch, options) {
                            dictItemStore.commitChanges()
                        },
                        failure: function (batch, options) {
                            dictItemStore.rejectChanges()
                        },
                        scope: this
                    })
                    dictItemStore.getProxy().extraParams = dictItemStoreExtraParams
                    _window.close()
                }

            }
        }
        this.queryDict = function () {
            //查询
            var form = this.getQueryForm()
            var store = this.getDictStore()
            Ext.apply(store.getProxy().extraParams, form.getForm().getValues())
            store.load()
        }
        this.removeDict = function (grid) {
            var store = grid.getStore()
            var selection = Ext.Array.map(grid.getSelectionModel().getSelection(), function (record) {
                store.remove(record)
                return record
            }, this)
            if (selection.length > 0) {
                var extraparams = store.getProxy().extraParams
                store.getProxy().extraParams = {}
                store.sync({
                    success: function (batch, options) {
                        store.commitChanges()
                        if (store.getProxy().pageSize > store.getTotalCount()) {
                            this.queryDict()
                        }
                    },
                    failure: function (batch, options) {
                        store.rejectChanges()
                    },
                    scope: this
                })
                store.getProxy().extraParams = extraparams
            }
        }
    }
})
;