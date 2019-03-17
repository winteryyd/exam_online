Ext.define('App.page.Card404', {
	extend: 'Ext.panel.Panel',
    xtype: 'card404',
    requires : [
    	'Ext.container.Container',
        'Ext.form.Label',
        'Ext.layout.container.VBox',
        'Ext.toolbar.Spacer'
    ],
    autoShow: true,
    cls: 'error-page-container',
    closable: false,
    titleAlign: 'center',
    maximized: true,
    modal: true,

    layout: {
        type: 'vbox',
        align: 'center',
        pack: 'center'
    },
    beforeLayout : function() {
        var me = this,
        time = me.items.items[0].items.items[1];
        time.setHtml('<div>页面未找到！</div><div>请联系管理员解决！</div><div>'+Ext.Date.format(new Date(),'Y-m-d H:i:s')+'</div>');
        me.callParent(arguments);
    },
    items: [
        {
            xtype: 'container',
            width: 400,
            cls:'error-page-inner-container',
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'center'
            },
            items: [
                {
                    xtype: 'label',
                    cls: 'error-page-top-text',
                    text: '404'
                },
                {
                    xtype: 'label',
                    cls: 'error-page-desc',
                    html: '<div>所请求的页面不存在或已被删除！</div><div>请联系管理员解决！</div><div>'+Ext.Date.format(new Date(),'Y-m-d H:i:s')+'</div>'
                },
                {
                    xtype: 'tbspacer',
                    flex: 1
                }
            ]
        }
    ]
});
