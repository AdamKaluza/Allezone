package pl.edu.pjwstk.jaz;


public class RegisterRequest {
    private final String username;
    private final String password;


    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}































//    private final String username;
//    private final String password;
//    private final String role;


//    public RegisterRequest(String username, String password,String role) {
//        this.username = username;
//        this.password = password;
//        this.role=role;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getRole() {
//        return role;
//    }