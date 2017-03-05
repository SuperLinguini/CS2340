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

    /**
     * gets the username of the User
     * @return the user name of the User
     */
    public String getUsername() {
        return username;
    }

    /**
     * gets the name of the User
     * @return the name of the User
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the user to the name passed in
     * @param name the new name to be set to the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the home address of the user
     * @return the home address of the user
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     * sets the home address of the user to homeaddress passed in
     * @param address the new address to be set to the user
     */
    public void setHomeAddress(String address) {
        this.homeAddress = address;
    }

    /**
     * gets the email address of the user
     * @return the email adress of the user
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * sets the email address of the user to the emailaddress passed in
     * @param address the new email address to be set to the user
     */
    public void setEmailAddress(String address) {
        this.emailAddress = address;
    }

    /**
     * gets the UserType of the user
     * @return the UserType of the user
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * sets the Usertype of the user to the type passed in
     * @param type the new Usertype to be set to the user
     */
    public void setUserType(UserType type) {
        this.userType = type;
    }

    /**
     * checks if the password of the user matches the password passed in
     * @param password the password passed in to be checked with the user's password
     * @return whether the password of the user was equal to the password passed in
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

}
