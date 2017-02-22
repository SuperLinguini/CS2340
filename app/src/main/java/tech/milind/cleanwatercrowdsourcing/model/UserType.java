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

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public static UserType findUserType(String type) {
        for (UserType u : UserType.values()) {
            if (u.getType().equals(type)) {
                return u;
            }
        }
        return null;
    }

    public static List<String> toList() {
        List<String> list = new ArrayList<String>(4);
        for (UserType u : UserType.values()) {
            list.add(u.type);
        }
        return list;
    }

}
