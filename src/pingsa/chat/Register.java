package pingsa.chat;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Register {

    
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }
    public boolean checkPasswordComplexity(String password) {

        boolean hasUppercase = !password.equals(password.toLowerCase());

        boolean hasNumber = password.matches(".*\\d.*");

        boolean hasSpecial =
                password.matches(".*[!@#$%^&*()].*");

        return password.length() >= 8
                && hasUppercase
                && hasNumber
                && hasSpecial;
    }

    
    public boolean checkCellPhoneNumber(String cellPhone) {

        return Pattern.matches("^\\+27\\d{9}$", cellPhone);
    }

    
    public static void main(String[] args) {

        Register register = new Register();
        String username = "abc_d";
        String password = "Password@1";
        String cellPhone = "+27812345678";

        
        if (register.checkUserName(username)) {
            System.out.println("Username successfully captured");
        } else {
            System.out.println("Username is not correctly formatted");
        }

        if (register.checkPasswordComplexity(password)) {
            System.out.println("Password successfully captured");
        } else {
            System.out.println("Password is not correctly formatted");
        }

                if (register.checkCellPhoneNumber(cellPhone)) {
            System.out.println("Cell phone number successfully added");
        } else {
            System.out.println("Cell phone number incorrectly formatted");
        }
    }
}