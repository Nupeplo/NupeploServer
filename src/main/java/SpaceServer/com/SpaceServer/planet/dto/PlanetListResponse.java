package SpaceServer.com.SpaceServer.planet.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlanetListResponse {
    private String name; // 행성이름
}
