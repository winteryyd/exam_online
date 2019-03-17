package com.ando.exam.sys.vo;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.ando.exam.sys.entity.Menu;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
/**
 * @Descrip: 头部菜单
 * @ClassName:  MenuHeadBar 
 * @author: ando
 * @date:   2019年2月2日 下午4:54:10    
 */
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class MenuHeadBar implements Serializable {
	private static final long serialVersionUID = 6354683061675750848L;
	private String itemId;
	private String iconCls;
	private String ui;
	private String href;
	private String hrefTarget;
	private String text;
	private String tooltip;

	public MenuHeadBar(Menu m) {
		this.setItemId("menuHeadBar" + m.getMenuId());
		this.href = m.getMenuHref();
		this.hrefTarget = m.getHrefTarget();
		this.iconCls = m.getIconCls();
		this.text = m.getText();
		this.ui = m.getUi();
		this.tooltip = m.getTooltip();
		if(StringUtils.isEmpty(hrefTarget)) {
			this.hrefTarget = "_self";
		}
		if(StringUtils.isEmpty(ui)) {
			this.ui = "header";
		}
	}
}
