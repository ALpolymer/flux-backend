package gr.aueb.cf.flux.repository;

import gr.aueb.cf.flux.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> ,
        JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByUuid(String uuid);
}
