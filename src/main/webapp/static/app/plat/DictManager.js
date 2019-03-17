Ext.define('App.plat.DictManagerStore', {
	extend : 'Ext.data.Store',
	fields : [ 'dictId', 'dictType','dictValue','dictLabel','rank'],
	pageSize : 10,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url : sys_dict+"/getPage",
		reader : {
			type : 'json',
			root : 'dicts',
			totalProperty : 'total'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts){
			var search = Ext.getCmp('dictSearch').getValue();
			Ext.apply(store.proxy.extraParams, {
				dictType : search,
				dictValue : search,
				dictLabel : search
			});
		}
	}
});

Ext.define('App.plat.DictManager', {
	extend : 'Ext.grid.Panel',
	xtype : 'dictmanager',
	store : Ext.create('App.plat.DictManagerStore'),
	title: '词典管理',
	forceFit : true,
	columns : [ {
		text : "词典类型",
		dataIndex : 'dictType'
	}, {
		text : "实际值",
		dataIndex : 'dictValue'
	}, {
		text : "展示值",
		dataIndex : 'dictLabel'
	}, {
		text : "排序值",
		dataIndex : 'rank'
	}],
	listeners : {
		afterrender : function(_this, eOpts) {
			_this.getStore().load();
		},
		sortchange: function (ct, column, direction, eOpts) {  
			var sortType = Ext.util.Format.uppercase(direction);
			var store = this.getStore();
			Ext.apply(store.proxy.extraParams, {
				direction:sortType,
				property:column.dataIndex
    		});
			store.load();
		}
	},
	tbar : [
		{
			text : '新增',
			iconCls : 'x-fa fa-plus',
			handler : function() {
				var dictManager = this.ownerCt.ownerCt;
				Ext.create('App.plat.DictManagerForm',{
					params : {
						mode 	: 'add',
						title	: '添加词典项',
						manager : dictManager
					}
				}).show();
			}
		}, '-', {
			text : '修改',
			iconCls : 'x-fa fa-edit',
			handler : function() {
				var dictManager = this.ownerCt.ownerCt;
        		var selModel = dictManager.getSelectionModel();
                if(!selModel.hasSelection()){
                	Ext.toast("请选中要修改的信息！", "提示信息", 'tr');
                 	return;
                }
                var selectedItem=selModel.getSelection()[0];
        		var dictManagerForm = new Ext.create('App.plat.DictManagerForm',{
					params : {
						mode   : 'edit',
						title  : '修改词典项',
						record : selectedItem,
						manager: dictManager
					}
				});
        		dictManagerForm.show();
			}
		}, '-', {
			text : '删除',
			iconCls : 'x-fa fa-times',
			handler : function() {
				var dictManager = this.ownerCt.ownerCt;
        		var selModel = dictManager.getSelectionModel();
                if(!selModel.hasSelection()){
                 	Ext.toast("请选中要删除的信息！", "提示信息", 'tr');
                 	return;
                }
                var selectedItem=selModel.getSelection()[0];
                Ext.MessageBox.confirm('提示', '确定要删除所选的记录？', function(btn) {
					if (btn == "yes") {
						Ext.Ajax.request({
							url : sys_dict+"/delete",
							method : 'POST',
							params : { dictId: selectedItem.get('dictId') },
							success : function(result) {
								if (Ext.decode(result.responseText).success) {
									dictManager.getStore().load();
									Ext.toast("删除成功！", "提示信息", 'tr');
								} else {
									Ext.Msg.alert("提示信息", "删除失败！");
								}
							}
						});
					}
				});
			}
		},'-', 
		{
			width : 150,
			id : 'dictSearch',
			xtype : 'textfield'
		},{
			text : '搜索',
			iconCls : 'x-fa fa-search',
			handler : function() {
				var dictManager = this.ownerCt.ownerCt;
				dictManager.getStore().load();
			}
		}
	],
	bbar : {
		xtype : 'pagingtoolbar',
		store : this.store,
		displayInfo : true,
		displayMsg : '显示 {0} - {1} 条，共计 {2} 条',
		emptyMsg : "没有数据",
		beforePageText : '第',
		afterPageText : '页/共{0}页'
	}
});

Ext.define('App.plat.DictManagerForm', {
	extend : 'Ext.window.Window',
	height : 300,
	width : 400,
	layout : 'fit',
	resizable : false,
	modal : true,
	buttons:[{
        text: '保存',
        iconCls: 'x-fa fa-save',
        handler: function () {
        	var win = this.ownerCt.ownerCt;
        	var form = win.down("form");
        	var manager = win.params.manager;
			if (form.isValid()) {
				form.submit({
					url : sys_dict+"/save",
					success : function(form, action) {
						if (action.result.success) {
							Ext.Msg.alert('提示信息', "保存成功！");
							win.close();
							manager.getStore().load();
						} else {
							Ext.Msg.alert('提示信息', action.result.message);
						}

					},
					failure : function(form, action) {
						Ext.Msg.alert('Failed', "系统繁忙,请稍后再试!")
					},
					waitMsg : '正在保存数据，稍后...'
				});
			} else {
				Ext.Msg.alert('提示信息', '数据校验未通过，请注意红色输入框，鼠标移上有错误提示!');
			}
        }
    },
    {
    	text: '关闭',
        iconCls: 'x-fa fa-close',
        handler: function () {
        	this.ownerCt.ownerCt.close();
        }
    }],
    items:{
		xtype : 'form',
		region : 'center',
		bodyPadding : 25,
		header : false,
		items : [{
			xtype: 'hidden',
			name : 'dictId'
		},{
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '词典类型',
			name : 'dictType',
			labelWidth : 70,
			allowBlank : false,
			afterLabelTextTpl : '<span style="color:red">*</span>'
		},{
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '实际值',
			name : 'dictValue',
			labelWidth : 70,
			allowBlank : false,
			afterLabelTextTpl : '<span style="color:red">*</span>'
		},{
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '展示值',
			name : 'dictLabel',
			labelWidth : 70,
			allowBlank : false,
			afterLabelTextTpl : '<span style="color:red">*</span>'
		},{
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '排序值',
			name : 'rank',
			labelWidth : 70
		}]
	},
	listeners : {
		show : function( _this, eOpts ) {
			_this.setTitle(_this.params.title);
			var form = _this.down("form");
			if (this.params.mode == 'add') {
			} else if (this.params.mode == 'edit') {
				var record = this.params.record;
				form.loadRecord(record);
			}
		}
	}
});