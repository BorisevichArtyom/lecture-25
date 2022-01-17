package by.itacademy.javaenterprise.borisevich.entity;

import by.itacademy.javaenterprise.borisevich.util.Convertation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Table(name = "Diary_users", indexes = {
        @Index(name = "UNIQUE", columnList = "email", unique = true),
})
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "user_password", nullable = false)
    @NotNull
    private String userPassword;

    @Column(name = "first_name", nullable = false)
    @Size(min = 3, max = 100, message
            = "FIRSTNAME must be between 3 and 200 characters")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 100, message
            = "Lastname must be between 3 and 200 characters")
    private String lastName;

    @Column(name = "age", nullable = false)
    @Min(value = 6, message = "Age should not be less than 6")
    @Max(value = 110, message = "Age should not be greater than 110")
    private Integer age;

    @Column(name = "weight", nullable = false)
    @Min(value = 20, message = "Weight should not be less than 20")
    @Max(value = 200, message = "Weight should not be greater than 200")
    private Integer weight;

    @Column(name = "balance_amount", nullable = false)
    private Long balanceAmount;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Training> trainingSet;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id")
    private Role role;

    public String getBalanceAmountByn() {
        return Convertation.convertToBYN(balanceAmount);
    }

    public void setBalanceAmount(Long balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", balanceAmount=" + balanceAmount +
                ", roleTypeName=" + role +
                '}';
    }
}