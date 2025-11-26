package model;

import java.sql.Date;

public class Activity {
	private String propertyId;
	private String propertyName;
	private String propertyType;
	private long propertyValue;
	private String activityType; // "ADD", "UPDATE", "DELETE"
	private Date activityDate;
	private Date addedDate; // Ngày thêm ban đầu

	public Activity(String propertyId, String propertyName, String propertyType, long propertyValue,
			String activityType, Date activityDate, Date addedDate) {
		this.propertyId = propertyId;
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.propertyValue = propertyValue;
		this.activityType = activityType;
		this.activityDate = activityDate;
		this.addedDate = addedDate;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public long getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(long propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
}
