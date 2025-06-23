package pe.edu.upc.center.agecare.users.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.FullName;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.Schedule;
@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private String specialty;

    @Embedded
    private Schedule schedule;

    @Embedded
    private FullName fullName;

    @Embedded
    private ContactInfo contactInfo;
}
