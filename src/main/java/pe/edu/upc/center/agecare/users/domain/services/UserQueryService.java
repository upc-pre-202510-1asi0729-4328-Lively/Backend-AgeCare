package pe.edu.upc.center.agecare.users.domain.services;

import pe.edu.upc.center.agecare.users.domain.model.aggregates.User;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
}
