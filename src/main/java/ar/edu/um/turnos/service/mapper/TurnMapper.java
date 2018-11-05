package ar.edu.um.turnos.service.mapper;

import ar.edu.um.turnos.domain.Turn;
import ar.edu.um.turnos.service.dto.TurnDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TurnMapper {

    public TurnDTO turnToTurnDTO (final Turn turn){

        TurnDTO turnDTO = new TurnDTO();
        turnDTO.setClinicName(turn.getClinic().getName());
        turnDTO.setDateAndHour(turn.getDateAndHour());
        turnDTO.setDoctorName(turn.getUser().getFirstName());
        turnDTO.setPatientName(turn.getPatient().getFullName());
        turnDTO.setPhone(turn.getPatient().getPhone());
        turnDTO.setId(turn.getId());

        return turnDTO;
    }

    /** devuelve una lista de turnos
     * @param turns */
    public List<TurnDTO> turnsToTurnsDTO (List<Turn> turns){

        List<TurnDTO> turnDTOS = new ArrayList<>();
        for (Turn turn: turns) {
            turnDTOS.add(this.turnToTurnDTO(turn));

        }

        return turnDTOS;
    }
}
