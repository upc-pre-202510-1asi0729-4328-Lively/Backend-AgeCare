package pe.edu.upc.center.agecare.appointments.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateAppointmentResource(
    Long residentId,
    Long doctorId,
    LocalDate date,
    LocalTime time,
    String status
)