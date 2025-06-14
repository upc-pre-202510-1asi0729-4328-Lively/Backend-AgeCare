package pe.edu.upc.center.agecare.appointments.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

//import pe.edu.upc.center.agecare.appointments.domain.model.commands.CreateAppointmentCommand;
import pe.edu.upc.center.agecare.appointments.domain.model.valueobjects.DateTime;
import pe.edu.upc.center.agecare.appointments.domain.model.valueobjects.DoctorId;
import pe.edu.upc.center.agecare.appointments.domain.model.valueobjects.NotificationId;
import pe.edu.upc.center.agecare.appointments.domain.model.valueobjects.Status;
import pe.edu.upc.center.agecare.appointments.domain.model.valueobjects.ResidentId;
import pe.edu.upc.center.agecare.shared.domain.aggregates.AuditableAbstractAggregateRoot;


@Entity
@Table(name = "appointments")
@Getter
@NoArgsConstructor

public class Appointment extends AuditableAbstractAggregateRoot<Appointment> {

    @Embedded
    private ResidentId residentId;

    @Embedded
    private DoctorId doctorId;

    @Embedded
    private DateTime dateTime;

    @Embedded
    private Status status;

    @Embedded
    private NotificationId notificationId;


    // Constructor completo
    public Appointment(ResidentId residentId, DoctorId doctorId, DateTime dateTime, Status status) {
        this.residentId = residentId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.status = status;
        this.notificationId = new NotificationId();
    }
}