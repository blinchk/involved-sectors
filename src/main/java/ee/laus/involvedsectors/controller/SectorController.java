package ee.laus.involvedsectors.controller;

import ee.laus.involvedsectors.response.SectorSelectOptionResponse;
import ee.laus.involvedsectors.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sector")
@RequiredArgsConstructor
public class SectorController {
    private final SectorService sectorService;

    @GetMapping
    public List<SectorSelectOptionResponse> findAll() {
        return sectorService.findAllInHierarchy();
    }
}
