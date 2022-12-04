package ee.laus.involvedsectors.controller;

import ee.laus.involvedsectors.service.SectorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SectorControllerTest {
    @InjectMocks
    SectorController sectorController;

    @Mock
    SectorService sectorService;

    @Test
    void findAll() {
        sectorController.findAll();
        verify(sectorService).findAllInHierarchy();
    }
}