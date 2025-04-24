package SpaceServer.com.SpaceServer.stars.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
/**
 * 별자리 상세페이지
 */
public class StarsDetailResponse {
    private Long starsId;
    private String name;
    private String imageUrl;
    private String description;
    private Boolean isLiked;
}
