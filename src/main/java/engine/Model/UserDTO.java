package engine.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String email;

    public UserDTO(QuizUser quizUser) {
        this.email = quizUser.getEmail();
    }
}
