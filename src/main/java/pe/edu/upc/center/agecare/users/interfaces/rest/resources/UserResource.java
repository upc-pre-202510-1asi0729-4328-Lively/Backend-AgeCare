package pe.edu.upc.center.agecare.users.interfaces.rest.resources;

public record UserResource(
        Long id,
        String name,
        String email,
        String role
) {
}
