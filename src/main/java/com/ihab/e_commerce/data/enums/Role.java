package com.ihab.e_commerce.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ihab.e_commerce.data.enums.Permission.*;

@RequiredArgsConstructor
public enum Role {
    /*--- each role with its permissions-----*/
    CUSTOMER(Set.of(
            CUSTOMER_READ
    )),
    VENDOR(Set.of(
            VENDOR_CREATE,
            VENDOR_DELETE,
            VENDOR_UPDATE,
            VENDOR_READ
    )),
    ADMIN(Set.of(
            ADMIN_CREATE,
            ADMIN_DELETE,
            ADMIN_UPDATE,
            ADMIN_READ,
            VENDOR_CREATE,
            VENDOR_DELETE,
            VENDOR_UPDATE,
            VENDOR_READ,
            CUSTOMER_READ
    ));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = this.getPermissions().stream().map(
                permission -> new SimpleGrantedAuthority(permission.getPermission())
        ).collect(Collectors.toList());

        /* ROLE_ IS Prefix no need to insert it every time*/
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
