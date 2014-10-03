/**
 * Created by hooxin on 14-9-21.
 */
Ext.define('Techsupport.view.base.BaseDetail', {
    extend: 'Ext.window.Window',
    width: 630,
    bodyPadding: 2,
    items: [
        {xtype: 'form',
            formBind: true,
            defaults: {
                margin: {right: 10, left: 5, top: 5, bottom: 5}
            }, items: [
            {xtype: 'panel',
                layout: 'column',
                defaults: {
                    xtype: 'textfield',
                    margin: {left: 5, top: 0, bottom: 5, right: 5}
                },
                border: false,
                items: [
                    // 内容 部分
                ]}
        ]}
    ],
    buttons: [
        {xtype: 'button', text: '确定', action: 'enter'},
        {xtype: 'button', text: '取消', action: 'cancel'}
    ]
});