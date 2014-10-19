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
        'sysadmin.dictItem.DictItemTreeList'
    ],
    models: [
        'DictItem',
        'DictItemTree'
    ],
    refs: [
        {ref: 'queryForm', selector: 'dictItemManage form:first'}
    ],
    init: function () {
        this.control({
            'dictItemManage dictItemSimpleList': { //简单类型列表

            },
            'dictItemManage dictItemTreeList': {//树形类型列表

            },
            'dictItemManage button[action=add]': { //管理界面添加按钮

            },
            'dictItemManage button[action=modify]': {//管理界面修改按钮

            },
            'dictItemManage button[action=remove]': {//管理界面删除按钮

            },
            'dictItemManage button[action=up]': {//管理界面上移按钮

            },
            'dictItemManage button[action=down]': {//管理界面下移按钮

            },
            'dictItemManage button[action=query]':{//管理界面查询按钮

            }
        })
    }
})
