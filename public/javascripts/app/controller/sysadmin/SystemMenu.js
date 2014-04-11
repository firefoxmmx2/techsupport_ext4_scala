/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.controller.sysadmin.SystemMenu', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.SystemMenu'],
    stores: ['UserMenuNode', 'UserSystem'],
    init: function () {
        var menuStore = this.getUserMenuNodeStore()
        var systemStore = this.getUserSystemStore();
        systemStore.load({params: {parentsystemcode: 0}});
        menuStore.load({params: {parentmenucode: 0}});
        this.control({
            'systemMenuAccordion': {
                render: function (p) {
                    var menus = menuStore.data;
                    var systems = [];
//                    menus.each(function (d) {
//                        systems[d.systemcode] = systemStore.getById(d.systemcode);
//                    });
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
