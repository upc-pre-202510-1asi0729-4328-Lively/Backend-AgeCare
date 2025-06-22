package pe.edu.upc.center.agecare.users.interfaces.rest.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.FullName;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.Schedule;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResource {
    private Long id;
    private String licenseNumber;
    private String specialty;
    private Schedule schedule;
    private FullName fullName;
    private ContactInfo contactInfo;
}
