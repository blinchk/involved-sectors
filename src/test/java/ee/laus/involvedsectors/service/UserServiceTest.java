package ee.laus.involvedsectors.service;

import ee.laus.involvedsectors.dto.UserDto;
import ee.laus.involvedsectors.exception.NoContentException;
import ee.laus.involvedsectors.model.InvolvedSector;
import ee.laus.involvedsectors.model.Sector;
import ee.laus.involvedsectors.model.User;
import ee.laus.involvedsectors.repository.UserRepository;
import ee.laus.involvedsectors.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    InvolvedSectorService involvedSectorService;
    @Mock
    UserRepository userRepository;

    @Test
    void findBySessionKey() {
        String sessionKey = "session-key-any-333";
        User user = new User();
        user.setName("Nikolas");
        InvolvedSector involvedSector = new InvolvedSector();
        Sector sector = new Sector(4L, "Any", null, null);
        involvedSector.setId(4L);
        involvedSector.setSector(sector);
        user.setSectors(List.of(involvedSector));
        when(userRepository.findBySessionKey(sessionKey)).thenReturn(Optional.of(user));
        UserResponse actual = userService.findBySessionKey(sessionKey);
        assertEquals(user.getName(), actual.getName());
        assertEquals(sector.getId(), actual.getInvolvedSectorsIds().stream().findFirst().get());
        assertEquals(sessionKey, actual.getSessionKey());
    }

    @Test
    void findBySessionKey_throwsException_whenUserNotFound() {
        String sessionKey = "zzz";
        assertThrows(NoContentException.class, () -> {
            when(userRepository.findBySessionKey(sessionKey)).thenReturn(Optional.empty());
            userService.findBySessionKey(sessionKey);
        });
    }

    @Test
    void findBySessionKey_throwsException_whenSessionKeyIsNull() {
        assertThrows(NoContentException.class, () -> userService.findBySessionKey(null));
    }

    @Test
    void save_whenSessionKeyIsFilled() {
        String sessionKey = "session-key";
        List<Long> involvedSectorsIds = List.of(3L, 4L);
        String name = "Nikolas";
        User user = new User();
        user.setName("Nikolas");
        UserDto dto = new UserDto(name, involvedSectorsIds, true);
        when(userRepository.findBySessionKey(sessionKey)).thenReturn(Optional.of(user));
        when(involvedSectorService.saveAllByUser(user, involvedSectorsIds)).thenReturn(involvedSectorsIds);
        when(userRepository.save(user)).thenReturn(user);
        UserResponse actual = userService.save(dto, sessionKey);
        verify(involvedSectorService).deleteAllByUser(user);
        assertEquals(name, actual.getName());
        assertEquals(involvedSectorsIds, actual.getInvolvedSectorsIds());
        assertEquals(sessionKey, actual.getSessionKey());
    }

    @Test
    void save_whenSessionKeyIsNotFilled() {
        String sessionKey = null;
        List<Long> involvedSectorsIds = List.of(3L, 4L);
        String name = "Nikolas";
        User user = new User();
        user.setName("Nikolas");
        UserDto dto = new UserDto(name, involvedSectorsIds, true);
        when(involvedSectorService.saveAllByUser(user, involvedSectorsIds)).thenReturn(involvedSectorsIds);
        when(userRepository.save(any())).thenReturn(user);
        UserResponse actual = userService.save(dto, sessionKey);
        assertEquals(name, actual.getName());
        assertEquals(involvedSectorsIds, actual.getInvolvedSectorsIds());
    }
}