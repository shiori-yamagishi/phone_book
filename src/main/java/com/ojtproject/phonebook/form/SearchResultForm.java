package com.ojtproject.phonebook.form;

public class SearchResultForm {

	private int id;
	/**検索で一致した名前*/
	private String name;
	/**検索で一致した電話番号*/
	private String phoneNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String searchName) {
		this.name = searchName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String searchNumber) {
		this.phoneNumber = searchNumber;
	}

}
