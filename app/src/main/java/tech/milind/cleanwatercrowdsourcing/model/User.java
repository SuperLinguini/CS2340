package tech.milind.cleanwatercrowdsourcing.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

public class User implements Comparable<User> {

    private String username;
    private String password;
    private String name;
    private String homeAddress;
    private String emailAddress;
    private UserType userType;
    private boolean banned;
    private Date creationDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.name = "";
        this.homeAddress = "";
        this.emailAddress = "";
        this.userType = UserType.USER;
        this.banned = false;
        this.creationDate = new Date();
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
     * sets the home address of the user to home address passed in
     * @param address the new address to be set to the user
     */
    public void setHomeAddress(String address) {
        this.homeAddress = address;
    }

    /**
     * gets the email address of the user
     * @return the email address of the user
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * sets the email address of the user to the email address passed in
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
     * sets the UserType of the user to the type passed in
     * @param type the new UserType to be set to the user
     */
    public void setUserType(UserType type) {
        this.userType = type;
    }

    /**
     * Checks if the user is banned.
     * @@return true if the user is banned
     */
    public boolean isBanned() {
        return this.banned;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }
    public void ban() {
        this.banned = true;
    }

    public void recover() {
        this.banned = false;
    }


    /**
     * checks if the password of the user matches the password passed in
     * @param password the password passed in to be checked with the user's password
     * @return whether the password of the user was equal to the password passed in
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }


    @Override
    public int compareTo(@NonNull User o) {
        return this.getCreationDate().compareTo(o.getCreationDate());
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(username);
//        dest.writeString(password);
//        dest.writeString(name);
//        dest.writeString(homeAddress);
//        dest.writeString(emailAddress);
//        dest.writeParcelable(userType, flags);
//        dest.writeByte((byte) (banned ? 1 : 0));
//    }
//
//    public static final Parcelable.Creator<User> CREATOR =
//            new Parcelable.Creator<User>() {
//                @Override
//                public User createFromParcel(Parcel source) {
//                    return new User(source);
//                }
//
//                @Override
//                public User[] newArray(int size) {
//                    return new User[size];
//                }
//            };

//    private User(Parcel in) {
//        this.username = in.readString();
//        this.password = in.readString();
//        this.name = in.readString();
//        this.homeAddress = in.readString();
//        this.emailAddress = in.readString();
//        this.userType = in.readParcelable(UserType.class.getClassLoader());
//        this.banned = in.readInt() == 1;
//    }

}
