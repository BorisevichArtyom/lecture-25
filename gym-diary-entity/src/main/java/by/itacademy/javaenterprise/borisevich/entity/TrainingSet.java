package by.itacademy.javaenterprise.borisevich.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalTime;

@Table(name = "Sets", indexes = {
        @Index(name = "Training_id_fk_idx", columnList = "training_id"),
        @Index(name = "Muscle_id_fk_idx", columnList = "name_exercises_id")
})
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_set_id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @ManyToOne(optional = false)
    @JoinColumn(name = "name_exercises_id", nullable = false)
    private Exercise nameExercise;

    @Column(name = "approach_counter")
    @Min(value = 0, message = "TrainingSet Counter should not be less than 0")
    private Integer approachCounter;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "weight")
    @Min(value = 0, message = "Weight should not be less than 0")
    @Max(value = 1000, message = "Weight should not be greater than 1000")
    private Integer weight;

    @Column(name = "repeats")
    @Min(value = 0, message = "Repeats should not be less than 0")
    private Integer repeats;

    @Override
    public String toString() {
        return "Set{" +
                "id=" + id +
                ", trainingSetCounter=" + approachCounter +
                ", time=" + time +
                ", weight=" + weight +
                ", repeats=" + repeats +
                '}';
    }
}