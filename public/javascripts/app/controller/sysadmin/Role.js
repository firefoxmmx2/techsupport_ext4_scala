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
        "sysadmin.role."
    ],
    models: [],
    refs: [
        {ref:'queryForm',selector:'rolemanage panel[region=center] form'}
    ],
    init: function () {
        this.control({
            'rolemanage rolelist':{
                itemdblclick: function (grid, record) { //双击打开编辑窗口
                    this.toModifyRole(record)
                }
            },
            'rolemanage panel[region=center] button[action=query]':{//管理界面查询按钮
                click: function (button) {

                }
            },
            'rolemanage panel[region=center] button[action=add]':{//管理界面添加按钮
                click: function (button) {

                }
            },
            'rolemanage panel[region=center button[action=modify]]':{//管理界面修改按钮
                click: function (button) {

                }
            },
            'rolemanage panel[region=center] button[action=remove]':{//管理界面删除按钮
                click: function (button) {

                }
            },
            'rolemanage panel[region=center] button[action=relateMenu]':{//管理界面关联菜单按钮
                click: function (button) {

                }
            },
            'rolemanage panel[region=center] button[action=relateFunction]':{//关联操作按钮
                click: function (button) {

                }
            }
        })
    },
    toAddRole: function () {//打开新增
        var _window=this.getView("sysadmin.role.Detail").create({
            title:'角色新增',
            _type:'add'
        })
        var form=_window.down('form')
        form.loadRecord(this.getModel("Role").create())
        _window.show()
    },
    addRole: function () {//新增

    },
    toModifyRole: function (record) { //打开修改
        var _window=this.getView("sysadmin.role.Detail").create({
            title:'角色修改',
            _type:'modify'
        })
        var form=_window.down('form')
        form.loadRecord(record)
        _window.show()
    },
    modifyRole: function () { //修改

    },
    removeRole: function () { //删除

    },
    queryRole: function () { //查询
        var form=this.getQueryForm()
        if(form.isValid()){
            var store=this.getRoleStore()
            Ext.apply(store.getProxy().extraParams,form.getValues())
            store.load()
        }
    }
})