package org.example.expert.domain.common.dto;

import lombok.Getter;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUser(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.authorities = List.of(new SimpleGrantedAuthority(userRole.name()));

        System.out.println("AuthUser 생성됨: id = " + id + ", email = " + email + ", userRole = " + userRole.name());
    }

    public String getUserRole() {
        if (authorities == null || authorities.isEmpty()) {
            throw new IllegalArgumentException("authorities 목록이 비어있습니다.");
        }

        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("권한 정보가 없습니다."));
    }
}
