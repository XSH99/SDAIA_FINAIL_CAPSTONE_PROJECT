package org.example.DTO;

import jakarta.ws.rs.QueryParam;

public class MedicalReportsFilterDto {

    private @QueryParam("patient_id") Integer patient_id;
    private @QueryParam("medical_report_details") String medical_report_details;

    public MedicalReportsFilterDto() {
    }

    public MedicalReportsFilterDto(Integer patient_id, String medical_report_details) {
        this.patient_id = patient_id;
        this.medical_report_details = medical_report_details;
    }


    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public String getMedical_report_details() {
        return medical_report_details;
    }

    public void setMedical_report_details(String medical_report_details) {
        this.medical_report_details = medical_report_details;
    }

    @Override
    public String toString() {
        return "MedicalReportsFilterDto{" +
                "patient_id=" + patient_id +
                ", medical_report_details='" + medical_report_details + '\'' +
                '}';
    }


}
