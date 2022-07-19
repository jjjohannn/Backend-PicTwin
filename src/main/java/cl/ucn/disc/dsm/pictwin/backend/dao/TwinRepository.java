package cl.ucn.disc.dsm.pictwin.backend.dao;

import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository of twin
 *
 * @Author Johan Rojas-Godoy.
 */
@Repository
public interface TwinRepository  extends ListCrudRepository<Twin,Long> {
}
