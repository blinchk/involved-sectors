package ee.laus.involvedsectors.repository;

import ee.laus.involvedsectors.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findAllByParentIsNull();
}
