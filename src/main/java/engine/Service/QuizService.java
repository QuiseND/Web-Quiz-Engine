package engine.Service;

import engine.Model.*;
import engine.Repository.CompletedQuizRepo;
import engine.Repository.QuizRepo;
import engine.Repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepo quizRepo;
    private final UserRepo userRepo;
    private final CompletedQuizRepo completedQuizRepo;

    public QuizService(QuizRepo quizRepo, UserRepo userRepo, CompletedQuizRepo completedQuizRepo) {
        this.quizRepo = quizRepo;
        this.userRepo = userRepo;
        this.completedQuizRepo = completedQuizRepo;
    }

    public QuizDTO addQuiz(Quiz quiz, String authorEmail) {
        QuizUser author = userRepo.findByEmail(authorEmail).get();
        quiz.setAuthor(author);
        quizRepo.save(quiz);
        return new QuizDTO(quiz);
    }

    public QuizDTO getQuizById(int id) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        if (quiz.isEmpty()) {
            throw new EntityNotFoundException("Quiz not found");
        }

        return new QuizDTO(quiz.get());
    }

    public Page<QuizDTO> getAllQuizzes(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        Page<Quiz> quizzesDTO = quizRepo.findAll(pageable);
        return quizzesDTO.map(QuizDTO::new);
    }

    public Page<CompletedQuizDTO> getAllCompletedQuizzes(int pageNumber, String authorEmail) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by( "completedAt").descending());
        Page<CompletedQuiz> quizzes = completedQuizRepo.findAllByUserName(authorEmail, pageable);
        return quizzes.map(CompletedQuizDTO::new);
    }

    public AnswerResponse checkAnswer(int id, List<Integer> answers, String authorEmail) {
        Optional<Quiz> quiz = quizRepo.findById(id);

        if (quiz.isEmpty()) {
            throw new EntityNotFoundException("Quiz not found");
        }

        List<Integer> quizAnswers = new ArrayList<>(quiz.get().getAnswer());
        quizAnswers.sort(Integer::compareTo);
        answers.sort(Integer::compareTo);

        AnswerResponse answerResponse = new AnswerResponse();

        if (quizAnswers.equals(answers)) {
            answerResponse.setSuccess(true);
            answerResponse.setFeedback("Congratulations, you're right!");
            CompletedQuiz completedQuiz = new CompletedQuiz();
            completedQuiz.setQuizId(id);
            completedQuiz.setUserName(authorEmail);
            completedQuiz.setCompletedAt(LocalDateTime.now());

            completedQuizRepo.save(completedQuiz);
        } else {
            answerResponse.setSuccess(false);
            answerResponse.setFeedback("Wrong answer! Please, try again.");
        }

        return answerResponse;
    }

    public void deleteQuiz(int id, String authorEmail) {
        Optional<Quiz> optQuiz = quizRepo.findById(id);
        if (optQuiz.isEmpty()) {
            throw new EntityNotFoundException("Quiz not found");
        }

        QuizUser author = userRepo.findByEmail(authorEmail).get();
        Quiz quiz = optQuiz.get();
        if (quiz.getAuthor().equals(author)) {
            quizRepo.delete(quiz);
        } else {
            throw new AccessDeniedException("You don't have permission to delete this quiz");
        }
    }
}
