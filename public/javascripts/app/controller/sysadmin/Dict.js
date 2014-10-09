/**
 * Created by hooxin on 14-10-9.
 */
/**
 * 字典控制器
 */
Ext.define('Techsupport.controller.sysadmin.Dict', {
    extend: 'Ext.app.Controller',
    stores: ['Dict','DictItem'],
    views: ['sysadmin.dict.Manage','sysadmin.dict.List','sysadmin.dict.Detail'],
    models: ['Dict','DictItem'],
    refs: [
        {ref:'queryForm',selector:'dictManage form:first'},
        {ref:'dictListGrid',selector:'dictManage dictList'}
    ],
    init: function () {
        this.control({
            'dictManage dictList':{
                itemdblclick: function (g, record) {
                    this.toEditDict(record)
                }
            },
            'dictManage button[action=query]':{
                //查询按钮
                click: function () {
                    this.queryDict()
                }
            },
            'dictManage button[action=add]':{
                click: function () {
//                    添加按钮
                }
            },
            'dictManage button[action=modify]':{
                click: function (button,evt) {

                }
            },
            'dictManage button[action=remove]':{
                click: function (button,evt) {

                }
            },
            'dictManage button[action=up]':{
                click: function (button,evt) {

                }
            },
            'dictManage button[action=down]':{
                click: function (button,evt) {

                }
            }
        })

        this.toEditDict= function (record) {
            //打开修改窗口
        }
        this.queryDict= function () {
            //查询
            var form=this.getQueryForm()
            var store=this.getDictStore()
            Ext.apply(store.getProxy().extraParams,form.getForm().getValues())
            store.load()
        }
    }
})