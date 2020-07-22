package com.ojtproject.phonebook.form;

public class SearchResultForm {

	private int id;

	/**検索で一致した名前*/
	private String name;

	/**検索で一致した電話番号*/
	private String phoneNumber;

	private int resultId;

	private String address;

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

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
