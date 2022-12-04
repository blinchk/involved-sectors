package ee.laus.involvedsectors.util;

import ee.laus.involvedsectors.dto.UserDto;
import ee.laus.involvedsectors.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserUtilTest {
    @Test
    void validateDto_throwsException_whenNameIsNull() {
        UserDto dto = new UserDto(null, List.of(), true);
        assertThrows(BadRequestException.class, () -> UserUtil.validateDto(dto));
    }

    @Test
    void validateDto_throwsException_whenNameIsEmpty() {
        UserDto dto = new UserDto("", List.of(), true);
        assertThrows(BadRequestException.class, () -> UserUtil.validateDto(dto));
    }

    @Test
    void validateDto_throwsException_whenNameIsBlank() {
        UserDto dto = new UserDto(" ", List.of(), true);
        assertThrows(BadRequestException.class, () -> UserUtil.validateDto(dto));
    }

    @Test
    void validateDto_throwsException_whenInvolvedSectorsIdsIsEmpty() {
        UserDto dto = new UserDto("Nikolas", List.of(), true);
        assertThrows(BadRequestException.class, () -> UserUtil.validateDto(dto));
    }

    @Test
    void validateDto_throwsException_whenAgreeToTermsIsFalse() {
        UserDto dto = new UserDto("Nikolas", List.of(3L), false);
        assertThrows(BadRequestException.class, () -> UserUtil.validateDto(dto));
    }
}