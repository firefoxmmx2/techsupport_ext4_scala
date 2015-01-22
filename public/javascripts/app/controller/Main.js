/**
 * Created by hooxin on 14-4-11.
 */
Ext.define('Techsupport.controller.Main', {
    extend: 'Ext.app.Controller',
    views: ['sysadmin.SystemMenu', 'Main', 'Copyright', 'THeader'],
    stores: ['UserMenuNode'],
    refs: [
        {ref: 'viewport', selector: 'viewport'},
        {ref: 'systemMenuAccordion', selector: 'main systemMenuAccordion'}
    ],
    init: function () {
        //加载系统菜单树控制器
        var systemMenuCotroller = this.getController("sysadmin.SystemMenu")
//        身份验证
        var loginController = this.getController('sysadmin.Login');
        loginController.heartCheck();

        this.control({
            'panel > tabpanel': {
                beforeadd: function (tp, component, index, eOpts) {
                    if (tp.queryById(component.getId())) {
                        return false;
                    }
                    return true;
                }
            },
            'main systemMenuAccordion': {
                afterlayout: function (p, layout, options) {
                    Ext.query('div[id*=placeholder_hd]', p.dom)
                },
                collapse: function (p, opts) {
                    var el = Ext.select('div[id*=placeholder_hd]', p.dom)
                    if (el) {
                        var onMouseOver = function () {
                            Ext.query('div[id*=placeholder]', p.dom)[2].click()
                        }
                        el.un('mouseover', onMouseOver)
                        el.on('mouseover', onMouseOver)
                    }
                }
            }
        });

    }
})
