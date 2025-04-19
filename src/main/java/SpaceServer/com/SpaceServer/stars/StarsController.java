package SpaceServer.com.SpaceServer.stars;

import SpaceServer.com.SpaceServer.stars.dto.response.StarsDetailResponse;
import SpaceServer.com.SpaceServer.stars.dto.response.StarsListResponse;
import SpaceServer.com.SpaceServer.stars.service.StarsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1/stars")
@RestController
@AllArgsConstructor
@Tag(name = "별자리", description = "별자리 입니다.")
public class StarsController {
    private final StarsService starsService;

    /**
     * 별자리 리스트 (키워드 검색 포함, 12개 페이징)
     */
    @Operation(summary = "별자리 전체 리스트", description = "12개 단위로 전체 별자리 목록 페이징")
    @GetMapping("/list")
    public ResponseEntity<Page<StarsListResponse>> getStarsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String userId
    ) {
        Page<StarsListResponse> result = starsService.getStarsList(page, userId);
        return ResponseEntity.ok(result);
    }
    /**
     * 검색 페이지
     */
    @Operation(summary = "별자리 이름 검색", description = "제목 키워드로 별자리를 검색하고 페이징")
    @GetMapping("/search")
    public ResponseEntity<Page<StarsListResponse>> searchStars(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String userId
    ) {
        Page<StarsListResponse> result = starsService.searchStarsByKeyword(keyword, page, userId);
        return ResponseEntity.ok(result);
    }

    /**
     * 별자리 상세 조회
     */
    @Operation(summary = "별자리 상세 조회", description = "별자리 ID로 상세 정보 + 찜 여부 반환")
    @GetMapping("/{starsId}")
    public ResponseEntity<StarsDetailResponse> getStarsDetail(
            @PathVariable Long id,
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(starsService.getStarsDetail(id, userId));
    }

    /**
     * 별자리 찜 토글
     */
    @Operation(summary = "즐겨찾기 토글", description = "해당 별자리 찜 또는 찜 해제를 토글")
    @PostMapping("/{id}/favorite")
    public ResponseEntity<Void> toggleFavorite(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        //starsService.toggleFavorite(userId, id);
        return ResponseEntity.ok().build();
    }

}
