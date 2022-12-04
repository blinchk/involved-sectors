package ee.laus.involvedsectors.controller;

import ee.laus.involvedsectors.dto.UserDto;
import ee.laus.involvedsectors.response.UserResponse;
import ee.laus.involvedsectors.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/current")
    public UserResponse current(@RequestParam(required = false) String sessionKey) {
        return userService.findBySessionKey(sessionKey);
    }
    @PostMapping
    public UserResponse save(@RequestBody UserDto dto, @RequestParam(required = false) String sessionKey) {
        return userService.save(dto, sessionKey);
    }
}
