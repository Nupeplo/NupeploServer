package SpaceServer.com.SpaceServer.planet.repository;

import SpaceServer.com.SpaceServer.planet.entity.PlanetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<PlanetEntity, Long> {
}
