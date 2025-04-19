package SpaceServer.com.SpaceServer.stars.service;

import SpaceServer.com.SpaceServer.favorite.repository.FavoriteRepository;
import SpaceServer.com.SpaceServer.stars.dto.response.StarsDetailResponse;
import SpaceServer.com.SpaceServer.stars.dto.response.StarsListResponse;
import SpaceServer.com.SpaceServer.stars.entity.StarsEntity;
import SpaceServer.com.SpaceServer.stars.repository.StarsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarsService {

    private final StarsRepository starsRepository;
    private final FavoriteRepository favoriteRepository;

    /**
     * 별자리 리스트 페이지 페이징 12개
     */
    public Page<StarsListResponse> getStarsList(int page, String userId) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("name").ascending());

        // 1. 전체 별자리 페이지 조회
        Page<StarsEntity> pageResult = starsRepository.findAll(pageable);

        // 2. 사용자가 찜한 별자리 ID 목록 조회
        List<Long> likedStars = favoriteRepository.findConstellationIdByUserId(userId);

        // 3. 전체 별자리 목록을 map 하며 찜 여부 판단
        return pageResult.map(stars -> StarsListResponse.builder()
                .starsId(stars.getStarsId())
                .name(stars.getName())
                .imageUrl(stars.getImageUrl())
                .isLiked(likedStars.contains(stars.getStarsId()))
                .build());
    }


    /**
     * 별자리 제목 검색
     */
    public Page<StarsListResponse> searchStarsByKeyword(String keyword, int page, String userId) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("name").ascending());

        // 제목 키워드로 별자리 검색
        Page<StarsEntity> pageResult = starsRepository.findByNameContainingIgnoreCase(keyword, pageable);

        // 사용자가 찜한 별자리 ID 목록 조회
        List<Long> likedStars = favoriteRepository.findConstellationIdByUserId(userId);

        // 결과 매핑 + 찜 여부 처리
        return pageResult.map(c -> StarsListResponse.builder()
                .starsId(c.getStarsId())
                .name(c.getName())
                .imageUrl(c.getImageUrl())
                .isLiked(likedStars.contains(c.getStarsId()))
                .build());
    }



    /**
     * 별자리 상세페에지
     */
    public StarsDetailResponse getStarsDetail(Long starsId, String userId) {
        StarsEntity constellation = starsRepository.findById(starsId)
                .orElseThrow(() -> new RuntimeException("해당 별자리를 찾을 수 없습니다."));

        boolean isLiked = favoriteRepository.existsByUserIdAndConstellationIdAndIsLikedTrue(userId, starsId);

        return StarsDetailResponse.builder()
                .starsId(constellation.getStarsId())
                .name(constellation.getName())
                .imageUrl(constellation.getImageUrl())
                .majorStar1(constellation.getMajorStar1())
                .majorStar2(constellation.getMajorStar2())
                .majorStar3(constellation.getMajorStar3())
                .description(constellation.getDescription())
                .isLiked(isLiked)
                .build();
    }


}
