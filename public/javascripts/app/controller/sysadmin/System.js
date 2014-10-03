/**
 * Created by hooxin on 14-10-2.
 */
/**
 * 系统管理控制器
 */
Ext.define('Techsupport.controller.sysadmin.System', {
    extend: 'Ext.app.Controller',
    stores: ['System'],
    views: ['sysadmin.system.Detail', 'sysadmin.system.Manage', 'sysadmin.system.List'],
    models: ['System'],
    refs: [
        {ref: 'queryForm', selector: 'systemmanage form:first'},
        {ref: 'systemListGrid', selector: 'systemmanage systemlist'}
    ],
    init: function () {
        this.toEditSystem = function (record) {//打开编辑页面
            var config = {}
            if (record) {
                config.title = '系统修改[' + record.data.systemname + ']'
                config.name = 'modifySystemWindow'
            }
            else {
                config.title = "系统新增"
                config.name = "addSystemWindow"
                record = this.getSystemModel().create({
                    nodeorder: 0,
                    isleaf: 'Y',
                    parentsystemcode: '0',
                    parentFullcode: ''
                })
            }
            var window = this.getView('sysadmin.system.Detail').create(config)
            var form = window.down('form')
            if (config.name == 'addSystemWindow') {
                Ext.Ajax.request({
                    url: '/api/systems/maxSystemOrder',
                    success: function (response) {
                        var res = Ext.decode(response.responseText)
                        if (res.data) {
                            record.data.nodeorder = res.data + 1
                            form.getForm().loadRecord(record)
                        }
                    },
                    failure: function (response) {
                        if (response.status == 200) {
                            Ext.Msg.alert("错误", response.result.message)
                        }
                        else {
                            Ext.Msg.alert('错误', '获取系统最大序列发生致命错误')
                        }
                    }
                })
            }
            else {
                form.down('textfield[name=id]').originalValue = record.data.id
            }
            form.getForm().loadRecord(record)
            window.show()
        }
        this.saveSystem = function (form, store) {//保存系统 包括插入和修改
            var window = form.up('window')
            if (form.getForm().isValid()) {
                if (form.up('window[name=addSystemWindow]')) {
                    form.getForm().submit({
                        url: '/api/systems',
                        method: 'post',
                        params: form.getForm().getValues(),
                        success: function (form, action) {
                            if (action.result.result == 0) {
                                this.querySystem()
                                window.close()
                            }
                        },
                        failure: function (form, action) {
                            if (action.response.state == 200)
                                Ext.Msg.alert('错误', action.result.message)
                            else
                                Ext.Msg.alert('错误', '系统新增发生致命错误')
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
                            Ext.Msg.alert('错误', '系统修改发生错误')
                        }
                    })
                    store.getProxy().extraParams = extraParams
                }

            }
        }
        this.removeSystem = function (grid) {//删除选择的系统
            var store = grid.getStore()
            var selection = grid.getSelectionModel().getSelection().map(function (record) {
                store.remove(record)
                return record
            })
            if (selection.length > 0) {
                var extraParams = store.getProxy().extraParams
                store.sync({
                    success: function (batch, option) {
                        store.commitChanges()
                        if (store.getProxy().pageSize < store.getTotalCount())
                            this.querySystem()
                    },
                    failure: function (batch, option) {
                        store.rejectChanges()
                        Ext.Msg.alert('错误', '删除发生错误')
                    },
                    scope: this
                })
                store.getProxy().extraParams = extraParams
            }
        }
        this.querySystem = function () {
            var form = this.getQueryForm()
            var store = this.getSystemStore()
            if (form.getForm().isValid()) {
                Ext.apply(store.getProxy().extraParams, form.getForm().getValues())
            }
            store.load()
        }

        this.control({//控制器注入
            'systemlist': {
                render: function (g) {
                    g.getStore().removeAll()
                },
                afterlayout: function (g, layout, opts) {
                    var headerHeight = g.headerCt.down('[id*=gridcolumn]').getHeight();
                    var pagesize = Math.round(g.getHeight() / headerHeight);
                    g.getStore().pageSize = pagesize;
                    g.getStore().trailingBufferZone = pagesize;
                    g.getStore().getProxy().setExtraParam('limit', pagesize);
                },
                itemdblclick: function (g, record, item, index, e, eOpts) {
                    this.toEditSystem(record)
                }
            },
            'systemmanage button[action=query]': { //查询按钮
                click: function (button, evt) {
                    this.querySystem()
                }
            },
            'systemmanage button[action=add]': {//添加按钮
                click: function (button, evt) {
                    this.toEditSystem()
                }
            },
            'systemmanage button[action=remove]': {//删除按钮
                click: function (button, evt) {
                    this.removeSystem(this.getSystemListGrid())
                }
            },
            'systemmanage button[action=modify]': {
                click: function (button, evt) {
                    var controller = this
                    var selection = this.getSystemListGrid().getSelectionModel().getSelection().map(function (record) {
                        controller.toEditSystem(record)
                        return record
                    })
                }
            },
            'systemmanage button[action=up]': {//上移
                click: function (button, evt) {

                }
            },
            'systemmanage button[action=down]': {//下移
                click: function (button, evt) {

                }
            },
            'systemdetail button[action=enter]': {//新增或者修改确认
                click: function (button, evt) {
                    this.saveSystem(button.up('form'), this.getSystemStore())
                }
            },
            'systemdetail button[action=cancel]': {//取消按钮 关闭打开的当前窗口
                click: function (button, evt) {
                    button.up('window').close()
                }
            },
            'systemdetail textfield[name=id]': {/*为系统代码字段添加可用性验证和全码
             的自动补全*/
                change: function (field, newValue, oldValue) {
                    if (newValue && newValue != field.originalValue) {
//                        系统代码可用验证
                        Ext.Ajax.request({
                            url: '/api/systems/checkSystemcodeAvaliable/' + newValue,
                            success: function (response) {
                                var res = Ext.decode(response.responseText)
                                if (res.isAvaliable) {
                                    this.clearInvalid()
                                    this.textValid = true
                                }
                                else {
                                    this.textValid = '该系统代码不可用'
                                    this.markInvalid(this.textValid)
                                }
                            },
                            failure: function (response) {
                                if (response.state == 200) {
                                    var res = Ext.decode(response.responseText)
                                    Ext.Msg.alert("错误", res.message)
                                }
                                else
                                    Ext.Msg.alert('错误', '系统代码可用验证服务器端发生错误')
                            },
                            scope: field
                        })
                    }
                    else {
                        field.clearInvalid()
                        field.textValid = true
                    }

                    // 系统全码自动补全
                    var form = field.up('form')
                    form.down('textfield[name=fullcode]').setValue(form.getForm().getRecord().data.parentFullcode + (newValue ? newValue + '.' : ''))
                }
            }
        })
    }
})
