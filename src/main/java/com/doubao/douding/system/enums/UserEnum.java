package com.doubao.douding.system.enums;

import lombok.Getter;

/**
 * @author: Johnson
 * @create: 2023-11-23
 * @Description: user enum
 */
public class UserEnum {

    /**
     * describe the status of user
     */
    @Getter
    public enum UserStatusEnum {
        LOCKED(0, "LOCKED"),
        NORMAL(1, "NORMAL"),
        DISABLED(2, "DISABLED");

        private Integer code;

        private String desc;

        UserStatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static UserStatusEnum getEnum(Integer code) {
            if (code == null) {
                return null;
            }
            for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
                if (userStatusEnum.getCode().equals(code)) {
                    return userStatusEnum;
                }
            }
            return null;
        }

        public static UserStatusEnum getEnum(String desc) {
            if (desc == null) {
                return null;
            }
            for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
                if (userStatusEnum.getDesc().equals(desc)) {
                    return userStatusEnum;
                }
            }
            return null;
        }
    }

    @Getter
    public enum GenderEnum {
        MALE(0, "MALE"),
        FEMALE(1, "FEMALE");

        private Integer code;

        private String desc;

        GenderEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static GenderEnum getEnum(Integer code) {
            if (code == null) {
                return null;
            }
            for (GenderEnum genderEnum : GenderEnum.values()) {
                if (genderEnum.getCode().equals(code)) {
                    return genderEnum;
                }
            }
            return null;
        }

        public static GenderEnum getEnum(String desc) {
            if (desc == null) {
                return null;
            }
            for (GenderEnum genderEnum : GenderEnum.values()) {
                if (genderEnum.getDesc().equals(desc)) {
                    return genderEnum;
                }
            }
            return null;
        }

    }

}
