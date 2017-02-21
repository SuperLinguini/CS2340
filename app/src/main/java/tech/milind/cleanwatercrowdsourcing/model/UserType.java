package tech.milind.cleanwatercrowdsourcing.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whe1996 on 2/20/17.
 */
public enum UserType {
    USER("user", 0),
    WORKER("worker", 1),
    MANAGER("manager", 2),
    ADMIN("administrator", 3);

    private final String type;
    private final int level;

    UserType(String type, int level) {
        this.type = type;
        this.level = level;
    }

    public String getType() { return type; }
    public int getLevel() { return level; }

    public static List<String> toList() {
        List<String> list = new ArrayList<String>(4);
        for (UserType u : UserType.values()) {
            list.add(u.type);
        }
        return list;
    }

    public static UserType findUserType(String standing) {
        for (UserType u : UserType.values()) {
            if (u.getType().equals(standing)) {
                return u;
            }
        }
        return null;
    }

}
