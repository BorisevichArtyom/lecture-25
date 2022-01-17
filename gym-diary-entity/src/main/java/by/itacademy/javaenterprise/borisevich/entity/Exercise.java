package by.itacademy.javaenterprise.borisevich.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Table(name = "Exercises", indexes = {
        @Index(name = "Exercise_name_UNIQUE", columnList = "name", unique = true)
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name_exercises_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(min = 10, max = 200, message
            = "Name must be between 10 and 200 characters")
    private String name;

    @Column(name = "description")
    @Size(min = 0, max = 200, message
            = "Description must be between 10 and 200 characters")
    private String description;

    @ManyToMany(mappedBy = "exerciseSet")
    @JsonIgnore
    private Set<Muscle> muscleSet = new HashSet<>();

    @OneToMany(mappedBy = "nameExercise")
    @JsonIgnore
    private Set<TrainingSet> trainingSet;

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
