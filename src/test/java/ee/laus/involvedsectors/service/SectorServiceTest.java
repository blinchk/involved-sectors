package ee.laus.involvedsectors.service;

import ee.laus.involvedsectors.mapper.SectorMapper;
import ee.laus.involvedsectors.model.Sector;
import ee.laus.involvedsectors.repository.SectorRepository;
import ee.laus.involvedsectors.response.SectorSelectOptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SectorServiceTest {
    @InjectMocks
    SectorService sectorService;
    @Mock
    SectorRepository sectorRepository;
    @Mock
    SectorMapper sectorMapper;

    @Test
    void findAllInHierarchy() {
        Sector sector = new Sector();
        when(sectorRepository.findAllByParentIsNull()).thenReturn(List.of(sector));
        sectorService.findAllInHierarchy();
        verify(sectorMapper).map(sector);
    }
}