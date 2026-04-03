package org.example.expert.domain.common.dto;

import lombok.Getter;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// ✅ 레벨2 9번 UserDetails 상속 받음
@Getter
public class AuthUser implements UserDetails {

    private final Long id;
    private final String email;
    private final UserRole userRole;
    private final String nickname; // ✅ 레벨1 2번 닉네임 추가

    public AuthUser(Long id, String email, UserRole userRole, String nickname) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
        this.nickname = nickname;
    }

    // ✅ 레벨2 9번 UserRole을 Security 권한으로 변환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    // ✅ 레벨2 9번 아래는 JWT 방식이라 기본값 사용
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
