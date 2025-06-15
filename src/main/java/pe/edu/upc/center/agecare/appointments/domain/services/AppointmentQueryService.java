package pe.edu.upc.center.agecare.appointments.domain.services;

import pe.edu.upc.center.agecare.appointments.domain.model.aggregates.Appointment;
import pe.edu.upc.center.agecare.appointments.domain.model.queries.GetAllAppointmentsQuery;
import pe.edu.upc.center.agecare.appointments.domain.model.queries.GetAppointmentByIdQuery;
import pe.edu.upc.center.agecare.appointments.domain.model.queries.GetAppointmentByResidentId;

import java.util.List;
import java.util.Optional;

public interface AppointmentQueryService {
    List<Appointment> handle(GetAllAppointmentsQuery query);
    Optional<Appointment> handle(GetAppointmentByIdQuery query);
    Optional<Appointment> handle(GetAppointmentByResidentId query);
}
