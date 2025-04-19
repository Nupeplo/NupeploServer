package SpaceServer.com.SpaceServer.planet;


import SpaceServer.com.SpaceServer.planet.dto.PlanetDetailResponse;
import SpaceServer.com.SpaceServer.planet.dto.PlanetListResponse;
import SpaceServer.com.SpaceServer.planet.service.PlanetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/planet")
@RequiredArgsConstructor
@Tag(name = "행성", description = "행성 관련 정보 입니다,")
public class PlanetController {

    private final PlanetService planetService;

    @Operation(summary = "행성 이름 리스트", description = "메인 페이지용 - 행성이름만 리스트로 반환")
    @GetMapping
    public ResponseEntity<List<PlanetListResponse>> getPlanetNameList() {
        return ResponseEntity.ok(planetService.getPlanetNameList());
    }


    @Operation(summary = "행성 상세 조회", description = "행성 ID를 이용해 상세 정보를 조회합니다.")
    @GetMapping("/{planetId}")
    public ResponseEntity<PlanetDetailResponse> getPlanetDetail(@PathVariable Long planetId) {
        PlanetDetailResponse planetDetail = planetService.getPlanetDetail(planetId);
        return ResponseEntity.ok(planetDetail);
    }

}
