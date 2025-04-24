package SpaceServer.com.SpaceServer.favorite;

import SpaceServer.com.SpaceServer.favorite.service.FavoriteService;
import SpaceServer.com.SpaceServer.security.jwt.JwtTokenProvider;
import SpaceServer.com.SpaceServer.stars.dto.response.StarsListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/my")
@RequiredArgsConstructor
public class favoriteController {
    private final FavoriteService favoriteService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/favorites")
    public ResponseEntity<Page<StarsListResponse>> getMyFavorites(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam(defaultValue = "0") int page
    ) {
        String pureToken = accessToken.replace("Bearer ", "");
        String userId = jwtTokenProvider.getUserIdFromToken(pureToken);
        Page<StarsListResponse> favorites = favoriteService.getFavoritesByUserId(userId, page);
        return ResponseEntity.ok(favorites);
    }
}
