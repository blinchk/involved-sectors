package ee.laus.involvedsectors.service;

import ee.laus.involvedsectors.dto.UserDto;
import ee.laus.involvedsectors.exception.NoContentException;
import ee.laus.involvedsectors.model.User;
import ee.laus.involvedsectors.repository.UserRepository;
import ee.laus.involvedsectors.response.UserResponse;
import ee.laus.involvedsectors.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final InvolvedSectorService involvedSectorService;
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserResponse findBySessionKey(String sessionKey) {
        if (sessionKey == null) {
            throw new NoContentException();
        }
        User user = userRepository.findBySessionKey(sessionKey).orElseThrow(NoContentException::new);
        List<Long> involvedSectorsIds = user.getSectors().stream()
                .map(involvedSector -> involvedSector.getSector().getId())
                .toList();
        return new UserResponse(user.getName(), involvedSectorsIds, sessionKey);
    }

    @Transactional
    public UserResponse save(UserDto dto, String sessionKey) {
        UserUtil.validateDto(dto);
        User user;
        if (sessionKey == null || userRepository.findBySessionKey(sessionKey).isEmpty()) {
            sessionKey = UUID.randomUUID().toString();
            user = new User(dto.getName(), sessionKey);
        } else {
            user = userRepository.findBySessionKey(sessionKey).get();
            logger.info("Found user with id={} and name={}", user.getId(), user.getName());
            user.setName(dto.getName());
            involvedSectorService.deleteAllByUser(user);
        }
        user = userRepository.save(user);
        logger.info("User with id={} and name={} successfully saved", user.getId(), user.getName());
        List<Long> involvedSectorsIds = involvedSectorService.saveAllByUser(user, dto.getInvolvedSectorsIds());
        logger.info("Sectors with id={} saved for user with id={}", involvedSectorsIds, user.getId());
        return new UserResponse(user.getName(), involvedSectorsIds, sessionKey);
    }
}
