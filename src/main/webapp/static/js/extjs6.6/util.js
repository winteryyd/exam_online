
/**
 * 获取grid panel选中的项
 * @param grid
 * @returns
 */
function getSelection(_this,column){
	var grid = _this.ownerCt.ownerCt;
	if(column){
		var selModel = grid.getSelectionModel();
		if(!selModel.hasSelection()){
			Ext.toast("请选中要操作的信息！", "提示信息", 'tr');
			return;
		}
		var selections = selModel.getSelection();//得到被选择的记录数组
		var arrId = [];
		for (var i = 0; i < selections.length; i++) {
			var model = selections[i];//得到model
			arrId.push(model.get(column));
		}
		return arrId.join(",");
	}else{
		var selModel = grid.getSelectionModel();
	    if(!selModel.hasSelection()){
	    	Ext.toast("请选中要操作的信息！", "提示信息", 'tr');
	     	return;
	    }
	    return selModel.getSelection()[0];
	}
}