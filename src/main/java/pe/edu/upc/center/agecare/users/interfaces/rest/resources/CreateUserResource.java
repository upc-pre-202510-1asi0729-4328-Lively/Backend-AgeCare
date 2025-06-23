package pe.edu.upc.center.agecare.users.interfaces.rest.resources;

public record CreateUserResource(
        String name,
        String email,
        String role
) {
}
