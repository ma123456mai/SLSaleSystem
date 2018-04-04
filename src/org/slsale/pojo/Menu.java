package org.slsale.pojo;

import java.util.List;

public class Menu {
	private Function mainMenu;
	private List<Function> subMenus;
	public Function getMainMenu() {
		return mainMenu;
	}
	public void setMainMenu(Function mainMenu) {
		this.mainMenu = mainMenu;
	}
	public List<Function> getSubMenus() {
		return subMenus;
	}
	public void setSubMenus(List<Function> subMenus) {
		this.subMenus = subMenus;
	}
	
}
