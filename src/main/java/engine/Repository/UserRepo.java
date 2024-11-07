package engine.Repository;

import engine.Model.QuizUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<QuizUser, Integer> {
    Optional<QuizUser> findByEmail(String username);
}
