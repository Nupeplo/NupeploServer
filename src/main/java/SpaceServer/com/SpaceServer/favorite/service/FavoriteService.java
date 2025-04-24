package SpaceServer.com.SpaceServer.favorite.service;

import SpaceServer.com.SpaceServer.favorite.repository.FavoriteRepository;
import SpaceServer.com.SpaceServer.stars.dto.response.StarsListResponse;
import SpaceServer.com.SpaceServer.stars.entity.StarsEntity;
import SpaceServer.com.SpaceServer.stars.repository.StarsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final StarsRepository starsRepository;

    /**
     * 해당 별자리 리스트 페이지
     */
    public Page<StarsListResponse> getFavoritesByUserId(String userId, int page) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("name").ascending());

        // 찜한 별자리 ID들
        List<Long> likedIds = favoriteRepository.findConstellationIdByUserId(userId);

        // 해당 ID 리스트에 포함된 별자리만 페이징 처리
        Page<StarsEntity> starsPage = starsRepository.findByStarsIdIn(likedIds, pageable);

        return starsPage.map(star -> StarsListResponse.builder()
                .starsId(star.getStarsId())
                .name(star.getName())
                .imageUrl(star.getImageUrl())
                .isLiked(true) // 무조건 true
                .build());
    }
}
