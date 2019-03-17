package com.ando.exam.sys.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
/**
 * @Descrip: 菜单树
 * @ClassName:  MenuNode 
 * @author: ando
 * @date:   2019年2月20日 下午9:10:56    
 */
@Data
public class MenuNode {
	private Long menuId;
	private Long pid;
	private String iconCls;
	private String text;
	private Long rank;
	private boolean leaf=true;
	private boolean checked = false;
	private List<MenuNode> children;
	public MenuNode(Menu menu) {
		this.menuId = menu.getMenuId();
		this.pid = menu.getPid();
		this.iconCls = menu.getIconCls();
		this.text = menu.getText();
		this.rank = menu.getRank();
		this.children = new ArrayList<MenuNode>();
	}
	
	public void addChildren(MenuNode mn) {
		children.add(mn);
		this.leaf=false;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MenuNode menuNode = (MenuNode) o;

		if (menuId != null ? !menuId.equals(menuNode.menuId) : menuNode.menuId != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return menuId != null ? menuId.hashCode() : 0;
	}
}
