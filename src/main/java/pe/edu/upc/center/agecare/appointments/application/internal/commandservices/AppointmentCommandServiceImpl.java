package pe.edu.upc.center.agecare.appointments.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.agecare.appointments.domain.model.aggregates.Appointment;
import pe.edu.upc.center.agecare.appointments.domain.model.commands.CreateAppointmentCommand;
import pe.edu.upc.center.agecare.appointments.domain.model.commands.DeleteAppointmentCommand;
import pe.edu.upc.center.agecare.appointments.domain.model.commands.UpdateAppointmentCommand;
import pe.edu.upc.center.agecare.appointments.domain.services.AppointmentCommandService;
import pe.edu.upc.center.agecare.appointments.infrastructure.persistence.jpa.repositories.AppointmentRepository;

import java.util.Optional;

@Service
public class AppointmentCommandServiceImpl implements AppointmentCommandService {

  private final AppointmentRepository appointmentRepository;

  public AppointmentCommandServiceImpl(AppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  @Override
  public Long handle(CreateAppointmentCommand command) {
    if (this.appointmentRepository.existsByDateTimeAndDateTimeTime(command.dateTime().date(), command.dateTime().time())) {
      throw new IllegalArgumentException("Appointment with date " + command.dateTime().date() + " and time " + command.dateTime().time() + " already exists");
    }

    Appointment appointment = new Appointment(command);
    try {
      this.appointmentRepository.save(appointment);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error while saving appointment: " + e.getMessage());
    }
    return appointment.getId();
  }


  @Override
  public Optional<Appointment> handle(UpdateAppointmentCommand command) {
    var appointmentId = command.appointmentId();

    if (!this.appointmentRepository.existsById(appointmentId)) {
      throw new IllegalArgumentException("Appointment with id " + appointmentId + " does not exist");
    }

    if (this.appointmentRepository.existsByDateTimeAndDateTimeTime(command.dateTime().date(), command.dateTime().time())) {
      throw new IllegalArgumentException("Appointment with date " + command.dateTime().date() + " and time " + command.dateTime().time() + " already exists");
    }

    var appointmentToUpdate = this.appointmentRepository.findById(appointmentId).get();
    appointmentToUpdate.UpdateAppointmentCommand(
        command.residentId(), 
        command.doctorId(), 
        command.dateTime(), 
        command.status()
        );

    try {
      var updatedAppointment = this.appointmentRepository.save(appointmentToUpdate);
      return Optional.of(updatedAppointment);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error while updating appointment: " + e.getMessage());
    }
  }

  @Override
  public void handle(DeleteAppointmentCommand command) {
    if (!this.appointmentRepository.existsById(command.appointmentId())) {
      throw new IllegalArgumentException("Appointment with id " + command.appointmentId() + " does not exist");
    }

    try {
      this.appointmentRepository.deleteById(command.appointmentId());
    } catch (Exception e) {
      throw new IllegalArgumentException("Error while deleting appointment: " + e.getMessage());
    }
  }
}