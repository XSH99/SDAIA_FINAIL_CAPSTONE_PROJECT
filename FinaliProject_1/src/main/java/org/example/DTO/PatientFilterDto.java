package org.example.DTO;

import jakarta.ws.rs.QueryParam;

public class PatientFilterDto {

    private @QueryParam("patient_name") String patient_name;

    public PatientFilterDto() {
    }

    public PatientFilterDto(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }


    @Override
    public String toString() {
        return "PatientFilterDto{" +
                "patient_name='" + patient_name + '\'' +
                '}';
    }


}
