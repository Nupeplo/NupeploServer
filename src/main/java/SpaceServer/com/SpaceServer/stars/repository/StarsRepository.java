package SpaceServer.com.SpaceServer.stars.repository;

import SpaceServer.com.SpaceServer.stars.entity.StarsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StarsRepository extends JpaRepository<StarsEntity, Long> {
    // 이름 검색 + 페이징
    @Query("SELECT s FROM StarsEntity s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY s.starsId DESC")
    Page<StarsEntity> findByNameContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    Page<StarsEntity> findByStarsIdIn(List<Long> ids, Pageable pageable);


}
