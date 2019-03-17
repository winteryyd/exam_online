Ext.define('App.page.BaseWindow', {
    extend: 'Ext.window.Window',
    xtype: 'basewindow',
    requires: [
        'Ext.layout.container.VBox'
    ],

    cls: 'auth-locked-window',
    closable: false,
    resizable: false,
    autoShow: true,
    titleAlign: 'center',
    maximized: true,
    modal: true,
    scrollable: true,

    layout: {
        type: 'vbox',
        align: 'center',
        pack: 'center'
    }
});
