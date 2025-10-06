package com.doubao.douding.system.enums;

import lombok.Getter;

@Getter
public enum SysResourceTypeEnum {
    DIR(1, "DIR"),
    MENU(2, "MENU"),
    BUTTOn(3, "BUTTON");

    private final Integer code;

    private final String desc;

    SysResourceTypeEnum(final Integer code, final String desc) {
        this.desc = desc;
        this.code = code;
    }
}
