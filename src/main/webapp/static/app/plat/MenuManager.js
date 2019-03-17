Ext.define('App.plat.MenuManagerStore', {
    extend: 'Ext.data.TreeStore',
    constructor : function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([ Ext.apply({
			autoLoad : true,
			proxy : {
				type : 'ajax',
				url : sys_menu+"/getMenus",
				reader : {
					type : 'json'
				},
				extraParams:{
					pid:'0'
			    }
			},
			listeners : {
				nodebeforeexpand:function(node,opts){
					if(node.raw){
						this.proxy.extraParams.pid=node.raw.menuId;
					}
			　　 }
			}
		}, cfg) ]);
	}
});
Ext.define('App.plat.MenuManager', {
    extend: 'Ext.tree.Panel',
    xtype: 'menumanager',
    title: '菜单管理',
    store: new Ext.create('App.plat.MenuManagerStore'),
    requires: [
    	'App.plat.RoleManager'
    ],
    tbar: {
        items: [
        	{
                text: '添加子菜单',
                iconCls: 'x-fa fa-plus',
            	handler: function () {
            		var selectedItem=getSelection(this);
                    if(!selectedItem) return;
                    var menuManager = this.ownerCt.ownerCt;
            		Ext.create('App.plat.MenuMangerForm',{
    					multiSelect : true,
    					params : {
    						mode 	: 'add',
    						title	: '添加子分类',
    						record 	: selectedItem,
    						manager : menuManager
    					}
    				}).show();
            	}
            }, '-',
            {
                text: '修改',
                iconCls: 'x-fa fa-edit',
                handler: function () {
                	var selectedItem=getSelection(this);
                    if(!selectedItem) return;
                    var menuManager = this.ownerCt.ownerCt;
            		Ext.create('App.plat.MenuMangerForm',{
    					multiSelect : true,
    					params : {
    						mode   : 'edit',
    						title  : '修改',
    						record : selectedItem,
    						manager: menuManager
    					}
    				}).show();
            	}
            }, '-',
            {
                text: '删除',
                iconCls: 'x-fa fa-times',
                handler: function () {
                	var selectedItem=getSelection(this);
                    if(!selectedItem) return;
                    var menuManager = this.ownerCt.ownerCt;
                    Ext.MessageBox.confirm('提示', '确定要删除所选的记录？', function(btn) {
    					if (btn == "yes") {
    						Ext.Ajax.request({
    							url : sys_menu+"/delete",
    							method : 'POST',
    							params : { menuId: selectedItem.data.menuId },
    							success : function(result) {
    								if (Ext.decode(result.responseText).success) {
    									menuManager.getStore().load({params:{pid:1}});
    									Ext.toast("删除成功！", "提示信息", 'tr');
    								} else {
    									Ext.Msg.alert("提示信息", "删除失败！");
    								}
    							}
    						});
    					}
    				});
            	}
            }, '-',
            {
                text: '角色管理',
                iconCls: 'x-fa fa-address-book',
                handler: function () {
                	var selectedItem=getSelection(this);
                    if(!selectedItem) return;
                    var menuManager = this.ownerCt.ownerCt;
                    Ext.create("App.plat.MenuRoleManger",{
                    	selected:selectedItem,
                    	tbar:{
	                            xtype: 'breadcrumb',
	                            showIcons: true,
	                            store: menuManager.getStore(),
	                            listeners: {
	                            	beforerender : function( _this, eOpts){
	                            		_this.setSelection(selectedItem);
	                            	},
	                            	change : function( _this, node, prevNode, eOpts ){
	                            		_this.ownerCt.selected = node;
	                            		var store = _this.ownerCt.items.items[0].getStore();
	                            		Ext.apply(store.proxy.extraParams, {
	                    					menuId : node.get('menuId')
	                    				});
	                            		store.load();
	                            	}
	                            },
	                            items: [{
	                                xtype: 'component',
	                                html: '菜单路径:',
	                                style: {
	                                    'margin-left': '10px',
	                                    'margin-right': '10px'
	                                }
	                            }]
                            }
                    }).show();
                    
                }
            }, '-',
            {
                text: "刷新",
                iconCls: "x-fa fa-refresh",
                handler: function () {
                	var menuManager = this.ownerCt.ownerCt;
                	menuManager.getStore().load({params:{pid:1}});
                }
            }, '-',
            {
                text: '展开所有',
                iconCls: 'x-fa fa-arrows',
                handler: function () {
                	var menuManager = this.ownerCt.ownerCt;
                	menuManager.expandAll();
                }
            }, '-',
            {
                text: '折叠所有',
                iconCls: 'x-fa fa-compress',
                handler: function () {
                	var menuManager = this.ownerCt.ownerCt;
                	menuManager.collapseAll();
                }
            }
        ]
    },
	rootVisible: true,
	useArrows:true,
    listeners: {
    	beforerender :function(_this,eOpts){
    		_this.setRootNode(_this.getRootNode().childNodes[0]);
    	}
    },
    columns: [{
        xtype: 'treecolumn',
        text: '菜单名称',
        flex: 2,
        dataIndex: 'text',
        editable: true,
        renderer: function(value, metaData, record, rowIndex, colIndex, store, view){
	        return value;
	    }
    },{
        text: '菜单链接',
        flex: 1,
        dataIndex: 'menuHref'
    },{
        text: '视图名称',
        flex: 1,
        dataIndex: 'viewType'
    },{
        text: '图标',
        flex: 1,
        dataIndex: 'iconCls',
    	renderer: function(value){
        	return "<span class='"+value+"'></span>";
	    }
    }, {
        text: '附加图标',
        flex: 1,
        dataIndex: 'rowCls',
        renderer: function(value){
        	return "<div class='"+value+"' style='position:relative;height:10px;left:-20%;margin-top: 5px;'></div>";
	    }
    },{
        text: '提示信息',
        flex: 1,
        dataIndex: 'tooltip'
    }, {
        text: '排序',
        flex: 1,
        dataIndex: 'rank',
        editable: true
    }]
});

