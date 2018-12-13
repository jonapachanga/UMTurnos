package ar.edu.um.turnos.repository;

import ar.edu.um.turnos.domain.Turn;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data  repository for the Turn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TurnRepository extends JpaRepository<Turn, Long>, QuerydslPredicateExecutor<Turn> {

    @Query("select turns from Turn turns where turns.user.login = ?#{principal.username}")
    List<Turn> findByUserIsCurrentUser();

    @Query("select turns from Turn turns where turns.dateAndHour between :dateAndHourStart and :dateAndHourEnd")
    List<Turn> findByDateAndHour(@Param("dateAndHourStart") ZonedDateTime dateAndHourStart, @Param("dateAndHourEnd") ZonedDateTime dateAndHourEnd);

    /*busqueda usando dsl*/
    List<Turn> findAllDateAndHour(Predicate boolexp, OrderSpecifier<?>... orders);
}
