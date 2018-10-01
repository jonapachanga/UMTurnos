package ar.edu.um.turnos.repository;

import ar.edu.um.turnos.domain.Turn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Turn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {

    @Query("select turns from Turn turns where turns.user.login = ?#{principal.username}")
    List<Turn> findByUserIsCurrentUser();

}
