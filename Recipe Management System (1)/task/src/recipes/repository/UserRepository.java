package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.model.auth.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
}
