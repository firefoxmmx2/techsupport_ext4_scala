Ext.define('Techsupport.view.sysadmin.department.Manage', {
    extend: 'Techsupport.view.sysadmin.user.Manage',
//    extend: 'Ext.panel.Panel',
    alias: 'widget.departmentManage',
    initComponent: function () {
//        this.items.filter(function (i) {
//            return i.xtype == "panel" && i.region == 'center';
//        }).map(function (i) {
//            i.items = {xtype: 'departmentlist'};
//            i.dockedItems.filter(function (j) {
//                return j.xtype == "buttongroup";
//            }).map(function (j) {
//                j.items.filter(function (k) {
//                    return k.xtype == 'form';
//                }).map(function (k) {
//                    k.items = [
//                        {xtype: 'textfield', fieldLabel: '机构代码', name: 'departcode'},
//                        {xtype: 'textfield', fieldLabel: '机构名称', name: 'departname'},
//                        {xtype: 'textfield', fieldLabel: '上级机构ID', name: 'parentDepartid', hidden: true}
//                    ];
//                    return k;
//                })
//
//                return j;
//            })
//            return i;
//        });

        this.callParent(arguments);
        this.query('panel[region=center]').map(function (p) {
            p.removeAll();
            p.add({xtype:'departmentlist'});
        });
        this.query('form').map(function (f) {
            f.removeAll();
            f.add([
                {xtype:'textfield',fieldLabel:'机构代码',name:'departcode'},
                {xtype:'textfield',fieldLabel:'机构名称',name:'departname'},
                {xtype:'textfield',fieldLabel:'上级机构ID',name:'parentDepartid',hidden:true}
            ])
        });
    }

//    layout: {
//        type: 'border',
//        align: 'stretch',
//        pack: 'start'
//    },
//    border: false,
//    items: [
//        {xtype: 'departmenttree', region: 'west', width: '20%', split: true},
//        {xtype: 'panel', region: 'center', border: false, layout: 'fit', items: [
//            {xtype: 'departmentlist'}
//        ],
//            dockedItems: [
//                {xtype: 'toolbar', dock: 'top', ui: 'footer', items: [
//                    {text: '添加', xtype: 'button', action: 'add'} ,
//                    '-',
//                    {xtype: 'button', text: '删除', action: 'remove'},
//                    '-',
//                    {xtype: 'button', text: '上移', action: 'up'},
//                    '-',
//                    {xtype: 'button', text: '下移', action: 'down'}
//                ]},
//                {xtype: 'buttongroup', dock: 'top', items: [
//                    {xtype: 'form', layout: 'column', defaults: {margin: {top: 2, bottom: 2, left: 0, right: 20}}, border: false, items: [
//                        {xtype: 'textfield', fieldLabel: '机构代码', name: 'departcode'},
//                        {xtype: 'textfield', fieldLabel: '机构名称', name: 'departname'},
//                        {xtype: 'textfield', fieldLabel: '上级机构ID', name: 'parentDepartid', hidden: true}
//                    ]},
//                    {xtype: 'button', text: '查询', action: 'query', margin: {top: 2, bottom: 2}}
//                ]}
//            ]}
//    ]
})
