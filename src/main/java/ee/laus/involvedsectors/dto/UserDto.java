package ee.laus.involvedsectors.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private final String name;
    private final List<Long> involvedSectorsIds;
    private final boolean agreeToTerms;
}
