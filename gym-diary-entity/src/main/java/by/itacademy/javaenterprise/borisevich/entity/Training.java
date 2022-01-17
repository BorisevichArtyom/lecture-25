package by.itacademy.javaenterprise.borisevich.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Table(name = "Trainings", indexes = {
        @Index(name = "user_name_idx", columnList = "user_id")
})
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_id")
    private Long id;

    @Column(name = "training_date")
    @Past
    private LocalDate trainingDate;

    @Column(name = "start")
    private LocalTime start;

    @Column(name = "end")
    private LocalTime end;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "training",
            cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<TrainingSet> trainingSet;

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", trainingDate=" + trainingDate +
                ", start=" + start +
                ", end=" + end +
                ", user=" + user +
                '}';
    }
}