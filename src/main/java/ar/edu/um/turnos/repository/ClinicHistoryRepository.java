package ar.edu.um.turnos.repository;

import ar.edu.um.turnos.domain.ClinicHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClinicHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClinicHistoryRepository extends JpaRepository<ClinicHistory, Long> {

}
