/**
 * Created by hooxin on 14-5-8.
 */
/**
 * 用户列表
 */
Ext.define('Techsupport.view.sysadmin.user.List', {
    extend: 'Techsupport.view.base.BaseList',
    alias: 'widget.userlist',
    store: 'User',
    columns: {
        items: [
            {text: 'ID', dataIndex: 'id', sortable: false, flex: 1},
            {text: '帐号', dataIndex: 'useraccount', sortable: false, flex: 1},
            {text: '姓名', dataIndex: 'username', sortable: false, flex: 1},
            {text: '密码', dataIndex: 'password', sortable: false, flex: 1,
                renderer: function (value, metaData, record) {
                    return "******"
                }},
            {text: '身份证', dataIndex: 'idnum', sortable: false, flex: 1},
            {text: '移动电话', dataIndex: 'mobilePhone', sortable: false, flex: 1},
            {text: '序号', dataIndex: 'userorder', sortable: false, flex: 1},
            {text: '是否有效', dataIndex: 'isValid', sortable: false, flex: 1,
                renderer: function (value, metaData, record) {
                    return record.data.isValid == "1" ? "是" : "否"
                }},
            {text: '用户类别', dataIndex: 'userType', sortable: false, flex: 1,renderer: function (value,
                metaData,record,rowIndex, colIndex, store, view) {
                var store=view.up('userlist').userTypeDictItemStore;

                var result = "";
                if(value){
                    var vl=[];
                    var values=value.split(",");
                    for(var i=0;i<values.length;i++){

                        if(values[i]){
                            var rec=store.findRecord("factValue",new RegExp("^"+values[i]+"$"));
                            var displayName=rec ? rec.data.displayName:null;

                            if(displayName)
                                vl.push(displayName);
                        }
                    }
                    Ext.Array.each(vl, function (v) {
                        result+=v+",";
                    })
                    if(vl.length>0)
                        result=result.substring(0,result.length-1);
                }
                return result;
            }},
            {text: '邮箱', dataIndex: 'email', sortable: false, flex: 1}
        ],
        defaults: {
        }
    }
});