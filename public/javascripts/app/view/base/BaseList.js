/**
 * Created by hooxin on 14-10-5.
 */
Ext.define('Techsupport.view.base.BaseList', {
    extend: 'Ext.grid.Panel',
    selType: 'checkboxmodel',
    selModel: {
        flex: 0,
        showHeaderCheckbox: true,
        width: 16
    },
    loadMask: true,
    dockedItems: [
        {xtype: 'pagingtoolbar',
            dock: 'bottom',
            pageSize: 10,
            listeners: {
                afterrender: function (p, eOpts) {
                    p.remove(p.down('button:last'))
                    p.remove(p.down('tbseparator:last'))
                }
            }
        }
    ],
    listeners: {
        render: function (g) {
            g.getStore().removeAll()
        },
        afterlayout: function (g, layout, opts) {
            var headerHeight = g.headerCt.down('[id*=gridcolumn]').getHeight();
            var pagesize = Math.round(g.getHeight() / headerHeight);
            g.getStore().pageSize = pagesize;
            g.getStore().trailingBufferZone = pagesize;
            g.getStore().getProxy().setExtraParam('limit', pagesize);
        }
    }
})
