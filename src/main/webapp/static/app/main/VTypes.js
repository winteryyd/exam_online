Ext.define('App.main.VTypes',{
    override: 'Ext.form.field.VTypes',
    confirm : function(val, field) {
        if (field.confirmField) {
            var confirmField = field.up("form").getForm().findField(field.confirmField);
            return (val == confirmField.getValue());
        }
        return true;
    },
    confirmText : '两次输入不一致!',
    
    time: function(value) {
        return this.timeRe.test(value);
    },
    timeRe: /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\s+(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/,
    timeText: '时间格式不正确！应为：yyyy-MM-dd HH:mm:ss',
    
    date: function(value) {
        return this.dateRe.test(value);
    },
    dateRe: /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/,
    dateText: '日期格式不正确！应为：yyyy-MM-dd',
    
    forbidBlank:function(value) {
        return value.indexOf(" ") == -1;
    },
    forbidBlankText: '不允许有空格！',
});