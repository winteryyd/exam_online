
/**
 * 获取grid panel选中的项
 * @param _this
 * @returns
 */
function getSelection(_this){
	var selModel = _this.ownerCt.ownerCt.getSelectionModel();
    if(!selModel.hasSelection()){
    	Ext.toast("请选中要操作的信息！", "提示信息", 'tr');
     	return;
    }
    return selModel.getSelection()[0]; 
}