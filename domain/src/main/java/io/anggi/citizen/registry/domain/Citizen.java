package io.anggi.citizen.registry.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "citizens")
public class Citizen {

    @Id
    @NotNull(message = "Ο Αριθμός Ταυτότητας είναι απαραίτητος.")
    @Size(min = 8, max = 8, message = "Ο αριθμός ταυτότητας (ΑΤ) πρέπει να έχει ακριβώς 8 χαρακτήρες.")
//    @Pattern(regexp = "^[A-Za-z]{2}\\d{6}$", message = "Ο αριθμός ταυτότητας πρέπει να ξεκινάει με 2 γράμματα και να ακολουθείται από 6 αριθμούς.")
    private String idNumber;

    @NotNull (message = "Το Ονομα δεν πρέπει να είναι κενό")
    private String firstName;

    @NotNull (message = "Το Επίθετο δεν πρέπει να είναι κενό")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Gender {
        MALE,
        FEMALE,
        ENBY,
        OTHER
    }

    @NotNull(message = "Η Ημερομηνία γέννησης είναι απαραίτητη.")
    @Past(message = "Η Ημερομηνία γέννησης πρέπει να είναι στο παρελθόν.")
    private LocalDate birthDate;

    @NotNull(message = "Το ΑΦΜ δεν πρέπει να είναι κενό.")
    @Size(min = 9, max = 9, message = "Ο ΑΦΜ πρέπει να έχει ακριβώς 9 ψηφία.")
    private String afm;

    @NotNull(message = "Η Διεύθυνση δεν πρέπει να είναι κενή.")
    private String address;

}
