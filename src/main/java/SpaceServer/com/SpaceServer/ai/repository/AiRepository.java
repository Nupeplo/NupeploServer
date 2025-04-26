package SpaceServer.com.SpaceServer.ai.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import SpaceServer.com.SpaceServer.ai.entity.AiEntity;

import java.util.List;

public interface AiRepository extends MongoRepository<AiEntity, String>{
    List<AiEntity> findByUserIdOrderByTimestampAsc(String userId);
}
