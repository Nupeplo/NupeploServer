package SpaceServer.com.SpaceServer.news.repository;

import SpaceServer.com.SpaceServer.news.entity.CardNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardNewsRepository extends JpaRepository<CardNewsEntity, Long> {
}
