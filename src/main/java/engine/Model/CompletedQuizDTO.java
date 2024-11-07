package engine.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompletedQuizDTO {
    private int id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime completedAt;

    public CompletedQuizDTO(CompletedQuiz quiz) {
        this.id = quiz.getQuizId();
        this.completedAt = quiz.getCompletedAt();
    }
}
