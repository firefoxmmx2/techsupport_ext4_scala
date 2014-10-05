/**
 * Created by hooxin on 14-10-5.
 */
Ext.define('Techsupport.view.base.BaseList',{
    extend:'Ext.grid.Panel',
    selType: 'checkboxmodel',
    selModel: {
        flex: 0,
        showHeaderCheckbox: true,
        width: 16
    },
    loadMask:true,
    dockedItems: [
        {xtype: 'pagingtoolbar',
            store: 'Menu',
            dock: 'bottom',
            pageSize: 10,
            listeners: {
                afterrender: function (p, eOpts) {
                    p.query('button:last').map(function (b) {
                        p.remove(b);
                    });
                    p.query('tbseparator:last').map(function (s) {
                        p.remove(s);
                    })
                }
            }
        }
    ]
})
