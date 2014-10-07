/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.controller.sysadmin.SystemMenu', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.SystemMenu'],
    stores: ['UserSystemNode'],
    refs: [
        {ref: 'viewport', selector: 'viewport'},
        {ref: 'tabPanel', selector: 'panel>tabpanel'}
    ],
    init: function () {
        var systemStore = this.getUserSystemNodeStore();
        this.control({
            'systemMenuAccordion': {
                render: function (p) {
                    var me = this;
                    systemStore.load({scope: this, params: {parentsystemcode: 0},
                        callback: function (records, operation, success) {
                            systemStore.each(function (s) {
                                var userMenuNodeStore = Ext.create("Techsupport.store.UserMenuNode");
                                p.add({title: s.get("systemname"), items: [
                                    {xtype: 'treepanel',
                                        store: userMenuNodeStore,
                                        border: false,
                                        listeners: {
                                            itemclick: {
                                                fn: function (view, record, item, index, e, eOpts) {
                                                    this.currentMenucode = record.raw.id;
                                                    this.currentSystemcode = record.raw.systemcode;
//                                                    打开对应组件内容在tab容器里
                                                    if (record.raw.id != "0") {
                                                        var tp = this.getTabPanel()
                                                        if (record.raw.funcentry && record.raw.funcentry.length > 3 &&
                                                            record.raw.funcentry.indexOf(".jsp") == -1
                                                            && record.raw.funcentry.indexOf(".action") == -1
                                                            && tp.query('panel[itemId=' + record.raw.id + ']').length==0) {
                                                            tp.add({id: record.raw.id, itemId: record.raw.id, title: record.raw.text, layout: 'fit', items: [
                                                                {xtype: record.raw.funcentry}
                                                            ]});
                                                        }

                                                        tp.setActiveTab(record.raw.id);

                                                    }
                                                },
                                                scope: me
                                            },
                                            beforeload: {
                                                fn: function (store, operation, eOpts) {
                                                    if (!this.currentMenucode)
                                                        this.currentMenucode = "0";
                                                    if (!this.currentSystemcode)
                                                        this.currentSystemcode = s.get("id");
//                                                    store.proxy.url = store.proxy.url + "?" + "parentmenucode=" + this.currentMenucode +
//                                                        "&systemcode=" + this.currentSystemcode;
                                                    store.getProxy().setExtraParam("parentmenucode", this.currentMenucode);
                                                    store.getProxy().setExtraParam("systemcode", this.currentSystemcode);

                                                }
                                            },
                                            beforeitemexpand: {
                                                fn: function (n, eOpts) {
                                                    this.currentMenucode = n.raw.id;
                                                    this.currentSystemcode = n.raw.systemcode;
                                                }
                                            },
                                            afterrender: {
                                                fn: function (t, opts) {
                                                    t.getRootNode().expand();
                                                }
                                            }
                                        }
                                    }
                                ]});

                            });
                        }});
                }
            }
        })
    }
})