Ext.define('App.plat.MenuMangerForm', {
	extend : 'Ext.window.Window',
	height : 400,
	width : 400,
	resizable : false,
	layout : {
		type : 'border'
	},
	modal : true,
	multiSelect : true,
	buttons:[{
        text: '保存',
        iconCls: 'x-fa fa-save',
        handler: function () {
        	var win = this.ownerCt.ownerCt;
        	var form = win.items.items[0];
        	var manager = win.params.manager;
        	var urlPath = "";
        	if (win.params.mode == 'add') {
        		urlPath = sys_menu+"/add";
        	} else if (win.params.mode == 'edit') {
        		urlPath = sys_menu+"/edit";
        	}
			if (form.isValid()) {
				form.submit({
					url : urlPath,
					success : function(form, action) {
						if (action.result.success) {
							Ext.Msg.alert('提示信息', "保存成功！");
							win.close();
							manager.getStore().load({params:{pid:1}});
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
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [ {
				xtype : 'form',
				region : 'center',
				bodyPadding : 25,
				header : false,
				items : [{
					xtype: 'hidden',
					name : 'menuId'
				},{
					xtype: 'hidden',
					name : 'pid'
				},{
					xtype: 'textfield',
					anchor : '100%',
					fieldLabel : '父节点',
					name : 'ptext',
					editable : false,
					labelWidth : 70
				}, {
					xtype: 'textfield',
					anchor : '100%',
					fieldLabel : '菜单名称',
					name : 'text',
					labelWidth : 70,
					allowBlank : false,
					afterLabelTextTpl : '<span style="color:red">*</span>'
				},{
					xtype: 'combobox',
					anchor : '100%',
					fieldLabel : '图标',
					name : 'iconCls',
					labelWidth : 70,
			        queryMode: 'local',
			        displayField: 'name',
			        valueField: 'name',
			        tpl: Ext.create('Ext.XTemplate',
			            '<ul class="x-list-plain"><tpl for=".">',
			            '<li role="option" class="x-boundlist-item {name}"></li>',
			            '</tpl></ul>'
			        ),
			        displayTpl: Ext.create('Ext.XTemplate','<tpl for=".">','{name}','</tpl>'),
			        editable: false,
			        store: Ext.create('App.main.IconStore') 
				},{
					xtype: 'textfield',
					anchor : '100%',
					fieldLabel : '菜单链接',
					name : 'menuHref',
					labelWidth : 70,
					allowBlank : false,
					afterLabelTextTpl : '<span style="color:red">*</span>'
				},{
					xtype: 'textfield',
					anchor : '100%',
					fieldLabel : '视图名称',
					name : 'viewType',
					labelWidth : 70,
					allowBlank : false,
					afterLabelTextTpl : '<span style="color:red">*</span>'
				},{
					xtype: 'combobox',
					anchor : '100%',
					fieldLabel : '附加图标',
					name : 'rowCls',
					labelWidth : 70,
			        queryMode: 'local',
			        displayField: 'abbr',
			        valueField: 'name',
			        editable: false,
			        store: [
			        	 { abbr: '空', name:null},
			             { abbr: 'hot', name:'nav-tree-badge nav-tree-badge-hot'},
			             { abbr: 'new', name: 'nav-tree-badge nav-tree-badge-new' }
			         ]
				}, {
					xtype: 'textfield',
					anchor : '100%',
					fieldLabel : '提示信息',
					name : 'tooltip',
					labelWidth : 70
				}, {
					xtype: 'textfield',
					anchor : '100%',
					fieldLabel : '排序',
					name : 'rank',
					labelWidth : 70
				}]
			} ],
			listeners : {
				show : {
					fn : me.onFormWinShow,
					scope : me
				}
			}
		});

		me.callParent(arguments);
	},
	onFormWinShow : function(component, eOpts) {
		this.setTitle(this.params.title);
		var form = this.items.items[0].getForm();
		var record = this.params.record;
		if (this.params.mode == 'add') {
			form.findField("pid").setValue(record.data.menuId);
			form.findField("ptext").setValue(record.data.text);
			if(record.data.pid == 0){
				form.findField("viewType").setValue("(NULL)").hide();
				form.findField("rowCls").hide();
			}else{
				form.findField("menuHref").setValue(record.data.menuHref).hide();
				form.findField("tooltip").hide();
			}
		} else if (this.params.mode == 'edit') {
			form.loadRecord(record);
			form.findField("ptext").setValue(record.parentNode.data.text);
			if(record.data.pid == 1){
				form.findField("viewType").setValue("(NULL)").hide();
				form.findField("rowCls").hide();
			}else{
				form.findField("menuHref").setValue(record.data.menuHref).hide();
				form.findField("tooltip").hide();
			}
		}
	}
});

Ext.define('App.plat.RoleMenuManagerStore', {
	extend : 'Ext.data.Store',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url : sys_role+"/getMenuRolePage",
		reader : {
			type : 'json',
			root : 'roles',
			totalProperty : 'total'
		}
	}
});

Ext.define('App.plat.MenuRoleManger', {
	extend : 'Ext.window.Window',
	initComponent:function(){  
        Ext.apply(this,{  
        	id : 'menuRoleManger',
        	height : 560,
        	width : 800,
        	title: '菜单角色权限管理',
        	layout : 'fit',
        	resizable : false,
        	modal : true,
        	items:{
        		xtype : 'gridpanel',
        		store : Ext.create('App.plat.RoleMenuManagerStore'),
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
        				var store = _this.getStore();
        				Ext.apply(store.proxy.extraParams, {
        					menuId : _this.ownerCt.selected.get('menuId')
        				});
        				store.load();
        			}
        		},
        		tbar : [
        			{
        				text : '分配角色',
        				iconCls : 'x-fa fa-plus',
        				handler : function() {
        					var gridpanel = this.ownerCt.ownerCt,
        					    roleMenuManger = gridpanel.ownerCt;
        					var selModel = gridpanel.getSelectionModel();
        	                if(!selModel.hasSelection()){
        	                 	Ext.Msg.alert("提示信息","请选中要分配菜单权限的角色信息！");
        	                 	return;
        	                }
        	                var selection = selModel.getSelection();//得到被选择的记录数组
        	                var arrRoleId = [],arrMenuId = [];
        	                for (var i = 0; i < selection.length; i++) {
        	                    var model = selection[i];//得到model
        	                    if(!model.get("auth")){
        	                    	arrRoleId.push(model.get("roleId"));
        	                    }
        	                }
        	                
        	                var selected = roleMenuManger.selected;
        	                while(selected.parentNode){
        	                	arrMenuId.push(selected.get('menuId'));
        	                	selected = selected.parentNode;
        	                }
        	                if(arrRoleId.length>0&&arrMenuId.length>0){
        	                	Ext.Ajax.request({
        	                		url : sys_menu+"/assignPrivileges",
        	                		method : 'POST',
        	                		params : { menuIds : arrMenuId.join(','),roleIds:arrRoleId.join(',') },
        	                		success : function(result) {
        	                			gridpanel.getStore().load();
        	                			if (Ext.decode(result.responseText).success) {
        	                				Ext.Msg.alert("提示信息", "分配权限成功！");
        	                			} else {
        	                				Ext.Msg.alert("提示信息", "分配权限失败！");
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
        				        roleMenuManger = gridpanel.ownerCt;
        	        		var selModel = gridpanel.getSelectionModel();
        	                if(!selModel.hasSelection()){
        	                 	Ext.Msg.alert("提示信息","请选中要收回菜单权限的角色信息！");
        	                 	return;
        	                }
        	                var selection = selModel.getSelection();//得到被选择的记录数组
        	                var arrRoleId = [],arrMenuId = [];
        	                for (var i = 0; i < selection.length; i++) {
        	                    var model = selection[i];//得到model
        	                    if(model.get("auth")){
        	                    	arrRoleId.push(model.get("roleId"));
        	                    }
        	                }
        	                var selected = roleMenuManger.selected;
        	                while(selected.parentNode){
        	                	arrMenuId.push(selected.get('menuId'));
        	                	selected = selected.parentNode;
        	                }
        	                if(arrRoleId.length>0&&arrMenuId.length>0){
        	                	Ext.Ajax.request({
        	                		url : sys_menu+"/revokePrivileges",
        	                		method : 'POST',
        	                		params : { menuIds : arrMenuId.join(','),roleIds:arrRoleId.join(',') },
        	                		success : function(result) {
        	                			gridpanel.getStore().load();
        	                			if (Ext.decode(result.responseText).success) {
        	                				Ext.Msg.alert("提示信息", "收回权限成功！");
        	                			} else {
        	                				Ext.Msg.alert("提示信息", "收回权限失败！");
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