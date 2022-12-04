package ee.laus.involvedsectors.util;

import ee.laus.involvedsectors.dto.UserDto;
import ee.laus.involvedsectors.exception.BadRequestException;

import java.util.Objects;

public class UserUtil {
    public static void validateDto(UserDto dto) {
        if (dto.getName() == null || dto.getName().isEmpty() || dto.getName().isBlank()) {
            throw new BadRequestException("Name must be filled");
        } else if (dto.getInvolvedSectorsIds().isEmpty()) {
            throw new BadRequestException("At least one involved sector must be selected");
        } else if (!dto.isAgreeToTerms()) {
            throw new BadRequestException("Terms must be agreed");
        }
    }
}
