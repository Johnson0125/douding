package com.doubao.douding.system.enums;

import lombok.Getter;

public class SysResourceEnum {
    @Getter
    public enum SysResourceTypeEnum {
        DIR(1, "DIR"),
        MENU(2, "MENU"),
        BUTTON(3, "BUTTON");

        private final Integer code;

        private final String desc;

        SysResourceTypeEnum(final Integer code, final String desc) {
            this.desc = desc;
            this.code = code;
        }
    }

    @Getter
    public enum SysResourceStatusEnum {
        NORMAL(1, "NORMAL"),
        DISABLE(0, "DISABLE");

        private final Integer code;

        private final String desc;

        SysResourceStatusEnum(final Integer code, final String desc) {
            this.desc = desc;
            this.code = code;
        }
    }
}

