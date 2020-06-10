package com.ojtproject.phonebook.form;

public class SearchResultForm {
	/**検索で一致した名前*/
	private String name;
	/**検索で一致した電話番号*/
	private String phoneNumber;

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
