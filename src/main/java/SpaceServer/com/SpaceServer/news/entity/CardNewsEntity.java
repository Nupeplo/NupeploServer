package SpaceServer.com.SpaceServer.news.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card_news")
@Getter
@NoArgsConstructor
public class CardNewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_news_id")
    private Long cardNewsId; // 카드 뉴스 ID

    @Column(name = "title", nullable = false)
    private String title; // 카드 뉴스 제목

    @Column(name = "image_url", nullable = false)
    private String imageUrl; // 카드 뉴스 이미지 URL

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; // 카드 뉴스 설명

    @Builder
    public CardNewsEntity(String title, String imageUrl, String content) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.content = content;
    }
}
