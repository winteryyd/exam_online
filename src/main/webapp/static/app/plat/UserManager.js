Ext.define('App.plat.UserManagerStore', {
	extend : 'Ext.data.Store',
	fields : [ 'empid', 'username','department','title','phone','email','password','active','hivename','jobqueue'],
	pageSize : 10,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url : sys_user+"/getPage",
		reader : {
			type : 'json',
			root : 'users',
			totalProperty : 'total'
		}
	}
});

Ext.define('App.plat.UserManager', {
	extend : 'Ext.grid.Panel',
	xtype : 'usermanager',
	store : Ext.create('App.plat.UserManagerStore'),
	title: '用户管理',
	forceFit : true,
	columns : [ {
		text : "工号",
		dataIndex : 'empid',
		width:60	
	}, {
		text : "姓名",
		dataIndex : 'username',
		width:70
	}, {
		text : "部门",
		dataIndex : 'department'
	}, {
		text : "岗位",
		dataIndex : 'title'
	}, {
		text : "电话",
		dataIndex : 'phone',
		width:70
	}, {
		text : "邮箱",
		dataIndex : 'email'
	}, {
		text : "hive用户",
		dataIndex : 'hivename'
	},{
		text : "任务队列",
		dataIndex : 'jobqueue'
	},{
		text : "状态",
		dataIndex : 'active',
		width : 60	,
		renderer: function(value){
	        if (value === 1) {
	            return '可用';
	        }
	        return '不可用';
	    }
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
				var userManager = this.ownerCt.ownerCt;
				Ext.create('App.plat.UserManagerForm',{
					params : {
						mode 	: 'add',
						title	: '添加用户',
						manager : userManager
					}
				}).show();
			}
		}, '-', {
			text : '修改',
			iconCls : 'x-fa fa-edit',
			handler : function() {
                var selectedItem=getSelection(this);
                if(!selectedItem) return;
                var userManager = this.ownerCt.ownerCt;
                Ext.create('App.plat.UserManagerForm',{
                	params : {
                		mode   : 'edit',
                		title  : '修改用户',
                		record : selectedItem,
                		manager: userManager
                	}
                }).show();
			}
		}, '-', {
			text : '删除',
			iconCls : 'x-fa fa-times',
			handler : function() {
				var selectedItem=getSelection(this);
                if(!selectedItem) return;
                var userManager = this.ownerCt.ownerCt;
                Ext.MessageBox.confirm('提示', '确定要删除所选的记录？', function(btn) {
					if (btn == "yes") {
						Ext.Ajax.request({
							url : sys_user+"/delete",
							method : 'POST',
							params : { empid: selectedItem.get("empid") },
							success : function(result) {
								if (Ext.decode(result.responseText).success) {
									userManager.getStore().load();
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
            text: '角色管理',
            iconCls: 'x-fa fa-address-book',
            handler: function () {
                var selectedItem=getSelection(this);
                if(!selectedItem) return;
                Ext.create("App.plat.UserRoleManger",{
                	selected:selectedItem
                }).show();
            }
        },'-',{
	        width: 200,
	        xtype: 'searchfield'
	    }
	],
	bbar : Ext.create('App.main.Paging')
});
/**
 * 新增修改
 */
Ext.define('App.plat.UserManagerForm', {
	extend : 'Ext.window.Window',
	height : 500,
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
					url : sys_user+"/save",
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
			name : 'password',
			value:'e10adc3949ba59abbe56e057f20f883e'
		},{
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '工号',
			name : 'empid',
			labelWidth : 60,
			allowBlank : false,
			afterLabelTextTpl : '<span style="color:red">*</span>'
		}, {
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '姓名',
			name : 'username',
			labelWidth : 60,
			allowBlank : false,
			afterLabelTextTpl : '<span style="color:red">*</span>'
		}, {
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '部门',
			name : 'department',
			labelWidth : 60
		}, {
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '岗位',
			name : 'title',
			labelWidth : 60
		}, {
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '电话',
			name : 'phone',
			labelWidth : 60,
			regex : /^[1]\d{10,10}/, //正则表达式在 
			regexText:"手机号无效！", //正则表达式错误提示
			allowBlank : false,
			maxLength: 11,
			afterLabelTextTpl : '<span style="color:red">*</span>'
		}, {
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '邮箱',
			name : 'email',
			vtype       : "email",//email格式验证  
	        vtypeText   : "邮箱无效！",//错误提示信息
			labelWidth : 60,
			allowBlank : false,
			afterLabelTextTpl : '<span style="color:red">*</span>'
		}, {
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : 'hive用户',
			name : 'hivename',
			labelWidth : 60
		},{
			fieldLabel : '队列',
			labelWidth : 60,
			name : 'jobqueue',
			anchor : '100%',
			xtype : 'combo',
			editable : false,
			store : Ext.create('App.main.DictStore',{dictType:'jobqueue'}),
			displayField : 'dictLabel',
			valueField : 'dictValue'
		},
		{
			fieldLabel : '状态',
			labelWidth : 60,
			name : 'active',
			anchor : '100%',
			xtype : 'combo',
			editable : false,
			store : Ext.create('App.main.DictStore',{dictType:'active'}),
			displayField : 'dictLabel',
			valueField : 'dictValue'
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

/**
 * 用户管理->权限管理
 */
Ext.define('App.plat.UserRoleManagerStore', {
	extend : 'Ext.data.Store',
	fields : [ 'auth','roleId', 'enRoleName','chRoleName','remark'],
	pageSize : 10,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url : sys_role+"/getUserRolePage",
		reader : {
			type : 'json',
			root : 'roles',
			totalProperty : 'total'
		}
	}
});

Ext.define('App.plat.UserRoleManger', {
	extend : 'Ext.window.Window',
	initComponent:function(){  
        Ext.apply(this,{  
        	height : 560,
        	width : 800,
        	title: '用户角色权限管理',
        	layout : 'fit',
        	resizable : false,
        	modal : true,
        	items:{
        		xtype : 'gridpanel',
        		store : Ext.create('App.plat.UserRoleManagerStore'),
        		header : false,
        		forceFit : true,
        		selType: 'checkboxmodel',
        		margin  : '1 3 1 3',
        		columns : [ 
        		{
        			text : "角色状态",
        			dataIndex : 'auth',
        			width : 60	,
        			renderer: function(value){
        				if(value){
        					return "已分配";
        				}
        				return "未分配";
        		    }
        		},{
        			text : "角色名称",
        			dataIndex : 'enRoleName'
        		}, {
        			text : "中文名称",
        			dataIndex : 'chRoleName'
        		}, {
        			text : "备注信息",
        			dataIndex : 'remark'
        		}],
        		listeners : {
        			afterrender : function(_this, eOpts) {
        				var selected = _this.ownerCt.selected,
        				    store = _this.getStore();
        				Ext.apply(store.proxy.extraParams, {
        					empid : selected.get("empid")
        	    		});
        				store.load();
        				
        				_this.down("toolbar").add(['-',{
                            xtype: 'component',
                            html: '用户：'+selected.get("empid")+'-'+selected.get("username"),
                            style: {
                                'margin-left': '10px'
                            }
                        }]);  
        			}
        		},
        		tbar : [
        			{
        				text : '分配角色',
        				iconCls : 'x-fa fa-plus',
        				handler : function() {
        					var gridpanel = this.ownerCt.ownerCt,
	        					selModel = gridpanel.getSelectionModel(),
	    					    selected = gridpanel.ownerCt.selected;
        	                if(!selModel.hasSelection()){
        	                 	Ext.Msg.alert("提示信息","请选中要分配的角色信息！");
        	                 	return;
        	                }
        	                var selections = selModel.getSelection();//得到被选择的记录数组
        	                var arrRoleId = [];
        	                for (var i = 0; i < selections.length; i++) {
        	                    var model = selections[i];//得到model
        	                    if(!model.get("auth")){
        	                    	arrRoleId.push(model.get("roleId"));
        	                    }
        	                }
        	                if(arrRoleId.length>0 && selected){
        	                	Ext.Ajax.request({
        	                		url : sys_role+"/saveUserRoles",
        	                		method : 'POST',
        	                		params : { empid : selected.get("empid"),roleIds:arrRoleId.join(',') },
        	                		success : function(result) {
        	                			gridpanel.getStore().load();
        	                			if (Ext.decode(result.responseText).success) {
        	                				Ext.Msg.alert("提示信息", "分配角色成功！");
        	                			} else {
        	                				Ext.Msg.alert("提示信息", "分配角色失败！");
        	                			}
        	                		}
        	                	});
        	                }
        				}
        			}, '-', {
        				text : '收回角色',
        				iconCls : 'x-fa fa-edit',
        				handler : function() {
        					var gridpanel = this.ownerCt.ownerCt,
	        					selModel = gridpanel.getSelectionModel(),
	    					    selected = gridpanel.ownerCt.selected;
	    	                if(!selModel.hasSelection()){
	    	                 	Ext.Msg.alert("提示信息","请选中要收回的角色信息！");
	    	                 	return;
	    	                }
	    	                var selections = selModel.getSelection();//得到被选择的记录数组
	    	                var arrRoleId = [];
	    	                for (var i = 0; i < selections.length; i++) {
	    	                    var model = selections[i];//得到model
	    	                    if(model.get("auth")){
	    	                       arrRoleId.push(model.get("roleId"));
	    	                    }
	    	                }
	    	                if(arrRoleId.length>0 && selected){
	    	                	console.log(arrRoleId,selected);
        	                	Ext.Ajax.request({
	    	                		url : sys_role+"/deleteUserRoles",
	    	                		method : 'POST',
	    	                		params : { empid : selected.get("empid"),roleIds:arrRoleId.join(',') },
	    	                		success : function(result) {
	    	                			gridpanel.getStore().load();
	    	                			if (Ext.decode(result.responseText).success) {
	    	                				Ext.Msg.alert("提示信息", "收回角色成功！");
	    	                			} else {
	    	                				Ext.Msg.alert("提示信息", "收回角色失败！");
	    	                			}
	    	                		}
	    	                	});
        	                }
        				}
        			},'-',{
        		        width: 200,
        		        xtype: 'searchfield'
        		    }
        		],
        		bbar : Ext.create('App.main.Paging')
        	}
        });
        this.callParent(arguments);   
	}
});
