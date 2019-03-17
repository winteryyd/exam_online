Ext.define('Ext.ux.form.SearchField', {
    extend: 'Ext.form.field.Text',
    alias: 'widget.searchfield',
    triggers: {
        clear: {
            weight: 0,
            cls: Ext.baseCSSPrefix + 'form-clear-trigger',
            hidden: true,
            handler: 'onClearClick',
            scope: 'this'
        },
        search: {
            weight: 1,
            cls: Ext.baseCSSPrefix + 'form-search-trigger',
            handler: 'onSearchClick',
            scope: 'this'
        }
    },
    listeners : {
		afterrender : function(_this, eOpts) {
			if (!_this.store || !_this.store.isStore) {
				_this.store = _this.ownerCt.ownerCt.getStore();
				_this.store.setRemoteFilter(true);
				var proxy = _this.store.getProxy();
				proxy.setFilterParam(_this.paramName);
				proxy.encodeFilters = function(filters) {
					return filters[0].getValue();
				}
			}
		}
    },
    paramName : 'query',
    initComponent: function() {
        var me = this;
        me.callParent(arguments);
        me.on('specialkey', function(f, e){
            if (e.getKey() == e.ENTER) {
                me.onSearchClick();
            }
        });
        
        if (me.store && store.isStore) {
        	me.store.setRemoteFilter(true);
	        var proxy = me.store.getProxy();
	        proxy.setFilterParam(me.paramName);
	        proxy.encodeFilters = function(filters) {
	            return filters[0].getValue();
	        }
        }
    },
    onClearClick : function(){
        var me = this;
            activeFilter = me.activeFilter;
        if (activeFilter) {
            me.setValue('');
            me.store.getFilters().remove(activeFilter);
            me.activeFilter = null;
            me.getTrigger('clear').hide();
            me.updateLayout();
        }
    },
    onSearchClick : function(){
        var me = this;
            value = me.getValue();
        if (value.length > 0) {
            me.activeFilter = new Ext.util.Filter({
                property: me.paramName,
                value: value
            });
            me.store.getFilters().add(me.activeFilter);
            me.getTrigger('clear').show();
            me.updateLayout();
        }
    }
});