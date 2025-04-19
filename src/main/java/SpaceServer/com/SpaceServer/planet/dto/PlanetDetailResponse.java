package SpaceServer.com.SpaceServer.planet.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
/**
 * 각각 행성 정보를 불러오는것
 */
public class PlanetDetailResponse {
    private String name; // 행성이름
    private Double size; // 행성 사이즈
    private Double mass;
    private Double distanceFromSun;
    private Double surfaceTemperature;
    private String rotationInfo;
    private String revolution;
    private List<satelliteResponse> satellites; // 위성은 리스트
}
