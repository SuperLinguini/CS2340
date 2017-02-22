package tech.milind.cleanwatercrowdsourcing.model;

public class User {

    private String username;
    private String password;
    private String name;
    private String homeAddress;
    private String emailAddress;
    private UserType userType;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.name = "";
        this.homeAddress = "";
        this.emailAddress = "";
        this.userType = UserType.USER;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String address) {
        this.homeAddress = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String address) {
        this.emailAddress = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType type) {
        this.userType = type;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

}
