package pe.edu.upc.center.agecare.users.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.User;
import pe.edu.upc.center.agecare.users.domain.services.UserCommandService;
import pe.edu.upc.center.agecare.users.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    public UserCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setRole(user.getEmail());
            // Agrega aquí otros campos a actualizar
            return userRepository.save(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
