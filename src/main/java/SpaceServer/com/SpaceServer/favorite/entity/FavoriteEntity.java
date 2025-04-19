package SpaceServer.com.SpaceServer.favorite.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "favorite")
@Getter
@NoArgsConstructor
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId; // 유저 ID

    @Column(name = "stars_id", nullable = false)
    private Long starsId; // 별자리 ID

    @Column(name = "is_liked", nullable = false)
    private Boolean isLiked; // 찜 여부 (true = 찜, false = 취소)

    @Builder
    public FavoriteEntity(String userId, Long starsId, Boolean isLiked) {
        this.userId = userId;
        this.starsId = starsId;
        this.isLiked = isLiked != null ? isLiked : true; // 기본값 처리
    }

}
