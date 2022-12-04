package ee.laus.involvedsectors.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private final String name;
    private final List<Long> involvedSectorsIds;
    private final String sessionKey;
}
