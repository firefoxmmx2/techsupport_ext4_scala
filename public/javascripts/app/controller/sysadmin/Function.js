/**
 * 功能控制器
 */
Ext.define('Techsupport.controller.sysadmin.Function', {
    extend: 'Ext.app.Controller',
    stores: ['Function'],
    views: [
        'sysadmin.function.Detail',
        'sysadmin.function.List',
        'sysadmin.function.Manage'
    ],
    refs: [
        {ref: 'queryForm', selector: 'functionmanage panel[region=center] form'},
        {ref: 'functionListGrid', selector: 'functionmanage panel[region=center] functionlist'}
    ],
    models: ['Function'],
    init: function () {
        this.control({
            'functionmanage functionlist':{
                itemdblick: function (self, record) {
                    this.toEditFunction(record)
                }
            },
            'functionmanage button[action=query]':{
                click: function (button) {
                    this.queryFunction()
                }
            },
            'functionmanage button[action=add]':{
                click: function (button) {
                    this.toEditFunction()
                }
            },
            'functionmanage button[action=modify]':{
                click: function (button) {
                    Ext.Array.each(this.getFunctionListGrid().getSelectionModel().getSelection(), function (record) {
                        this.toEditFunction(record)
                    },this)
                }
            },
            'functionmanage button[action=remove]':{
                click: function (button) {
                    this.removeFunction()
                }
            },
            'functiondetail button[action=enter]':{
                click: function (button) {
                    var _window=button.up('window')
                    if("add"==_window._type)
                        this.addFunction(_window.down('form'))
                    else if("modify"==_window._type)
                        this.updateFunction(_window.down('form'))
                }
            },
            'functiondetail button[action=cancel]':{
                click: function (button) {
                    button.up('window').close()
                }
            }
        })
    },
    toEditFunction: function (record) {
        var windowConfig = {}
        if (record) {
            windowConfig.title = "功能修改"
            windowConfig._type="modify"
        }
        else {
            windowConfig.title = "功能新增"
            windowConfig._type="add"
        }
        var _window = this.getView("sysadmin.view.function.Detail").create(windowConfig)
        var form = _window.down('form')
        if (!record) {
            form.loadRecord(this.getFunctionModel().create())
        }
        else{
            form.down('textfield[name=id]').originalValue=record.data.id
            form.loadRecord(record)
        }
        _window.show()
    },
    addFunction: function (form) {
        var store = this.getFunctionListGrid().getStore()
        if (form.isValid()) {
            form.updateRecord()
            store.add(form.getRecord())
            store.sync({
                success: function (batch, options) {
                    store.commitChanges()
                    this.queryFunction()
                },
                failure: function (batch, options) {
                    store.rejectChanges()
                    Ext.Msg.alert("错误", "功能新增发生错误")
                },
                scope: this
            })
        }
    },
    updateFunction: function (form) {
        var store = this.getFunctionListGrid().getStore()
        if (form.isValid()) {
            form.updateRecord()
            store.sync({
                success: function (batch, options) {
                    store.commitChanges()
                },
                failure: function (batch, option) {
                    store.rejectChanges()
                    Ext.Msg.alert("错误", "功能修改发生错误")
                }
            })
        }
    },
    removeFunction: function () {
        var store = this.getFunctionListGrid().getStore()
        var selection = Ext.Array.map(this.getFunctionListGrid().getSelectionModel().getSelection(), function (record) {
            store.remove(record)
            return record
        }, this)
        if (selection.length > 0) {
            store.sync({
                success: function (batch, options) {
                    store.commitChanges()
                    if (store.getProxy().pageSize > store.getTotalCount()) {
                        this.queryFunction()
                    }
                },
                failure: function (batch, options) {
                    store.rejectChanges()
                    Ext.Msg.alert("错误", "功能删除发生错误")
                },
                scope: this
            })
        }
    },
    queryFunction: function () {
        var form = this.getQueryForm()
        var store = this.getFunctionListGrid().getStore()
        if (form.isValid()) {
            Ext.apply(store.getProxy().extraParams, form.getValues())
            store.load()
        }
    }
})