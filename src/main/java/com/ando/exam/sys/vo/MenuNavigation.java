package com.ando.exam.sys.vo;

import java.io.Serializable;

import com.ando.exam.sys.entity.Menu;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @Descrip: 左侧菜单
 * @ClassName:  MenuNavigationTree 
 * @author: ando
 * @date:   2019年2月2日 下午4:54:34    
 */
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class MenuNavigation implements Serializable {
	private static final long serialVersionUID = 6354683061675750848L;
	private Long menuId;
	private Long pid;
	private String text;
	private String iconCls;
	private String rowCls;
	private String viewType;
	
	public MenuNavigation(Menu m) {
		this.menuId = m.getMenuId();
		this.pid = m.getPid();
		this.text = m.getText();
		this.iconCls = m.getIconCls();
		this.rowCls = m.getRowCls();
		this.viewType = m.getViewType();
	}
}
