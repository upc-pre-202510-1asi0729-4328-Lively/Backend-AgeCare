package pe.edu.upc.center.agecare.users.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.FullName;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "family_members")
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String relationship;
    private Long linkedResidentId;

    @Embedded
    private FullName fullName;

    @Embedded
    private ContactInfo contactInfo;
}
