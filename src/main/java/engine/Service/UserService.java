package engine.Service;

import engine.Model.QuizUser;
import engine.Model.UserDTO;
import engine.Repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO addUser(QuizUser user) {
        user.setPassword(encryptPassword(user.getPassword()));
        userRepo.save(user);
        return new UserDTO(user);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
