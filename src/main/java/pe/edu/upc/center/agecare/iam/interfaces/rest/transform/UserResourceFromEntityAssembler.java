package pe.edu.upc.center.agecare.iam.interfaces.rest.transform;

import pe.edu.upc.center.agecare.iam.domain.model.aggregates.User;
import pe.edu.upc.center.agecare.iam.domain.model.entities.Role;
import pe.edu.upc.center.agecare.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {

  public static UserResource toResourceFromEntity(User user) {
    var roles = user.getRoles().stream()
        .map(Role::getStringName)
        .toList();
    return new UserResource(user.getId(), user.getUsername(), roles);
  }
}
