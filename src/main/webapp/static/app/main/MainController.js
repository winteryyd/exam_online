Ext.define('App.main.MainController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.main',

    requires: [
        'App.dashboard.Dashboard',
        'App.plat.Plat',
        'App.scheduler.Scheduler',
        'App.page.Page404',
        'App.page.Card404',
        'App.page.LockScreen',
        'App.page.Login',
        'App.page.PasswordReset',
        'App.page.Register'
    ],
    
    listen : {
        controller : {
            '#' : {
                unmatchedroute : 'onRouteChange'
            }
        }
    },

    routes: {
        ':node': 'onRouteChange'
    },

    lastView: null,

    setCurrentView: function(hashTag) {
        hashTag = (hashTag || '').toLowerCase();

        var me = this,
            refs = me.getReferences(),
            mainCard = refs.mainCardPanel,
            mainLayout = mainCard.getLayout(),
            view = hashTag || 'page404',
            lastView = me.lastView,
            existingItem = mainCard.child('component[routeId=' + hashTag + ']'),
            newView;

        // Kill any previously routed window
        if (lastView && lastView.isWindow) {
            lastView.destroy();
        }

        lastView = mainLayout.getActiveItem();

        if (!existingItem) {
        	try{
        		newView = Ext.create({
        			xtype: view,
        			routeId: hashTag,  // for existingItem search later
        			hideMode: 'offsets'
        		});
        	}catch(e){
        		console.log(e);
        		newView = Ext.create({
        			xtype: 'page404',
        			routeId: hashTag,  // for existingItem search later
        			hideMode: 'offsets'
        		});
        	}
        }

        if (!newView || !newView.isWindow) {
            // !newView means we have an existing view, but if the newView isWindow
            // we don't add it to the card layout.
            if (existingItem) {
                // We don't have a newView, so activate the existing view.
                if (existingItem !== lastView) {
                    mainLayout.setActiveItem(existingItem);
                }
                newView = existingItem;
            }
            else {
                // newView is set (did not exist already), so add it and make it the
                // activeItem.
                Ext.suspendLayouts();
                mainLayout.setActiveItem(mainCard.add(newView));
                Ext.resumeLayouts(true);
            }
        }

        if (newView.isFocusable(true)) {
            newView.focus();
        }
        
        if(lastView && newView.xtype != 'page404'){
        	var oldv = lastView.items.items[0].getMicro();
        	var newv = newView.items.items[0].getMicro();
        	if(oldv != newv){
        		this.onToggleNavigationSize();
        	}
        }
        me.lastView = newView;
    },

    onNavigationTreeSelectionChange: function (tree, node) {
        var to = node && (node.get('routeId') || node.get('viewType'));

        if (to) {
            this.redirectTo(to);
        }
    },

    onToggleNavigationSize: function () {
    	var me = this,
	        refs = me.getReferences(),
	        mainCard = refs.mainCardPanel,
	        mainLayout = mainCard.getLayout(),
	        lastView = mainLayout.getActiveItem(),
    	    navigationList = lastView.items.items[0],
            collapsing = !navigationList.getMicro(),
            new_width = collapsing ? 64 : 250;

        if (Ext.isIE9m || !Ext.os.is.Desktop) {
            Ext.suspendLayouts();

            refs.senchaLogo.setWidth(new_width);

            navigationList.setWidth(new_width);
            navigationList.setMicro(collapsing);

            Ext.resumeLayouts(); // do not flush the layout here...
            lastView.layout.animatePolicy = lastView.layout.animate = null;
            lastView.updateLayout();  // ... since this will flush them
        }
        else {
            if (!collapsing) {
                navigationList.setMicro(false);
            }
            navigationList.canMeasure = false;

            refs.senchaLogo.animate({dynamic: true, to: {width: new_width}});
            navigationList.width = new_width;
            lastView.updateLayout({isRoot: true});
            navigationList.el.addCls('nav-tree-animating');
            if (collapsing) {
                navigationList.on({
                    afterlayoutanimation: function () {
                        navigationList.setMicro(true);
                        navigationList.el.removeCls('nav-tree-animating');
                        navigationList.canMeasure = true;
                    },
                    single: true
                });
            }
        }
    },

    onMainViewRender:function() {
    	if(!this.view.user){
    		this.setCurrentView("login");
    		return;
    	}
    	var hash = window.location.hash;
        if (!hash) {
            this.redirectTo("dashboard");
        }else{
        	if(hash.indexOf("#")==0){
        		var view = hash.substr(1);
        		this.setCurrentView(view);
        	}else{
        		this.setCurrentView(hash);
        	}
        }
    },

    onRouteChange:function(id){
    	console.log(id);
    	if(id == 'logout'){
    		id = 'login';
    		Ext.Ajax.request({
				url : app_logout,
				method : 'POST',
				success : function(result) {
					if(result.responseText == 'success'){
						console.log("退出成功！")
					}else{
						console.log("退出失败！")
					}
				}
			});
    	}
    	this.setCurrentView(id);
    }
});
