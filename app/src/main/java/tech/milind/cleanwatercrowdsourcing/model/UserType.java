package tech.milind.cleanwatercrowdsourcing.model;

import java.util.ArrayList;
import java.util.List;

public enum UserType {
    USER("User", 0),
    WORKER("Worker", 1),
    MANAGER("Manager", 2),
    ADMIN("Administrator", 3);

    private final String type;
    private final int level;

    UserType(String type, int level) {
        this.type = type;
        this.level = level;
    }

    /**
     * Gets the String value of the userType
     * @return the String value of the UserType
     */
    public String getType() {
        return type;
    }

    /**
     * gets the level of the UserType
     * @return the level of the UserType
     */
    public int getLevel() {
        return level;
    }

    /**
     * finds the Usertype given the Usertype value
     * @param type the usertype value
     * @return The UserType U if the value is found or null if value is not found
     */
    public static UserType findUserType(String type) {
        for (UserType u : UserType.values()) {
            if (u.getType().equals(type)) {
                return u;
            }
        }
        return null;
    }

    /**
     * makes a list of the UserType values
     * @return the list of the Usertype values
     */
    public static List<String> toList() {
        List<String> list = new ArrayList<String>(4);
        for (UserType u : UserType.values()) {
            list.add(u.type);
        }
        return list;
    }

}
