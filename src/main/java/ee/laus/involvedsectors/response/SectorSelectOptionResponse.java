package ee.laus.involvedsectors.response;

import lombok.Data;

import java.util.List;

@Data
public class SectorSelectOptionResponse {
    private final Long id;
    private final String title;
    private final List<SectorSelectOptionResponse> children;
}
