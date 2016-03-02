package com.infobird.entity;

public class InfoEntity {

	private String key;
	private String city;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public InfoEntity(String key, String city) {
		super();
		this.key = key;
		this.city = city;
	}
	public InfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "[key:" + key + "] [city:" + city + "]";
	}
}
