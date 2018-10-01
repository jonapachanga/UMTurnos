package ar.edu.um.turnos.repository;

import ar.edu.um.turnos.domain.TurnType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TurnType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TurnTypeRepository extends JpaRepository<TurnType, Long> {

}
