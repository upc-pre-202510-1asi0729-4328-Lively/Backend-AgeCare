package pe.edu.upc.center.agecare.appointments.domain.model.valueobjects;

import jakarta.persistence.Embeddable;Add commentMore actions

@Embeddable
public record ResidentId(Long residentId) {

    public ResidentId() {
        this(null);
    }

    public ResidentId {
        if (residentId == null || residentId <= 0) {
            throw new IllegalArgumentException("ResidentId must be positive and not null");
        }
    }
}