package ee.laus.involvedsectors.service;

import ee.laus.involvedsectors.mapper.SectorMapper;
import ee.laus.involvedsectors.model.Sector;
import ee.laus.involvedsectors.repository.SectorRepository;
import ee.laus.involvedsectors.response.SectorSelectOptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SectorService {
    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;
    public List<SectorSelectOptionResponse> findAllInHierarchy() {
        List<Sector> sectors = sectorRepository.findAllByParentIsNull();
        return sectors.stream().map(sectorMapper::map).toList();
    }
}
