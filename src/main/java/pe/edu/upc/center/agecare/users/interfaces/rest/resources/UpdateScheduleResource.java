package pe.edu.upc.center.agecare.users.interfaces.rest.resources;

public record UpdateScheduleResource(
        String day,
        String startTime,       // Formato "HH:mm"
        String endTime,         // Formato "HH:mm"
        Long appointmentId
) {}
