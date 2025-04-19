package SpaceServer.com.SpaceServer.member.repository;

import SpaceServer.com.SpaceServer.member.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,String> {

    // userId 기준으로 유저 찾기- > 카카오 고유 아이디
    Optional<UserEntity> findByUserId(String userId);


    // 가입한 사용자 인지.
    Boolean existsByUserId(String userId);
}
