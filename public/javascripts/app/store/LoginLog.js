/**
 * 登录日志存储
 */
Ext.define('Techsupport.store.LoginLog', {
    extend: 'Techsupport.store.Basic',
    model: 'Techsupport.model.LoginLog',
    proxy: {
        type: 'rest',
        url: "/api/loginlogs",
        reader: {
            type: 'json',
            root: 'data' // success 和total都使用默认值
        }
    },
    sorters: [
        {property: 'logintiime', direction: 'DESC'}
    ]
})
