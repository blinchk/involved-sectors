package ee.laus.involvedsectors.controller;

import ee.laus.involvedsectors.dto.UserDto;
import ee.laus.involvedsectors.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    void current() {
        String sessionKey = UUID.randomUUID().toString();
        userController.current(sessionKey);
        verify(userService).findBySessionKey(sessionKey);
    }

    @Test
    void save() {
        String sessionKey = UUID.randomUUID().toString();
        UserDto dto = new UserDto("Nikolas", List.of(), true);
        userController.save(dto, sessionKey);
        verify(userService).save(dto, sessionKey);
    }
}