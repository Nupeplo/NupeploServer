package SpaceServer.com.SpaceServer.planet.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class satelliteResponse {

    private String satellite1; // 위성 1
    private String satellite2; // 위성 2
    private String satellite3; // 위성 3
    private String satellite4; // 위성 4
    private String satellite5; // 위성 5
}
