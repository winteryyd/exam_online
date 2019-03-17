Ext.define('App.main.NavigationTreeStore', {
	extend : 'Ext.data.TreeStore',
	constructor : function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([ Ext.apply({
			autoLoad : false,
			proxy : {
				type : 'ajax',
				url : sys_menu+"/getNavigationTree",
				reader : {
					type : 'json',
					root : 'navTreeList'
				},
				extraParams:{
			    	module : '#dashboard'
			    }
			},
			listeners : {
				load : {
					fn : me.onJsonstoreLoad,
					scope : me
				}
			}
		}, cfg) ]);
	},
	onJsonstoreLoad : function(store, records, successful, eOpts) {
		if (successful) {
			var json = Ext.loadMenuFilter(records,{pid : 'pid',id:'menuId'});
			var navTree ={
					root:json[0]
			};
			store.module.setStore(navTree);
		}else{
			console.log('异常信息,左侧菜单加载异常,请重试!');
		}
	}
});

Ext.loadMenuFilter= function(records, opt) {
	var  id , pid ,iconCls, text, rowCls , viewType;
	if (opt.pid&&opt.id) {
		id = opt.id || 'id';
		pid = opt.pid || 'pid';
		text = opt.text || 'text';
		iconCls = opt.iconCls || 'iconCls';
		rowCls = opt.rowCls || 'rowCls';
		viewType = opt.viewType || 'viewType';
		var i, l, data = [],treeData = [], tmpMap = [];
		for (i = 0, l = records.length; i < l; i++) {
			data[i] = records[i].data;
			tmpMap[data[i][id]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][pid]] && data[i][id] != data[i][pid]) {
				if (!tmpMap[data[i][pid]]['children'])
					tmpMap[data[i][pid]]['children'] = [];
				data[i]['text'] = data[i][text];
				data[i]['iconCls'] = data[i][iconCls];
				data[i]['rowCls'] = data[i][rowCls];
				data[i]['viewType'] = data[i][viewType];
				data[i]['leaf'] = true;//推断为叶子节点
				tmpMap[data[i][pid]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][text];
				data[i]['iconCls'] = data[i][iconCls];
				data[i]['rowCls'] = data[i][rowCls];
				data[i]['viewType'] = data[i][viewType];
				treeData.push(data[i]);
			}
		}
		var arr = ['children','text','iconCls','rowCls','viewType','leaf','expanded','selectable'];
		for (i = 0, l = data.length; i < l; i++) {
			if(data[i]['children']&&data[i]['children'].length>0){
				delete data[i]['leaf'];
				delete data[i]['viewType'];
				data[i]['expanded'] = false;
				data[i]['selectable'] = false;
			}
			for(var j in data[i]){
				var flag = Ext.Array.contains(arr,j);
				if(!flag){
					delete data[i][j]
				}
			}
		}
		return treeData;
	}
	return records;
}