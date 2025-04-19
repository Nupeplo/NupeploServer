package SpaceServer.com.SpaceServer.member.service.security;

import SpaceServer.com.SpaceServer.member.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class SecurityUser implements UserDetails {

    private String userId;

    private String nickname;

    private String profileImageUrl;

    public SecurityUser(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.nickname = userEntity.getNickname();
        this.profileImageUrl = userEntity.getProfileImageUrl();
    }



    // UserDetails 인터페이스 구현 (Spring Security용)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 현재는 권한(Role) 사용 X
    }

    @Override
    public String getPassword() {
        return "";
    }


    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (true = 만료되지 않음)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 (true = 잠금되지 않음)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부 (true = 만료되지 않음)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 (true = 활성화됨)
    }
}
