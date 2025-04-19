package SpaceServer.com.SpaceServer.stars.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stars")
@Getter
@NoArgsConstructor
public class StarsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stars_id")
    private Long starsId; // 별자리 ID

    @Column(name = "name", nullable = false)
    private String name; // 별자리 이름

    @Column(name = "image_url", nullable = false)
    private String imageUrl; // 별자리 이미지 URL

    @Column(name = "major_star_1", nullable = false)
    private String majorStar1; // 주요 별 1

    @Column(name = "major_star_2", nullable = false)
    private String majorStar2; // 주요 별 2

    @Column(name = "major_star_3", nullable = false)
    private String majorStar3; // 주요 별 3

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description; // 별자리 설명 (TEXT)


    @Builder
    public StarsEntity(String name, String imageUrl,
                               String majorStar1, String majorStar2, String majorStar3,
                               String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.majorStar1 = majorStar1;
        this.majorStar2 = majorStar2;
        this.majorStar3 = majorStar3;
        this.description = description;
    }
}
