/**
 * Created by hooxin on 14-10-5.
 */
/**
 * 全局参数
 */
Ext.define('Techsupport.store.GlobalParam', {
    extend: 'Techsupport.store.Basic',
    model: 'Techsupport.model.GlobalParam',
    proxy: {
        type: 'rest',
        url: '/api/globalParams',
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success',
            totalProperty: 'total'
        }
    },
    sorters: 'id'
})