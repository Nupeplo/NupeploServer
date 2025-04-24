package SpaceServer.com.SpaceServer.planet.service;

import SpaceServer.com.SpaceServer.planet.dto.PlanetDetailResponse;
import SpaceServer.com.SpaceServer.planet.dto.PlanetListResponse;
import SpaceServer.com.SpaceServer.planet.entity.PlanetEntity;
import SpaceServer.com.SpaceServer.planet.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
/**
 * 각각 행성 상세정보 불러오기
 */
public class PlanetService {
    private final PlanetRepository planetRepository;
    /**
     * 행성 전체 메인 페이지
     */
    public List<PlanetListResponse> getPlanetNameList() {
        List<PlanetEntity> entities = planetRepository.findAll(Sort.by(Sort.Direction.ASC, "planetId"));

        return entities.stream()
                .map(p -> PlanetListResponse.builder()
                        .name(p.getName())
                        .build())
                .toList();
    }

    /**
     * 행성 상세페이지 보여주기
     */
    /**
     * 행성 상세페이지 보여주기
     */
    public PlanetDetailResponse getPlanetDetail(Long planetId) {
        PlanetEntity planet = planetRepository.findById(planetId)
                .orElseThrow(() -> new RuntimeException("해당 행성이 존재하지 않습니다."));

        return PlanetDetailResponse.builder()
                .name(planet.getName())
                .size(planet.getSize())
                .mass(planet.getMass())
                .distanceFromSun(planet.getDistanceFromSun())
                .surfaceTemperature(planet.getSurfaceTemperature())
                .rotationInfo(planet.getRotationInfo())
                .revolution(planet.getRevolution())
                .satellites(Stream.of(
                                planet.getSatellite1(),
                                planet.getSatellite2(),
                                planet.getSatellite3(),
                                planet.getSatellite4(),
                                planet.getSatellite5()
                        )
                        .filter(Objects::nonNull)
                        .toList())
                .build();
    }
}
