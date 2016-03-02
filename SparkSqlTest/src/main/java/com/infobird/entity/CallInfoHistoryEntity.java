package com.infobird.entity;

public class CallInfoHistoryEntity {

	private String phoneNo; 
	private String city;
	private String numberSegment;
	private String isp;
	private String completeTime;
	private String status;
	private String ringingDuration;
	private String talkingTime;
	private String questionAnswerId;
	private String keyStamp;
	private String taskId;
	private String huashuId;
	private String updateTime;
	private String age;
	private String phone;
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNumberSegment() {
		return numberSegment;
	}
	public void setNumberSegment(String numberSegment) {
		this.numberSegment = numberSegment;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRingingDuration() {
		return ringingDuration;
	}
	public void setRingingDuration(String ringingDuration) {
		this.ringingDuration = ringingDuration;
	}
	public String getTalkingTime() {
		return talkingTime;
	}
	public void setTalkingTime(String talkingTime) {
		this.talkingTime = talkingTime;
	}
	public String getQuestionAnswerId() {
		return questionAnswerId;
	}
	public void setQuestionAnswerId(String questionAnswerId) {
		this.questionAnswerId = questionAnswerId;
	}
	public String getKeyStamp() {
		return keyStamp;
	}
	public void setKeyStamp(String keyStamp) {
		this.keyStamp = keyStamp;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getHuashuId() {
		return huashuId;
	}
	public void setHuashuId(String huashuId) {
		this.huashuId = huashuId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public CallInfoHistoryEntity(String phoneNo, String city,
			String numberSegment, String isp, String completeTime,
			String status, String ringingDuration, String talkingTime,
			String questionAnswerId, String keyStamp, String taskId,
			String huashuId, String updateTime, String age, String phone) {
		super();
		this.phoneNo = phoneNo;
		this.city = city;
		this.numberSegment = numberSegment;
		this.isp = isp;
		this.completeTime = completeTime;
		this.status = status;
		this.ringingDuration = ringingDuration;
		this.talkingTime = talkingTime;
		this.questionAnswerId = questionAnswerId;
		this.keyStamp = keyStamp;
		this.taskId = taskId;
		this.huashuId = huashuId;
		this.updateTime = updateTime;
		this.age = age;
		this.phone = phone;
	}
	public CallInfoHistoryEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CallInfoHistoryEntity [phoneNo=" + phoneNo + ", city=" + city
				+ ", numberSegment=" + numberSegment + ", isp=" + isp
				+ ", completeTime=" + completeTime + ", status=" + status
				+ ", ringingDuration=" + ringingDuration + ", talkingTime="
				+ talkingTime + ", questionAnswerId=" + questionAnswerId
				+ ", keyStamp=" + keyStamp + ", taskId=" + taskId
				+ ", huashuId=" + huashuId + ", updateTime=" + updateTime
				+ ", age=" + age + ", phone=" + phone + "]";
	}
	
	
	
}
