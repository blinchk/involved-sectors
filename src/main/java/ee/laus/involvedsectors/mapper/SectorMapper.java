package ee.laus.involvedsectors.mapper;

import ee.laus.involvedsectors.model.Sector;
import ee.laus.involvedsectors.response.SectorSelectOptionResponse;
import org.springframework.stereotype.Component;

@Component
public class SectorMapper {
    public SectorSelectOptionResponse map(Sector sector) {
        return new SectorSelectOptionResponse(
                sector.getId(),
                sector.getTitle(),
                sector.getChildren().stream().map(this::map).toList()
        );
    }
}
