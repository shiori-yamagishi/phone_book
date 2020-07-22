package com.ojtproject.phonebook.form;

public class SearchForm {

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**検索キーワード*/
	private String keyword;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/*住所検索*/
	private String address;
}
