/**
 * Created by hooxin on 14-10-6.
 */
Ext.define('Techsupport.controller.sysadmin.GlobalParam', {
    extend: 'Ext.app.Controller',
    stores: ['GlobalParam'],
    views: ['sysadmin.globalParam.List', 'sysadmin.globalParam.Manage', 'sysadmin.globalParam.Detail'],
    models: ['GlobalParam'],
    refs: [
        {ref: 'queryForm', selector: 'globalParamManage form:first'},
        {ref: 'saveForm', selector: "globalParamDetail form:first"},
        {ref: 'globalParamListGrid', selector: 'globalParamManage globalParamList'}
    ],
    init: function () {
        this.control({
            'globalParamList': {
                itemdblclick: function (g, record) {
                    this.toEditGlobalParam(record)
                }
            },
            'globalParamDetail button[action=enter]': {
//               确认按钮
                click: function () {
                    this.saveGlobalParam(this.getSaveForm(), this.getGlobalParamStore())
                }
            },
            'globalParamDetail button[action=cancel]': {
//               取消按钮 关闭窗口
                click: function (button) {
                    button.up('window').close()
                }
            },
            'globalParamManage button[action=add]': {
//               添加按钮
                click: function () {
                    this.toEditGlobalParam()
                }
            },
            'globalParamManage button[action=modify]': {
//               修改按钮
                click: function (button) {
                    var grid = button.up('globalParamList')
                    Ext.Array.map(grid.getSelectionModel().getSelection(), function (record) {
                        this.toEditGlobalParam(record)
                        return record
                    })
                }
            },
            'globalParamManage button[action=remove]': {
//                             删除按钮
                click: function (button, evt) {
                    var grid = this.getGlobalParamListGrid()
                    this.removeGlobalParam(grid)
                }
            },
            'globalParamManage button[action=up]': {
//               上移按钮
            },
            'globalParamManage button[action=down]': {
//               下移按钮
            },
            'globalParamManage button[action=query]': {
//               查询按钮
                click: function (button, evt) {
                    this.queryGlobalParam()
                }
            },
            'globalParamDetail textfield[name=id]': {
//                全局参数代码
                change: function (field, newValue, oldValue) {
                    if (newValue && newValue != field.originalValue ) {
//                        全局参数代码验证
                        Ext.Ajax.request({
                            url: '/api/globalParams/checkGlobalParamLAvaliable/' + newValue,
                            success: function (response) {
                                var res = Ext.decode(response.responseText)
                                if (res.isAvaliable) {
                                    this.clearInvalid()
                                    this.textValid = true
                                }
                                else {
                                    this.textValid = '该全局参数代码不可用'
                                    this.markInvalid(this.textValid)
                                }
                            },
                            failure: function (response) {
                                if (response.state == 200) {
                                    this.textValid = '该全局参数代码不可用'
                                    this.markInvalid(this.textValid)
                                    var res = Ext.decode(response.responseText)
                                    Ext.Msg.alert("错误", res.message)
                                }
                                else
                                    Ext.Msg.alert('错误', '全局参数代码可用验证服务器端发生错误')
                            },
                            scope: field
                        })

                    }
                    else {
                        field.textValid = true
                        field.clearInvalid()
                    }
                }
            },
            'globalParamDetail[name=modifyGlobalParamWindow] textfield[name=id]':{
                render: function (field) {
                    field.setReadOnly(true)
                }
            }
        })

        this.saveGlobalParam = function (form, store) {
            var window = form.up('window')
            if (form.getForm().isValid()) {
                if (form.up('window[name=addGlobalParamWindow]')) {
                    form.getForm().submit({
                        url: '/api/globalParams',
                        method: 'post',
                        params: form.getForm().getValues(),
                        success: function (form, action) {
                            if (action.result.result == 0) {
                                this.queryGlobalParam()
                                window.close()
                            }
                        },
                        failure: function (form, action) {
                            if (action.response.state == 200)
                                Ext.Msg.alert('错误', action.result.message)
                            else
                                Ext.Msg.alert('错误', '全局参数新增发生致命错误')
                        },
                        scope: this
                    })
                }
                else {
                    form.getForm().updateRecord()
                    var extraParams = store.getProxy().extraParams
                    store.getProxy().extraParams = {}
                    store.sync({
                        success: function (batch, options) {
                            store.commitChanges()
                            window.close()
                        },
                        failure: function (batch, option) {
                            store.rejectChanges()
                            Ext.Msg.alert('错误', '全局参数修改发生错误')
                        }
                    })
                    store.getProxy().extraParams = extraParams
                }

            }

        }

        this.toEditGlobalParam = function (record) {
            var config = {}
            if (record) {
                config.title = '全局参数修改[' + record.data.globalparname + ']'
                config.name = 'modifyGlobalParamWindow'
            }
            else {
                config.title = "全局参数新增"
                config.name = "addGlobalParamWindow"
                record = this.getGlobalParamModel().create()
            }
            var window = this.getView('sysadmin.globalParam.Detail').create(config)
            var form = window.down('form')
            if (config.name == 'addGlobalParamWindow') {
            }
            else {
                form.down('textfield[name=id]').originalValue = record.data.id
            }
            form.getForm().loadRecord(record)
            window.show()

        }

        this.queryGlobalParam = function () {
//            查询
            var form = this.getQueryForm()
            var store = this.getGlobalParamStore()
            if (form.getForm().isValid()) {
                Ext.apply(store.getProxy().extraParams, form.getForm().getValues())
                store.load()
            }
        }
        this.removeGlobalParam = function (grid) {
//            删除
            var store = this.getGlobalParamStore()
            var selection = Ext.Array.map(grid.getSelectionModel().getSelection(), function (record) {
                store.remove(record)
                return record;
            })

            if (selection.length) {
                store.sync({
                    success: function (batch, options) {
                        store.commitChanges()
                        if (store.getProxy().pageSize > store.getTotalCount())
                            this.queryGlobalParam()
                    },
                    failure: function (batch, options) {
                        store.rejectChanges()
                    },
                    scope: this
                })
            }
        }
    }
})
