/**
 * 自定义组件
 */
Ext.define('Ext.form.field.Checkbox', {
    override: 'Ext.form.field.Checkbox',
    getValue: function () {
        return this.checked ? this.inputValue : null;
    }
});

//扩展ext
Ext.define('Ext.Array',{
    override:'Ext.Array',
    reduce: function (array, func, scope) { //集合合并处理
        array = Ext.Array.from(array)
        var i= 0,len=array.length
        var result=array[i]
        if(len>1)
            for(i=1;i<len;i++){
                result=func.call(scope||this,result,array[i],array)
            }

        return result
    }
})
