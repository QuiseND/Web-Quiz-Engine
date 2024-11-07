package engine.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    @Size(min = 2)
    private List<String> options;
    @ElementCollection
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Integer> answer;
    @ManyToOne
    private QuizUser author;
}