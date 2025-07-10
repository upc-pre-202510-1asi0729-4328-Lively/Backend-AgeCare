package pe.edu.upc.center.agecare.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String username, String token) {
}
