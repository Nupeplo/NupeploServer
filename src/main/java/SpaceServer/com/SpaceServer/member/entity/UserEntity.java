package SpaceServer.com.SpaceServer.member.entity;

import SpaceServer.com.SpaceServer.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserEntity extends BaseTimeEntity {

    @Id
    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    private String profileImageUrl;

    // 생성자
    @Builder
    public UserEntity(String userId, String nickname, String profileImageUrl) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
