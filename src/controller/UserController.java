package controller;

import database.UserHandler;
import model.User;

public class UserController {

	private UserHandler userHandler = new UserHandler();
	
	public User login(String email, String password) {
		return userHandler.login(email, password);
	}
	
	public String register(String name, String email, String pass, String confPass, String phone, String address, String gender) {
        // 1. Full Name: Cannot be empty
        if(name.isEmpty()) {
            return "Full Name cannot be empty!";
        }

        // 2. Email: Must be filled, ends with @gmail.com
        if(email.isEmpty() || !email.endsWith("@gmail.com")) {
            return "Email must end with @gmail.com!";
        }

        // 3. Password: Must be at least 6 characters
        if(pass.length() < 6) {
            return "Password must be at least 6 characters!";
        }

        // 4. Confirm Password: Must match Password
        if(!pass.equals(confPass)) {
            return "Confirm Password must match Password!";
        }

        // 5. Validate Phone Number without REGEX
        if(phone.length() < 10 || phone.length() > 13) {
            return "Phone number must be between 10-13 digits!";
        }
        
        boolean isNumeric = true;
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                isNumeric = false;
                break;
            }
        }
        
        if(!isNumeric) {
            return "Phone number must be numeric (digits only)!";
        }

        // 6. Address: Must be filled
        if(address.isEmpty()) {
            return "Address cannot be empty!";
        }

        // 7. Gender: Must be chosen
        if(gender.isEmpty()) {
            return "Please select a gender!";
        }

        // 8. Check Uniqueness Email and Insert to Database
        boolean isEmailAvailable = userHandler.checkEmailAvailability(email);
        
        if (!isEmailAvailable) {
            return "Email is already registered!";
        }

        // Insert Data via Handler
        boolean success = userHandler.addNewUser(name, email, pass, phone, address, gender);
        
        if(success) {
        	return "SUCCESS";
        } else {
        	return "Database connection failed";
        }
	}
	
	public String updateProfile(User currentUser, String name, String email, String phone, String address, String inputPass, String inputConfPass, String gender) {

		String finalPassword;
		
        // 1. Validate Name
        if(name.isEmpty()) {
            return "Full Name cannot be empty!";
        }

        // 2. Validate Email
        if(email.isEmpty() || !email.endsWith("@gmail.com") || email.indexOf('@') < 0) {
            return "Email must be valid (@gmail.com)!";
        }

        // 3. If Password PasswordField is empty, take from old Password
        if (inputPass.isEmpty()) {
            finalPassword = currentUser.getPassword();
        } else {
            if(inputPass.length() < 6) {
                return "New Password must be at least 6 characters!";
            }
            if(!inputPass.equals(inputConfPass)) {
                return "Confirm Password must match!";
            }
            finalPassword = inputPass;
        }

        // 4. Validate Phone Number
        if(phone.length() < 10 || phone.length() > 13) {
            return "Phone number must be 10-13 digits!";
        }
        boolean isNumeric = true;
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                isNumeric = false; break;
            }
        }
        if(!isNumeric) {
            return "Phone number must be numeric!";
        }

        // 5. Validate Address and Gender
        if(address.isEmpty()) {
            return "Address cannot be empty!";
        }
        if(gender.isEmpty()) {
            return "Please select a gender!";
        }

        // 6. Check Email Uniqueness
        boolean isAvailable = userHandler.checkEmailAvailabilityForUpdate(email, currentUser.getIdUser());
        if(!isAvailable) {
            return "Email is already taken!";
        }

        // 7. Update Database
        boolean success = userHandler.editProfile(currentUser.getIdUser(), name, email, finalPassword, phone, address, gender);
        
        if(success) {
            return "SUCCESS";       
        } else {
            return "Database connection failed";
        }
    }
}
