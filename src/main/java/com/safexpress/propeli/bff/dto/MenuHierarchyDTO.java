package com.safexpress.propeli.bff.dto;

public class MenuHierarchyDTO {
	
	private Long id;
	private Long menu_level;
	private String menu_label;
	private String menu_fullid;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the menu_level
	 */
	public Long getMenu_level() {
		return menu_level;
	}
	/**
	 * @param menu_level the menu_level to set
	 */
	public void setMenu_level(Long menu_level) {
		this.menu_level = menu_level;
	}
	/**
	 * @return the menu_label
	 */
	public String getMenu_label() {
		return menu_label;
	}
	/**
	 * @param menu_label the menu_label to set
	 */
	public void setMenu_label(String menu_label) {
		this.menu_label = menu_label;
	}
	/**
	 * @return the menu_fullid
	 */
	public String getMenu_fullid() {
		return menu_fullid;
	}
	/**
	 * @param menu_fullid the menu_fullid to set
	 */
	public void setMenu_fullid(String menu_fullid) {
		this.menu_fullid = menu_fullid;
	}
	

}
