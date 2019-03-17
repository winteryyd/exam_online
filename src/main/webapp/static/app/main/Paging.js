Ext.define('App.main.Paging', {
    extend: 'Ext.toolbar.Paging',
    requires: [
        'Ext.ux.ProgressBarPager'
    ],
	displayInfo : true,
	displayMsg : '显示 {0} - {1} 条，共计 {2} 条',
	emptyMsg : "没有数据",
	beforePageText : '第',
	afterPageText : '页/共{0}页',
	items: ['-', '每页', {
        xtype: 'combobox',
        displayField: 'id',  　　  　//获取的内容
        valueField: 'value',　　　 　//显示的内容
        editable: false,　　　　  　　//不允许编辑只能选择
        allowBlank: false,　　　　 　 //不允许为空
        triggerAction: 'all',      //请设置为”all”,否则默认 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，
        width: 70,
        listeners: {
            render: function (comboBox) {
                comboBox.setValue(comboBox.ownerCt.getStore().getPageSize());   //使得下拉菜单的默认值是初始值
            },
            select: function (comboBox) {
                var pSize = comboBox.getValue();
                comboBox.ownerCt.getStore().pageSize = parseInt(pSize); 
                comboBox.ownerCt.moveFirst();
            }
        },
        queryMode: 'local',
        store: {
            fields: ['id', 'value'],
            data: [['5', 5],['10', 10],['15', 15],['20',20],['25', 25],['50', 50]]
        }
    }, '条'],
    plugins: {
        'ux-progressbarpager': true
    }
});
