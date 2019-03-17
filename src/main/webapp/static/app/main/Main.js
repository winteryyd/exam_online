Ext.define('ToolBarMenu',{
    extend: 'Ext.data.Model',
    fields: [
        {name:'itemId',mapping:'itemId'},
        'iconCls',
        'ui',
        'href',
        'hrefTarget',
        'text',
        'tooltip'
    ]
});
Ext.define('App.main.Main', {
    extend: 'Ext.container.Viewport',
    requires: [
    	'Ext.ux.form.SearchField',
//        'Ext.button.Segmented',
//        'Ext.list.Tree',
        'App.main.MainController',
        'App.main.MainModel',
        'App.main.IconStore',
        'App.main.DictStore',
        'App.main.NavigationTreeStore',
        'App.main.Paging',
        'App.main.VTypes'
    ],
    controller: 'main',
    viewModel: 'main',
    cls: 'sencha-dash-viewport',
    itemId: 'mainView',

    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    listeners: {
        render: 'onMainViewRender',
        beforerender:function(_this,opts){
        	var vheaderBar = _this.getComponent('headerBar');
        	Ext.create('Ext.data.Store', {
        	    model: 'ToolBarMenu',
        	    autoLoad: true ,
        	    proxy: {
        	        type: 'ajax',
        	        url : sys_menu+"/getMenuHeadBar",
        	        reader: {
        	            type: 'json',
        	            root: 'menuHeadBar'
        	        } 
        	    },
        	    listeners: {
        	        load: function( store,records,successful, operation, eOpts ){
        	            if(successful){
        	            	Ext.Array.forEach(records,function(record,index,array){
        	            		vheaderBar.add(record.data);
        	            	});
        	            	vheaderBar.add('->');
        	            	
        	            	vheaderBar.add({
        	            		iconCls: 'x-fa fa-delicious',
        	                    xtype: 'button',
        	                    tooltip: '选择主题',
        	                    menu: [{
        	                        text:'aria',
        	                        listeners: {
	           	                    	 click : function (menu,item,e,eOpts ) {
	           	                    		window.location.href = app_index+"?theme="+menu.text;
	           	                    	 }
	           	                    }
        	                    },{
        	                        text:'crisp',
        	                        listeners: {
	           	                    	 click : function (menu,item,e,eOpts ) {
	           	                    		window.location.href = app_index+"?theme="+menu.text;
	           	                    	 }
	           	                    }
        	                    },{
        	                        text:'graphite',
        	                        listeners: {
	           	                    	 click : function (menu,item,e,eOpts ) {
	           	                    		window.location.href = app_index+"?theme="+menu.text;
	           	                    	 }
	           	                    }
        	                    },{
        	                        text:'gray',
        	                        listeners: {
	           	                    	 click : function (menu,item,e,eOpts ) {
	           	                    		window.location.href = app_index+"?theme="+menu.text;
	           	                    	 }
	           	                    }
        	                    },{
        	                        text:'neptune',
        	                        listeners: {
	           	                    	 click : function (menu,item,e,eOpts ) {
	           	                    		window.location.href = app_index+"?theme="+menu.text;
	           	                    	 }
	           	                    }
        	                    },{
        	                        text:'triton',
        	                        listeners: {
	           	                    	 click : function (menu,item,e,eOpts ) {
	           	                    		window.location.href = app_index+"?theme="+menu.text;
	           	                    	 }
	           	                    }
        	                    }]
        	                   
        	                });
        	            	vheaderBar.add({
        	            		iconCls:'x-fa fa-key',
        	                    ui: 'header',
        	                    href: '#passwordreset',
        	                    hrefTarget: '_self',
        	                    tooltip: '改密码'
        	                });
        	            	vheaderBar.add({
        	            		iconCls:'x-fa fa-spin fa-power-off',
        	                    ui: 'header',
        	                    href: '#logout',
        	                    hrefTarget: '_self',
        	                    tooltip: '退出'
        	                });
        	            	vheaderBar.add({
        	                    xtype: 'tbtext',
        	                    text: _this.user.username,
        	                    cls: 'top-user-name'
        	                });
        	            	vheaderBar.add({
            	                    xtype: 'image',
            	                    cls: 'header-right-profile-image',
            	                    height: 35,
            	                    width: 35,
            	                    alt:'current user image',
            	                    src: app+'/photo'
        	                });
        	            }else{
        	            	console.log("headerBar获取数据失败");
        	            }
        	        }
        	    }
        	});
        }
    },

    items: [
        {
            xtype: 'toolbar',
            cls: 'sencha-dash-dash-headerbar shadow',
            height: 64,
            itemId: 'headerBar',
            items: [
                {
                    xtype: 'component',
                    reference: 'senchaLogo',
                    cls: 'sencha-logo',
                    html: '<div class="main-logo"><img style="width: 60px;" src="'+app_static+'/resources/images/logo.png">'+app_name+'</div>',
                    width: 250
                },
                {
                    margin: '0 0 0 8',
                    ui: 'header',
                    iconCls:'x-fa fa-navicon',
                    id: 'main-navigation-btn',
                    handler: 'onToggleNavigationSize'
                },
                { xtype: 'tbspacer', width: 30 }
            ]
        },
        {
            xtype: 'container',
            flex: 1,
            reference: 'mainCardPanel',
            cls: 'sencha-dash-right-main-container',
            itemId: 'contentPanel',
            layout: {
                type: 'card',
                anchor: '100%'
            }
        }
    ]
});
