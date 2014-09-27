/**
 * 自定义组件
 */
Ext.define('Ext.form.field.Checkbox', {
    override: 'Ext.form.field.Checkbox',
    getValue: function () {
        return this.checked ? this.inputValue : null;
    }
});
