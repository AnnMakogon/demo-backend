package dev.check.entity.EnumEntity;

import lombok.Getter;

@Getter
public enum GroupNumber {
    GROUP_1_1("1.1"),
    GROUP_1_2("1.2"),
    GROUP_1_3("1.3"),
    GROUP_2_1("2.1"),
    GROUP_3_1("3.1"),
    GROUP_3_2("3.2"),
    GROUP_3_3("3.3");

    private final String group;

    GroupNumber(String group) {
        this.group = group;
    }

    public static GroupNumber fromString(String groupName) {
        for (GroupNumber group : values()) {
            if (group.group.equals(groupName)) {
                return group;
            }
        }
        return null;
    }

}
