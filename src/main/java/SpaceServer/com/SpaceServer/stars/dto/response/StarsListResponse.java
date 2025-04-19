package SpaceServer.com.SpaceServer.stars.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
/**
 * 별자리 리스트
 */
public class StarsListResponse {
    private Long starsId;
    private String name;
    private String imageUrl;
    private Boolean isLiked;
}
