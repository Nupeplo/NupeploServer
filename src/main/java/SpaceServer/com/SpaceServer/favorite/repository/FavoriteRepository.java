package SpaceServer.com.SpaceServer.favorite.repository;

import SpaceServer.com.SpaceServer.favorite.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity,Long> {
    /**
     * 해당 별자리에 해당 사용자가 찜을 한 경우
     */
    boolean existsByUserIdAndStarsIdAndIsLikedTrue(String userId, Long starsId);
    /**
     * 해당 사용자가 별자리 리스트에 찜을 누른 경우
     */
    @Query("SELECT f.starsId FROM FavoriteEntity f WHERE f.userId = :userId AND f.isLiked = true")
    List<Long> findConstellationIdByUserId(@Param("userId") String userId);


}
