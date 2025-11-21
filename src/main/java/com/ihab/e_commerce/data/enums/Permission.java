package com.ihab.e_commerce.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    CUSTOMER_READ("customer:read"),
    VENDOR_READ("vendor:read"),
    VENDOR_DELETE("vendor:delete"),
    VENDOR_UPDATE("vendor:update"),
    VENDOR_CREATE("vendor:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_CREATE("admin:create");

    @Getter
    private final String permission;

}
