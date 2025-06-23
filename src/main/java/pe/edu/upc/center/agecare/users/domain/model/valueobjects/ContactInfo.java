package pe.edu.upc.center.agecare.users.domain.model.valueobjects;

import jakarta.persistence.Embedded;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ContactInfo {
    private String phone;

    @Embedded
    private Address address;
}
