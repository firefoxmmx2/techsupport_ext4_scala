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
            'functionmanage': {
                afterrender: function (p) {
                    var queryBtn = p.down('panel[region=center] button[action=query]')
                    queryBtn.fireEvent('click', queryBtn)
                }
            },
            'functionmanage panel[region=center] functionlist': {
                itemdblclick: function (self, record) {
                    this.toEditFunction(record)
                }
            },
            'functionmanage button[action=query]': {
                click: function (button) {
                    this.queryFunction()
                }
            },
            'functionmanage button[action=add]': {
                click: function (button) {
                    this.toEditFunction()
                }
            },
            'functionmanage button[action=modify]': {
                click: function (button) {
                    Ext.Array.each(this.getFunctionListGrid().getSelectionModel().getSelection(), function (record) {
                        this.toEditFunction(record)
                    }, this)
                }
            },
            'functionmanage button[action=remove]': {
                click: function (button) {
                    this.removeFunction()
                }
            },
            'functiondetail button[action=enter]': {
                click: function (button) {
                    var _window = button.up('window')
                    if ("add" == _window._type)
                        this.addFunction(_window.down('form'))
                    else if ("modify" == _window._type)
                        this.updateFunction(_window.down('form'))
                }
            },
            'functiondetail button[action=cancel]': {
                click: function (button) {
                    button.up('window').close()
                }
            },
            'functiondetail textfield[name=id]':{
                change: function (field, newValue, oldValue) {
                    if(newValue && newValue != field.originalValue){
                        Ext.Ajax.request({
                            url:'/api/functions/checkIDAvailable/'+newValue,
                            success: function (response) {
                                var res=Ext.decode(response.responseText)
                                if(res.isAvailable) {
                                    this.textValid = true
                                    this.clearInvalid()
                                }
                                else{
                                    this.textValid="该功能代码不可用"
                                    this.markInvalid(this.textValid)
                                }
                            },
                            failure: function (response) {
                                this.textValid="功能代码可用验证发生错误"
                                this.markInvalid(this.textValid)
                                Ext.Msg.alert('错误','功能代码可用验证发生错误')
                            },
                            scope:field
                        })
                    }
                    else{
                        field.textValid=true
                        field.clearInvalid()
                    }
                }
            }
        })
    },
    toEditFunction: function (record) {
        var windowConfig = {}
        if (record) {
            windowConfig.title = "功能修改"
            windowConfig._type = "modify"
        }
        else {
            windowConfig.title = "功能新增"
            windowConfig._type = "add"
        }
        var _window = this.getView("sysadmin.function.Detail").create(windowConfig)
        var form = _window.down('form')
        if (!record) {
            form.loadRecord(this.getFunctionModel().create())
        }
        else {
            form.down('textfield[name=id]').originalValue = record.data.id
            form.loadRecord(record)
        }
        _window.show()
    },
    addFunction: function (form) {
        var store = this.getFunctionListGrid().getStore()
        if (form.isValid()) {
            form.updateRecord()
            store.add(form.getRecord())
            var extraParams = store.getProxy().extraParams
            store.getProxy().extraParams={}
            store.sync({
                success: function (batch, options) {
                    store.commitChanges()
                    form.up('window').close()
                    this.queryFunction()
                },
                failure: function (batch, options) {
                    store.rejectChanges()
                    Ext.Msg.alert("错误", "功能新增发生错误")
                },
                scope: this
            })
            store.getProxy().extraParams=extraParams
        }
    },
    updateFunction: function (form) {
        var store = this.getFunctionListGrid().getStore()
        if (form.isValid()) {
            form.updateRecord()
            var extraParams = store.getProxy().extraParams
            store.getProxy().extraParams = {}
            store.sync({
                success: function (batch, options) {
                    store.commitChanges()
                    form.up('window').close()
                },
                failure: function (batch, option) {
                    store.rejectChanges()
                    Ext.Msg.alert("错误", "功能修改发生错误")
                }
            })
            store.getProxy().extraParams = extraParams
        }
    },
    removeFunction: function () {
        var store = this.getFunctionListGrid().getStore()
        var selection = Ext.Array.map(this.getFunctionListGrid().getSelectionModel().getSelection(), function (record) {
            store.remove(record)
            return record
        }, this)
        if (selection.length > 0) {
            var extraParams=store.getProxy().extraParams
            store.getProxy().extraParams={}
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
            store.getProxy().extraParams=extraParams
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