package pe.edu.upc.center.agecare.iam.domain.services;

import pe.edu.upc.center.agecare.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
