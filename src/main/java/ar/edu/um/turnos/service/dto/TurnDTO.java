package ar.edu.um.turnos.service.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.ZonedDateTime;


public class TurnDTO implements Serializable {

    private Long id;
    private String doctorName;
    private String patientName;
    private String clinicName;
    private String phone;
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private ZonedDateTime dateAndHour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ZonedDateTime getDateAndHour() {
        return dateAndHour;
    }

    public void setDateAndHour(ZonedDateTime dateAndHour) {
        this.dateAndHour = dateAndHour;
    }

    @Override
    public String toString() {
        return "TurnDTO{" +
            "id=" + id +
            ", doctorName='" + doctorName + '\'' +
            ", patientName='" + patientName + '\'' +
            ", clinicName='" + clinicName + '\'' +
            ", phone='" + phone + '\'' +
            ", dateAndHour=" + dateAndHour +
            '}';
    }
}
