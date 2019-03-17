Ext.define('App.plat.Plat', {
    extend: 'Ext.container.Container',
    xtype: 'plat',
    requires : [
        'Ext.layout.container.HBox',
        'App.plat.PlatController',
        'App.plat.PlatModel'
    ],
    controller: 'plat',
    viewModel: 'plat',
    scrollable: 'y',
    layout: {
        type: 'hbox',
        align: 'stretchmax',
        animate: true,
        animatePolicy: {
            x: true,
            width: true
        }
    },
    beforeLayout : function() {
        var me = this,
            height = Ext.Element.getViewportHeight() - 64,  
            navTree = me.items.items[0];
        me.minHeight = height;
        navTree.setStyle({
            'min-height': height + 'px'
        });
        me.callParent(arguments);
    },
    listeners: {
    	beforerender:function(_this,opts){
    		var menuStore = Ext.create('App.main.NavigationTreeStore');
    		var hash = window.location.hash;
    		Ext.apply(menuStore.proxy.extraParams, {
    			module : hash
    		});
    		Ext.apply(menuStore,{
    			module : _this.items.items[0]
    		});
    		menuStore.load();
    	}
    },
    items: [
    	{
            xtype: 'treelist',
            ui: 'nav',
            width: 250,
            expanderFirst: false,
            expanderOnly: false,
            listeners: {
                selectionchange: 'onNavTreeSelectionChange'
            }
        },
        {
            xtype: 'container',
            flex: 1,
            reference: 'cardPanel',
            cls: 'sencha-dash-right-main-container',
            layout: {
                type: 'card',
                anchor: '100%'
            },
            style : 'background-image:url('+background+');background-repeat: no-repeat;filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=\'scale\')";-moz-background-size:100% 100%;background-size:100% 100%;',
        }
    ]
});
