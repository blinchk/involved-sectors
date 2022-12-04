package ee.laus.involvedsectors.service;

import ee.laus.involvedsectors.model.InvolvedSector;
import ee.laus.involvedsectors.model.Sector;
import ee.laus.involvedsectors.model.User;
import ee.laus.involvedsectors.repository.InvolvedSectorRepository;
import ee.laus.involvedsectors.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvolvedSectorService {
    private final InvolvedSectorRepository involvedSectorRepository;
    private final SectorRepository sectorRepository;

    public List<Long> saveAllByUser(User user, List<Long> involvedSectorsIds) {
        List<InvolvedSector> involvedSectors = involvedSectorsIds
                .stream()
                .filter(sectorRepository::existsById)
                .map(involvedSectorId -> {
                    Sector sector = sectorRepository.findById(involvedSectorId).get();
                    return new InvolvedSector(user, sector);
                })
                .toList();
        return involvedSectorRepository.saveAll(involvedSectors).stream()
                .map(involvedSector -> involvedSector.getSector().getId())
                .toList();
    }

    public void deleteAllByUser(User user) {
        involvedSectorRepository.deleteAllByUser(user);
    }
}
