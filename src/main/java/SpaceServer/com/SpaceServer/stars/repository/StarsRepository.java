package SpaceServer.com.SpaceServer.stars.repository;

import SpaceServer.com.SpaceServer.stars.entity.StarsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StarsRepository extends JpaRepository<StarsEntity, Long> {
    // 이름 검색 + 페이징
    Page<StarsEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<StarsEntity> findByStarsIdIn(List<Long> ids, Pageable pageable);
}
