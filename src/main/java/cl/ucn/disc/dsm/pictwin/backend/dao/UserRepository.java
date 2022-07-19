package cl.ucn.disc.dsm.pictwin.backend.dao;

import cl.ucn.disc.dsm.pictwin.backend.model.User;
import lombok.NonNull;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The repository of User
 * @Author Johan Rojas-Godoy.
 */
@Repository
public interface UserRepository extends ListCrudRepository<User,Long> {

    /**
     * Return the user with the email
     * @param email
     * @return
     */
    Optional<User> findOneByEmail(@NonNull String email);
}
