package ar.edu.um.turnos.repository;

import ar.edu.um.turnos.domain.Clinic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Clinic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

}
