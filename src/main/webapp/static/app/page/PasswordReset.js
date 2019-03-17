Ext.define('App.page.PasswordReset', {
    extend: 'App.page.BaseWindow',
    xtype: 'passwordreset',

    requires: [
        'App.page.Dialog',
        'Ext.form.Label',
        'Ext.form.field.Text',
        'Ext.button.Button'
    ],

    title: '重置密码',

    defaultFocus : 'authdialog',  // Focus the Auth Form to force field focus as well

    items: [
        {
            xtype: 'authdialog',
            width: 455,
            defaultButton: 'resetPassword',
            autoComplete: true,
            bodyPadding: '20 20',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },

            defaults : {
                margin: '10 0'
            },

            cls: 'auth-dialog-login',
            items: [
                {
                    xtype: 'label',
                    cls: 'lock-screen-top-label',
                    text: '请输入您要修改的新密码:'
                },
                {
                    xtype: 'textfield',
                    cls: 'auth-textbox',
                    height: 55,
                    name: 'password',
                    inputType: 'password',
                    hideLabel: true,
                    allowBlank: false,
                    vtype:'forbidBlank',
                    emptyText: '新密码',
                    triggers: {
                        glyphed: {
                        	cls: 'trigger-glyph-noop auth-password-trigger'
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    cls: 'auth-textbox',
                    height: 55,
                    name: 'checkpassword',
                    inputType: 'password',
                    hideLabel: true,
                    allowBlank: false,
                    emptyText: '确认密码',
                    vtype:'confirm',
                    confirmField : 'password',
                    triggers: {
                        glyphed: {
                        	cls: 'trigger-glyph-noop auth-password-trigger'
                        }
                    }
                },
                {
                    xtype: 'button',
                    reference: 'resetPassword',
                    scale: 'large',
                    ui: 'soft-blue',
                    formBind: true,
                    iconAlign: 'right',
                    iconCls: 'x-fa fa-angle-right',
                    text: '设置密码',
                    listeners: {
                    	click: function(){
                    		var password = this.up("form").getForm().findField("password").getValue();
                        	Ext.Ajax.request({
    							url : app_reset,
    							method : 'POST',
    							params : { password:password },
    							success : function(result) {
    								if(result.responseText == 'success'){
    									Ext.MessageBox.confirm('提示',"密码设置成功，请重新登录！", function(btn) {
    										window.location.href=app_index;
    									});
    								}else{
    									Ext.toast(result.responseText, "提示信息", 't');
    								}
    							}
    						});
                        }
                    }
                },
                {
                    xtype: 'button',
                    scale: 'large',
                    ui: 'soft-blue',
                    iconAlign: 'right',
                    iconCls: 'x-fa fa-angle-left',
                    text: '返回',
                    listeners: {
                    	click: function(){
                    		window.history.back(-1); 
                    		this.up("window").close();
                        }
                    }
                }
            ]
        }
    ]
});
