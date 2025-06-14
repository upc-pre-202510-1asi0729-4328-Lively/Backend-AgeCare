package pe.edu.upc.center.agecare.appointments.domain.model.valueobjects;Add commentMore actions

import jakarta.persistence.Embeddable;

@Embeddable
public record Status(String value) {

    public Status() {
        this(null);
    }

    public Status {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status cannot be null or blank");
        }
    }Add commentMore actions
}