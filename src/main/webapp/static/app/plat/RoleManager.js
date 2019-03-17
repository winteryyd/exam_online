Ext.define('App.plat.RoleManagerStore', {
	extend : 'Ext.data.Store',
	fields : [ 'roleId', 'enRoleName','chRoleName','remark'],
	pageSize : 10,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url : sys_role+"/getPage",
		reader : {
			type : 'json',
			root : 'roles',
			totalProperty : 'total'
		}
	}
});

Ext.define('App.plat.RoleManager', {
	extend : 'Ext.grid.Panel',
	xtype : 'rolemanager',
	store : Ext.create('App.plat.RoleManagerStore'),
	title: '角色管理',
	forceFit : true,
	columns : [ {
		text : "角色名称",
		dataIndex : 'enRoleName'
	}, {
		text : "中文名称",
		dataIndex : 'chRoleName'
	}, {
		text : "备注信息",
		dataIndex : 'remark'
	} ],
	listeners : {
		afterrender : function(_this, eOpts) {
			_this.getStore().load();
		}
	},
	tbar : [
		{
			text : '新增',
			iconCls : 'x-fa fa-plus',
			handler : function() {
				var roleManager = this.ownerCt.ownerCt;
				Ext.create('App.plat.RoleMangerForm',{
					params : {
						mode 	: 'add',
						title	: '添加新角色',
						manager : roleManager
					}
				}).show();
			}
		}, '-', {
			text : '修改',
			iconCls : 'x-fa fa-edit',
			handler : function() {
				var selectedItem=getSelection(this);
                if(!selectedItem) return;
                var roleManager = this.ownerCt.ownerCt;
        		Ext.create('App.plat.RoleMangerForm',{
					params : {
						mode   : 'edit',
						title  : '修改角色',
						record : selectedItem,
						manager: roleManager
					}
				}).show();
			}
		}, '-', {
			text : '删除',
			iconCls : 'x-fa fa-times',
			handler : function() {
				var selectedItem=getSelection(this);
                if(!selectedItem) return;
                var roleManager = this.ownerCt.ownerCt;
                Ext.MessageBox.confirm('提示', '确定要删除所选的记录？', function(btn) {
					if (btn == "yes") {
						Ext.Ajax.request({
							url : sys_role+"/delete",
							method : 'POST',
							params : { roleId: selectedItem.data.roleId },
							success : function(result) {
								if (Ext.decode(result.responseText).success) {
									roleManager.getStore().load();
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
            text: '用户管理',
            iconCls: 'x-fa fa-id-badge',
            handler: function () {
            	var selectedItem=getSelection(this);
                if(!selectedItem) return;
                Ext.create("App.plat.RoleUserManger",{
                	selected:selectedItem
                }).show();
            }
        }, '-',
        {
            text: '菜单管理',
            iconCls: 'x-fa fa-sitemap',
            handler: function () {
            	var selectedItem=getSelection(this);
                if(!selectedItem) return;
                Ext.create("App.plat.RoleMenuManger",{
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

Ext.define('App.plat.RoleMangerForm', {
	extend : 'Ext.window.Window',
	height : 260,
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
					url : sys_role+"/save",
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
			name : 'roleId'
		},{
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '角色名称',
			name : 'enRoleName',
			labelWidth : 70,
			allowBlank : false,
			afterLabelTextTpl : '<span style="color:red">*</span>'
		},{
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '中文名称',
			name : 'chRoleName',
			labelWidth : 70,
			allowBlank : false,
			afterLabelTextTpl : '<span style="color:red">*</span>'
		},{
			xtype: 'textfield',
			anchor : '100%',
			fieldLabel : '备注信息',
			name : 'remark',
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

/**
 * 角色管理->权限管理
 */
Ext.define('App.plat.RoleMenuTreeStore', {
	extend : 'Ext.data.TreeStore',
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url : sys_role+"/getRoleMenuTree",
		reader : {
			type : 'json',
			root : 'menuTree'
		}
	},
	listeners : {
		load : function(store, records, successful, eOpts) {
			if (successful) {
				records[0].data.expanded = true;
				store.ownerCt.setStore({root:records[0].data});
			}else{
				console.log('加载异常,请重试!');
			}
		}
	}
});

Ext.define('App.plat.RoleMenuManger', {
	extend : 'Ext.window.Window',
	height : 600,
	width : 500,
	title: '角色菜单分配管理',
	layout : 'fit',
	resizable : false,
	modal : true,
	items:{
		xtype: 'treepanel',
		store :Ext.create('App.plat.RoleMenuTreeStore'),
		header : false,
		forceFit : true,
		rootVisible: true,
		useArrows:true,
		margin  : '1 3 1 3',
		listeners : {
			beforerender : function(_this, eOpts) {
				var roleId = _this.ownerCt.selected.get("roleId");
				var store = Ext.create('App.plat.RoleMenuTreeStore');
				Ext.apply(store.proxy.extraParams, {
					roleId : roleId
	    		});
	    		Ext.apply(store,{
	    			ownerCt : _this
	    		});
	    		store.load();
			},
			afterrender : function(_this, eOpts) {
				var selected = _this.ownerCt.selected;
				_this.down("toolbar").add(['-',{
                    xtype: 'component',
                    html: '角色：'+selected.get("enRoleName")+'，'+selected.get("chRoleName"),
                    style: {
                        'margin-left': '10px'
                    }
                }]);  
			},
			checkchange : function(node, state) { 
				checkParentNode(node,state);
				checkChildNodes(node,state);
				function checkParentNode(node,state){
					if(node.parentNode!=null && state){
						node.parentNode.set('checked', true);
						checkParentNode(node.parentNode,state);
					}
					if(node.parentNode!=null && !state){
						var flag = true;
						node.parentNode.childNodes.forEach(function(node, index, a){
							if(node.get("checked")){
								flag = false;
								return ;
							}
						});
						if(flag){
							node.parentNode.set('checked', false);
							checkParentNode(node.parentNode,state);
						}
					}
				}
				
				function checkChildNodes(node,state){
					node.childNodes.forEach(function(node, index, a){
						node.set('checked', state);
						checkChildNodes(node,state);
					});
				}
			} 
		},
		tbar : [
			{
				text: '展开',
                iconCls: 'x-fa fa-arrows',
				handler : function() {
					this.ownerCt.ownerCt.expandAll();
				}
			}, '-', {
				text: '保存',
		        iconCls: 'x-fa fa-save',
				handler : function() {
					var treePanel = this.ownerCt.ownerCt;
					var roleId = treePanel.ownerCt.selected.get("roleId"),arrMenuId=[];
					treePanel.getChecked().forEach(function(node){
						arrMenuId.push(node.get("menuId"));
					});
					
					if(roleId){
	                	Ext.Ajax.request({
	                		url : sys_role+"/saveRoleMenus",
	                		method : 'POST',
	                		params : { menuIds : arrMenuId.join(','),roleId:roleId },
	                		success : function(result) {
	                			if (Ext.decode(result.responseText).success) {
	                				Ext.Msg.alert("提示信息", "分配权限成功！");
	                				treePanel.ownerCt.close();
	                			} else {
	                				Ext.Msg.alert("提示信息", "分配权限失败！");
	                			}
	                		}
	                	});
	                }
				}
			}
		]
	}
});

/**
 * 角色管理->用户管理
 */
Ext.define('App.plat.RoleUserManagerStore', {
	extend : 'Ext.data.Store',
	fields : ['auth', 'empid', 'username','department','title'],
	pageSize : 10,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url : sys_user+"/getRoleUserPage",
		reader : {
			type : 'json',
			root : 'users',
			totalProperty : 'total'
		}
	}
});
Ext.define('App.plat.RoleUserManger', {
	extend : 'Ext.window.Window',
	initComponent:function(){  
        Ext.apply(this,{  
        	height : 560,
        	width : 800,
        	title: '角色用户权限管理',
        	layout : 'fit',
        	resizable : false,
        	modal : true,
        	items:{
        		xtype : 'gridpanel',
        		store : Ext.create('App.plat.RoleUserManagerStore'),
        		header : false,
        		forceFit : true,
        		selType: 'checkboxmodel',
        		margin  : '1 3 1 3',
        		columns : [ 
        		{
        			text : "权限状态",
        			dataIndex : 'auth',
        			width : 60	,
        			renderer: function(value){
        				if(value){
        					return "已分配";
        				}
        				return "未分配";
        		    }
        		},{
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
        		}],
        		listeners : {
        			afterrender : function(_this, eOpts) {
        				var selected = _this.ownerCt.selected,
        				    store = _this.getStore();
        				Ext.apply(store.proxy.extraParams, {
        					roleId : selected.get("roleId")
        	    		});
        				store.load();
        				
        				_this.down("toolbar").add(['-',{
                            xtype: 'component',
                            html: '角色：'+selected.get("enRoleName")+'-'+selected.get("chRoleName"),
                            style: {
                                'margin-left': '10px'
                            }
                        }]);  
        			}
        		},
        		tbar : [
        			{
        				text : '分配用户',
        				iconCls : 'x-fa fa-plus',
        				handler : function() {
        					var gridpanel = this.ownerCt.ownerCt,
	        					selModel = gridpanel.getSelectionModel(),
	    					    selected = gridpanel.ownerCt.selected;
        	                if(!selModel.hasSelection()){
        	                 	Ext.Msg.alert("提示信息","请选中要分配的用户信息！");
        	                 	return;
        	                }
        	                var selections = selModel.getSelection();//得到被选择的记录数组
        	                var arrEmpid = [];
        	                for (var i = 0; i < selections.length; i++) {
        	                    var model = selections[i];//得到model
        	                    if(!model.get("auth")){
        	                    	arrEmpid.push(model.get("empid"));
        	                    }
        	                }
        	                if(arrEmpid.length>0 && selected){
        	                	Ext.Ajax.request({
        	                		url : sys_user+"/saveRoleUsers",
        	                		method : 'POST',
        	                		params : { roleId : selected.get("roleId"),empids:arrEmpid.join(',') },
        	                		success : function(result) {
        	                			gridpanel.getStore().load();
        	                			if (Ext.decode(result.responseText).success) {
        	                				Ext.Msg.alert("提示信息", "分配用户成功！");
        	                			} else {
        	                				Ext.Msg.alert("提示信息", "分配用户失败！");
        	                			}
        	                		}
        	                	});
        	                }
        				}
        			}, '-', {
        				text : '收回用户',
        				iconCls : 'x-fa fa-edit',
        				handler : function() {
        					var gridpanel = this.ownerCt.ownerCt,
	        					selModel = gridpanel.getSelectionModel(),
	    					    selected = gridpanel.ownerCt.selected;
	    	                if(!selModel.hasSelection()){
	    	                 	Ext.Msg.alert("提示信息","请选中要收回的用户信息！");
	    	                 	return;
	    	                }
	    	                var selections = selModel.getSelection();//得到被选择的记录数组
	    	                var arrEmpid = [];
        	                for (var i = 0; i < selections.length; i++) {
        	                    var model = selections[i];//得到model
        	                    if(model.get("auth")){
        	                    	arrEmpid.push(model.get("empid"));
        	                    }
        	                }
        	                if(arrEmpid.length>0 && selected){
        	                	Ext.Ajax.request({
        	                		url : sys_user+"/deleteRoleUsers",
        	                		method : 'POST',
        	                		params : { roleId : selected.get("roleId"),empids:arrEmpid.join(',') },
        	                		success : function(result) {
        	                			gridpanel.getStore().load();
        	                			if (Ext.decode(result.responseText).success) {
        	                				Ext.Msg.alert("提示信息", "收回用户成功！");
        	                			} else {
        	                				Ext.Msg.alert("提示信息", "收回用户失败！");
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