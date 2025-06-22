package pe.edu.upc.center.agecare.users.domain.services;

import pe.edu.upc.center.agecare.users.domain.model.aggregates.User;

public interface UserCommandService {
    User createUser(User user);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
}
