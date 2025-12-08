package model;

public class User {

	protected String idUser;
	protected String fullName;
	protected String email;
	protected String password;
	protected String phone;
	protected String address;
	protected String role;
	protected String gender;
	
	public User(String idUser, String fullName, String email, String password, String phone, String address,
			String role, String gender) {
		super();
		this.idUser = idUser;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.gender = gender;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdUser() {
		return idUser;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public String getRole() {
		return role;
	}

	public String getGender() {
		return gender;
	}
}
