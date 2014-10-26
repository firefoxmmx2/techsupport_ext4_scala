/**
 * 登录日志数据模型
 */
Ext.define('Techsupport.model.LoginLog', {
    extend: "Ext.data.Model",
    idProperty: 'id',
    fields: [
        {name: "id", type: "Long"},
        {name: "loginuserid", type: "Long"},
        {name: "useraccount", type: "String"},
        {name: "username", type: "String"},
        {name: "loginunitcode", type: "String"},
        {name: "loginunitname", type: "String"},
        {name: "loginip", type: "String"},
        {name: "loginmac", type: "String"},
        {name: "logintiime", type: "Date", dateFormat: 'Y-m-d H:i:s', convert: function (v, record) {
            if (v instanceof Date)
                return v
            else if (v)
                return new Date(v.millis)
            return v
        }},
        {name: "quittime", type: "Date", dateFormat: 'Y-m-d H:i:s', convert: function (v, record) {
            if (v instanceof Date)
                return v
            else if (v)
                return new Date(v.millis)
            return v
        }}
    ]
})
