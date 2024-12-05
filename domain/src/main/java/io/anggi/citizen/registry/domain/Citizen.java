package io.anggi.citizen.registry.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "citizens")
public class Citizen {

    @Id
    @NotNull
    @Size(min = 8, max = 8, message = "Ο αριθμός ταυτότητας (ΑΤ) πρέπει να έχει ακριβώς 8 χαρακτήρες.")
    private String idNumber;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Gender {
        MALE,
        FEMALE,
        ENBY,
        OTHER
    }

    @NotNull
    @Past(message = "Ημερομηνία γέννησης πρέπει να είναι στο παρελθόν.")
    private LocalDate birthDate;

    @NotNull
    @Size(min = 9, max = 9, message = "ΑΦΜ πρέπει να έχει 9 ψηφία.")
    private String afm;

    private String address;

}
