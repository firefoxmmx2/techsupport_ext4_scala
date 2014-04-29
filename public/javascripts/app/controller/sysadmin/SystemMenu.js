/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.controller.sysadmin.SystemMenu', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.SystemMenu'],
    stores: ['UserMenuNode', 'UserSystemNode'],
    init: function () {
        var menuStore = this.getUserMenuNodeStore()
        var systemStore = this.getUserSystemNodeStore();

        this.control({
            'systemMenuAccordion': {
                render: function (p) {
//                    var menus = menuStore;
                    var me = this;
                    systemStore.load({scope: this, params: {parentsystemcode: 0},
                        callback: function (records, operation, success) {
                            systemStore.each(function (s) {
                                var userMenuNodeStore = me.getUserMenuNodeStore();
                                userMenuNodeStore.load({scope: this, params: {parentmenucode: "0", systemcode: s.get("id")},
                                    callback: function (records, operation, success) {
                                        p.add({title: s.get("systemname"), items: [
                                            {xtype: 'treepanel', store: userMenuNodeStore}
                                        ]})
                                    }});

                            });
                        }})

//                    p.removeAll();
//                    p.items = systems.map(function ( v) {
//                        return {
//                            title: v.systemname,
//                            items: [
//                                {xtype: 'treepanel', store: menuStore }
//                            ]
//                        }
//                    });

                }
            }
        })
    }
})
