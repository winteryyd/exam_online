Ext.define('App.dashboard.DashboardController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.dashboard',

    requires: [
    	'App.dashboard.HeadPhoto'
    ],
    
    lastView: null,
    setCurrentView: function(hashTag) {
        hashTag = (hashTag || '').toLowerCase();

        var me = this,
            refs = me.getReferences();
            card = refs.cardPanel,
            layout = card.getLayout(),
            view = hashTag || 'card404',
            lastView = me.lastView,
            existingItem = card.child('component[routeId=' + hashTag + ']'),
            newView=null;
        lastView = layout.getActiveItem();
        
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
        			xtype: 'card404',
        			routeId: hashTag,  // for existingItem search later
        			hideMode: 'offsets'
        		});
        	}
        }

        if (!newView || !newView.isWindow) {
            if (existingItem) {
                if (existingItem !== lastView) {
                	layout.setActiveItem(existingItem);
                }
                newView = existingItem;
            }
            else {
                Ext.suspendLayouts();
                layout.setActiveItem(card.add(newView));
                Ext.resumeLayouts(true);
            }
        }
        if (newView.isFocusable(true)) {
            newView.focus();
        }

        me.lastView = newView;
    },

    onNavTreeSelectionChange: function (tree, node) {
        var to = node && (node.get('routeId') || node.get('viewType'));
        this.setCurrentView(to);
    }
});
