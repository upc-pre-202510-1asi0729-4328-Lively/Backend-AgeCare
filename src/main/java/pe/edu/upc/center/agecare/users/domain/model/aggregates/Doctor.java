package pe.edu.upc.center.agecare.users.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.FullName;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.Schedule;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licenseNumber;
    private String specialty;

    @Embedded
    private Schedule schedule;

    @Embedded
    private FullName fullName;

    @Embedded
    private ContactInfo contactInfo;
}
