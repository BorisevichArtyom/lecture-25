package by.itacademy.javaenterprise.borisevich.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Table(name = "Muscles", indexes = {
        @Index(name = "Muscle_name_UNIQUE", columnList = "muscle_name", unique = true)
})
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Muscle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "muscle_id", nullable = false)
    private Long id;

    @Column(name = "muscle_name", nullable = false, length = 45)
    private String muscleName;

    @Column(name = "description", nullable = false)
    @Size(min = 0, max = 200, message
            = "Description must be between 10 and 200 characters")
    private String description;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    @JoinTable(name = "Muscles_x_Exercises",
            joinColumns = @JoinColumn(name = "muscle_id"),
            inverseJoinColumns = @JoinColumn(name = "name_exercises_id"))
    private Set<Exercise> exerciseSet = new HashSet<>();


    @Override
    public String toString() {
        return "Muscle{" +
                "id=" + id +
                ", muscleName='" + muscleName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}