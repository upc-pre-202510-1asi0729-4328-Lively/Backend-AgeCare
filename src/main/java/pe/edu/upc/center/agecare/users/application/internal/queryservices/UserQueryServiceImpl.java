package pe.edu.upc.center.agecare.users.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.User;
import pe.edu.upc.center.agecare.users.domain.services.UserQueryService;
import pe.edu.upc.center.agecare.users.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
