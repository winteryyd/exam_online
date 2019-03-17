Ext.define('App.main.DictStore', {
	extend : 'Ext.data.Store',
	fields : [ 'dictId', 'dictType','dictValue','dictLabel','rank'],
	autoLoad : true,
	proxy : {
		type : 'ajax',
		url : sys_dict+"/getDicts",
		reader : {
			type : 'json',
			root : 'dicts'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts){
			Ext.apply(store.proxy.extraParams, {
				dictType : store.dictType
			});
		}
	}
});