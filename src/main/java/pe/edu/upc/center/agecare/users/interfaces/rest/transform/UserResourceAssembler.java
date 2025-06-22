package pe.edu.upc.center.agecare.users.interfaces.rest.transform;

import pe.edu.upc.center.agecare.users.domain.model.aggregates.User;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.CreateUserResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UserResource;

public class UserResourceAssembler {

    public static UserResource toResource(User user) {
        return new UserResource(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
                // agrega otros campos si tienes más
        );
    }

    public static User toEntity(CreateUserResource resource) {
        User user = new User();
        user.setName(resource.name());
        user.setEmail(resource.email());
        user.setRole(resource.role());
        // agrega otros setters si tienes más
        return user;
    }
}
