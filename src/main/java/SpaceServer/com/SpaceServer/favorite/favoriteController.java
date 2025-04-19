package SpaceServer.com.SpaceServer.favorite;

import SpaceServer.com.SpaceServer.favorite.service.FavoriteService;
import SpaceServer.com.SpaceServer.stars.dto.response.StarsListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/my")
@RequiredArgsConstructor
public class favoriteController {
    private final FavoriteService favoriteService;

    @GetMapping("/favorites")
    public ResponseEntity<Page<StarsListResponse>> getMyFavorites(
            @RequestParam String userId,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<StarsListResponse> favorites = favoriteService.getFavoritesByUserId(userId, page);
        return ResponseEntity.ok(favorites);
    }
}
