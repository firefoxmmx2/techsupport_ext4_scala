/**
 * Created by hooxin on 14-10-6.
 */
Ext.define('Techsupport.view.sysadmin.globalParam.Detail', {
    extend: 'Techsupport.view.base.BaseDetail',
    alias: 'widget.globalParamDetail',
    initComponent: function () {
        Ext.apply(this.items, this.superclass.items)
        this.items[0].items[0].layout = 'fit'
        this.callParent(arguments)
        this.down('form > panel').add({
            xtype: 'panel',
            layout: 'anchor',
            border: false,
            items: [
                {xtype: 'textfield', name: 'id', fieldLabel: '全局参数代码',
                    allowBlank: false,
                    blankText: '全局参数代码不能为空',
                    anchor: '90%',
                    vtype:'alphanum',
                    textValid: false,
                    validator: function (value) {
                        return this.textValid
                    }},
                {xtype: 'textfield', name: 'globalparname', fieldLabel: '全局参数名称',
                    allowBlank: false,
                    blankText: '全局参数名称不能为空',
                    anchor: '90%'},
                {xtype: 'textfield', name: 'globalparvalue', fieldLabel: '全局参数值',
                    allowBlank: false,
                    blankText: '全局参数值不能为空',
                    anchor: '90%'}
            ]
        })
    }
})