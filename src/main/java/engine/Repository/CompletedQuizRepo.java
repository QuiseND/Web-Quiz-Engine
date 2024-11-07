package engine.Repository;

import engine.Model.CompletedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletedQuizRepo extends PagingAndSortingRepository<CompletedQuiz, Integer>, CrudRepository<CompletedQuiz, Integer> {
        Page<CompletedQuiz> findAllByUserName(String email, Pageable pageable);
}
