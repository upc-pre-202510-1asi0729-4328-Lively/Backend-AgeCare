package pe.edu.upc.center.agecare.iam.domain.services;

import pe.edu.upc.center.agecare.iam.domain.model.aggregates.User;
import pe.edu.upc.center.agecare.iam.domain.model.queries.GetAllUsersQuery;
import pe.edu.upc.center.agecare.iam.domain.model.queries.GetUserByIdQuery;
import pe.edu.upc.center.agecare.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
  List<User> handle(GetAllUsersQuery query);
  Optional<User> handle(GetUserByIdQuery query);
  Optional<User> handle(GetUserByUsernameQuery query);
}
