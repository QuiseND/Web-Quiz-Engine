package engine.Controller;

import engine.Model.QuizUser;
import engine.Model.UserDTO;
import engine.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody QuizUser user) {
        UserDTO userDTO = userService.addUser(user);
        return ResponseEntity.ok(userDTO);
    }
}
