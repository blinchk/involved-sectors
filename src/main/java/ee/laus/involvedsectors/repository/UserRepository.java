package ee.laus.involvedsectors.repository;

import ee.laus.involvedsectors.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySessionKey(String sessionKey);
}
