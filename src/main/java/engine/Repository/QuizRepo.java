package engine.Repository;

import engine.Model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepo extends PagingAndSortingRepository<Quiz, Integer>, CrudRepository<Quiz, Integer> {
    Page<Quiz> findAllByAuthorEmail(String email, Pageable pageable);
}
