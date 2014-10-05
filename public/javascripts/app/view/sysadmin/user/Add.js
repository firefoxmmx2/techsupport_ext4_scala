/**
 * Created by hooxin on 14-5-8.
 */
Ext.define('Techsupport.view.sysadmin.user.Add', {
    extend: 'Ext.window.Window',
    title: '新增用户',
    width: 630,
    alias: 'widget.useradd',
    closeAction: 'destroy',
    bodyPadding: 2,
    items: [
        {xtype: 'form',
            defaults: {
                margin: {right: 10, left: 5, top: 5, bottom: 5}
            },
            items: [
                {xtype: 'panel', layout: 'column',
                    defaults: {
                        xtype: 'textfield',
                        margin: {left: 5, top: 0, bottom: 5, right: 5}
                    },
                    border: false,
                    items: [
                        {fieldLabel: '用户帐号', name: 'useraccount', allowBlank: false, blankText: '用户帐号不能为空', validator: function (value) {
                            return this.textValid;
                        }},
                        {fieldLabel: '密码', name: 'password', inputType: 'password',
                            allowBlank: false, blankText: '密码不能为空',
                            maxLength: 16, maxLengthText: '最长16位', validator: function (value) {
                            var res = true;
                            var p = this.findParentByType('panel').down('textfield[name=password2]')
                            if (value) {
                                if (value == p.getValue())
                                    res = true;
                                else
                                    res = "前后输入密码不匹配";
                            }
                            return res;
                        }},
                        {fieldLabel: '重复密码', name: 'password2', inputType: 'password',
                            inputType: 'password', allowBlank: false, blankText: '重复密码不能为空',
                            maxLength: 16, maxLengthText: '最长16位', validator: function (value) {
                            var res = true;
                            var p = this.findParentByType('panel').down('textfield[name=password]')
                            if (value) {
                                if (value == p.getValue())
                                    res = true;
                                else
                                    res = "前后输入密码不匹配";
                                if (res) {
                                    p.clearInvalid();
                                }
                            }

                            return res;
                        }},
                        {fieldLabel: '用户名称', name: 'username', allowBlank: false, blankText: '用户名称不能为空'},
                        {fieldLabel: '身份证', name: 'idnum'},
                        {fieldLabel: '邮箱', name: 'email', vtype: 'email'},
                        {fieldLabel: '手机', name: 'mobilePhone', vtype: 'number'},
                        {fieldLabel: '序号', name: 'userorder', value: '1'},
                        {fieldLabel: '所属机构', name: 'departname', allowBlank: false, blankText: '所属机构不能为空', readOnly: true},
                        {fieldLabel: '所属机构id', name: 'departid', allowBlank: false, blankText: '所属机构id不能为空', hidden: true},
                        {fieldLabel: '是否可用', name: 'isValid', allowBlank: false, blankText: '是否可用不能为空', hidden: true, xtype: 'combobox',
                            store: 'OneZero',
                            queryMode: 'local',
                            displayField: 'text',
                            valueField: 'value',
                            value: '1'}
                    ]},
                {fieldLabel: '用户类别', margin: {left: 10, top: 0, bottom: 0, right: 5}, allowBlank: false, blankText: '用户类别不能为空', name: 'userTypeGroup', xtype: 'checkboxgroup', columns: 2, vertical: true, items: [
//                    { boxLabel: 'Item 1', name: 'usertype', inputValue: '1' },
//                    { boxLabel: 'Item 2', name: 'usertype', inputValue: '2', checked: true },
//                    { boxLabel: 'Item 3', name: 'usertype', inputValue: '3' },
//                    { boxLabel: 'Item 4', name: 'usertype', inputValue: '4' },
//                    { boxLabel: 'Item 5', name: 'usertype', inputValue: '5' },
//                    { boxLabel: 'Item 6', name: 'usertype', inputValue: '6' }
                ]}
            ]}
    ],
    buttons: [
        {xtype: 'button', text: '确定', action: 'enter'},
        {xtype: 'button', text: '取消', action: 'cancel'}
    ]
});