package ee.laus.involvedsectors.mapper;

import ee.laus.involvedsectors.model.Sector;
import ee.laus.involvedsectors.response.SectorSelectOptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SectorMapperTest {
    @InjectMocks
    SectorMapper sectorMapper;

    @Test
    void map() {
        Sector sector = new Sector();
        sector.setId(4L);
        sector.setTitle("Other");
        sector.setChildren(List.of());
        SectorSelectOptionResponse actual = sectorMapper.map(sector);
        assertEquals(sector.getId(), actual.getId());
        assertEquals(sector.getTitle(), actual.getTitle());
        assertEquals(List.of(), actual.getChildren());
    }
}