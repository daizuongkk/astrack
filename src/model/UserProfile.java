package model;

public class UserProfile {
	private String fullName;
	private String email;
	private String phone;
	private String dob;
	private String address;
	private String occupation;

	public UserProfile() {
	}

	public UserProfile(String fullName, String email, String phone, String dob, String address, String occupation) {
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.dob = dob;
		this.address = address;
		this.occupation = occupation;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

}
