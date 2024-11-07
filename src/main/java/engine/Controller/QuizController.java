package engine.Controller;

import engine.Model.*;
import engine.Service.QuizService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller()
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody Quiz quiz, Authentication authentication) {
       QuizDTO quizDTO = quizService.addQuiz(quiz, authentication.getName());
       return ResponseEntity.ok(quizDTO);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<AnswerResponse> solveQuiz(@PathVariable("id") int quizID, @RequestBody Map<String, List<Integer>> request,
                                                    Authentication authentication) {
        AnswerResponse response = quizService.checkAnswer(quizID, request.get("answer"), authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<Page<QuizDTO>> getAllQuizzes(@RequestParam("page") int page) {
        Page<QuizDTO> quizzes = quizService.getAllQuizzes(page);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable("id") int id){
        QuizDTO quizDTO = quizService.getQuizById(id);
        return ResponseEntity.ok(quizDTO);
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<CompletedQuizDTO>> getCompletedQuizzes(@RequestParam("page") int page, Authentication authentication) {
        Page<CompletedQuizDTO> quizzes = quizService.getAllCompletedQuizzes(page, authentication.getName());
        return ResponseEntity.ok(quizzes);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("id") int id, Authentication authentication){
        quizService.deleteQuiz(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
