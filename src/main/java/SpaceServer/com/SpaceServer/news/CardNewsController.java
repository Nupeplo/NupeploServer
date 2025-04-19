package SpaceServer.com.SpaceServer.news;

import SpaceServer.com.SpaceServer.news.entity.CardNewsEntity;
import SpaceServer.com.SpaceServer.news.service.CardNewsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class CardNewsController {
    private final CardNewsService cardNewsService;

    @Operation(summary = "카드 뉴스 전체 조회", description = "모든 카드 뉴스 리스트를 반환합니다.")
    @GetMapping
    public ResponseEntity<List<CardNewsEntity>> getCardNewsList() {
        return ResponseEntity.ok(cardNewsService.getAllCardNews());
    }

    @Operation(summary = "카드 뉴스 상세 조회", description = "카드 뉴스 ID로 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CardNewsEntity> getCardNewsDetail(@PathVariable Long id) {
        return ResponseEntity.ok(cardNewsService.getCardNewsDetail(id));
    }
}
