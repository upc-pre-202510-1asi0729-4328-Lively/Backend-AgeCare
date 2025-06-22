package pe.edu.upc.center.agecare.users.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
