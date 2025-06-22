package pe.edu.upc.center.agecare.users.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Schedule {
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;

    @Embedded
    private AppointmentId appointmentId;
}
