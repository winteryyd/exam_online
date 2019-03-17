Ext.define('App.page.Login', {
    extend: 'App.page.BaseWindow',
    xtype: 'login',

    requires: [
        'App.page.Dialog',
        'Ext.container.Container',
        'Ext.form.field.Text',
        'Ext.form.field.Checkbox',
        'Ext.button.Button'
    ],

    title: '登录',
    defaultFocus: 'authdialog', // Focus the Auth Form to force field focus as well

    items: [
        {
            xtype: 'authdialog',
            defaultButton : 'loginButton',
            autoComplete: true,
            bodyPadding: '20 20',
            cls: 'auth-dialog-login',
            header: false,
            width: 415,
            layout: {
                type: 'vbox',
                align: 'stretch'
            },

            defaults : {
                margin : '5 0'
            },

            items: [
                {
                    xtype: 'label',
                    text: '请输入您的账号'
                },
                {
                    xtype: 'textfield',
                    cls: 'auth-textbox',
                    name: 'empid',
                    bind: '{empid}',
                    height: 55,
                    hideLabel: true,
                    allowBlank : false,
                    emptyText: '工号',
                    triggers: {
                        glyphed: {
                            cls: 'trigger-glyph-noop auth-email-trigger'
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    cls: 'auth-textbox',
                    height: 55,
                    hideLabel: true,
                    emptyText: '密码',
                    inputType: 'password',
                    name: 'password',
                    bind: '{password}',
                    allowBlank : false,
                    triggers: {
                        glyphed: {
                            cls: 'trigger-glyph-noop auth-password-trigger'
                        }
                    }
                },
                {
                    xtype: 'container',
                    layout: 'hbox',
                    items: [
                        {
                            xtype: 'checkboxfield',
                            flex : 1,
                            cls: 'form-panel-font-color rememberMeCheckbox',
                            height: 30,
                            bind: '{persist}',
                            boxLabel: 'Remember me'
                        },
                        {
                            xtype: 'box',
                            html: '<a href="#passwordreset" class="link-forgot-password"> Forgot Password ?</a>'
                        }
                    ]
                },
                {
                    xtype: 'button',
                    reference: 'loginButton',
                    scale: 'large',
                    ui: 'soft-green',
                    iconAlign: 'right',
                    iconCls: 'x-fa fa-angle-right',
                    text: '登录',
                    formBind: true,
                    listeners: {
                        click: function(){
                        	var items = this.ownerCt.items.items,
                        	empid = items[1].getValue(),
                        	password = items[2].getValue();
                        	Ext.Ajax.request({
    							url : app_login,
    							method : 'POST',
    							params : { empid:empid,password:password },
    							success : function(result) {
    								if(result.responseText == 'success'){
    									 window.location.href=app_index;
    								}else{
    									Ext.toast(result.responseText, "提示信息", 't');
    								}
    							}
    						});
                        }
                    }
                }
            ]
        }
    ],

    initComponent: function() {
        this.addCls('user-login-register-container');
        this.callParent(arguments);
    }
});
