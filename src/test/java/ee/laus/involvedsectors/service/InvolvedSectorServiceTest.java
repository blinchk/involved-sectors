package ee.laus.involvedsectors.service;

import ee.laus.involvedsectors.model.InvolvedSector;
import ee.laus.involvedsectors.model.Sector;
import ee.laus.involvedsectors.model.User;
import ee.laus.involvedsectors.repository.InvolvedSectorRepository;
import ee.laus.involvedsectors.repository.SectorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvolvedSectorServiceTest {
    @InjectMocks
    InvolvedSectorService involvedSectorService;
    @Mock
    InvolvedSectorRepository involvedSectorRepository;
    @Mock
    SectorRepository sectorRepository;

    @Test
    void saveAllByUser() {
        User user = new User();
        Sector sector = new Sector();
        List<Long> involvedSectorsIds = List.of(4L);
        sector.setId(4L);
        sector.setTitle("Other");
        InvolvedSector involvedSector = new InvolvedSector(user, sector);
        when(sectorRepository.existsById(any())).thenReturn(true);
        when(sectorRepository.findById(any())).thenReturn(Optional.of(sector));
        List<InvolvedSector> involvedSectors = List.of(involvedSector);
        when(involvedSectorRepository.saveAll(any())).thenReturn(involvedSectors);
        List<Long> actual = involvedSectorService.saveAllByUser(user, involvedSectorsIds);
        assertEquals(involvedSectorsIds, actual);
    }

    @Test
    void deleteAllByUser() {
        User user = new User();
        involvedSectorService.deleteAllByUser(user);
        verify(involvedSectorRepository).deleteAllByUser(user);
    }
}