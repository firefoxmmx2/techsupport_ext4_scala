/**
 * Created by hooxin on 14-9-19.
 */
Ext.apply(Ext.form.field.VTypes, {
    number: function (v) {
        return /^\d*$/.test(v);
    },
    numberText: '必须为数字',
    numberMask: /[\d]/,
    alphanum: function (v) {
        return /^[a-zA-Z0-9_.]*$/.test(v);
    },
    alphanumMask: /[a-zA-Z0-9_.]/i,
    alphanumText: '必须为字母,数字,下划线和点'
});