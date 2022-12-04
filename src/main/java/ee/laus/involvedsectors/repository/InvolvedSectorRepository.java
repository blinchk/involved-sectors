package ee.laus.involvedsectors.repository;

import ee.laus.involvedsectors.model.InvolvedSector;
import ee.laus.involvedsectors.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvolvedSectorRepository extends JpaRepository<InvolvedSector, Long> {
    void deleteAllByUser(User user);;
}
