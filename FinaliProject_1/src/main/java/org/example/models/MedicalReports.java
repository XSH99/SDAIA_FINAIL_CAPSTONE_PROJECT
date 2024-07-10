package org.example.models;

import org.example.DTO.LinkDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MedicalReports {

    private int medical_report_id;
    private int patient_id;
    private String medical_report_details;
    private LocalDateTime medical_report_date;
    private ArrayList<Consultations> consultations;


    public MedicalReports() {
    }

    public MedicalReports(int medical_report_id, int patient_id, String medical_report_details, LocalDateTime medical_report_date) {
        this.medical_report_id = medical_report_id;
        this.patient_id = patient_id;
        this.medical_report_details = medical_report_details;
        this.medical_report_date = medical_report_date;
    }



    public int getMedical_report_id() {
        return medical_report_id;
    }

    public void setMedical_report_id(int medical_report_id) {
        this.medical_report_id = medical_report_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getMedical_report_details() {
        return medical_report_details;
    }

    public void setMedical_report_details(String medical_report_details) {
        this.medical_report_details = medical_report_details;
    }

    public LocalDateTime getMedical_report_date() {
        return medical_report_date;
    }

    public void setMedical_report_date(LocalDateTime medical_report_date) {
        this.medical_report_date = medical_report_date;
    }

    // cheek line 63
     public MedicalReports(ResultSet rs)throws SQLException {
         medical_report_id = rs.getInt("medical_report_id");
         patient_id = rs.getInt("patient_id");
         medical_report_details = rs.getString("medical_report_details");
         medical_report_date = LocalDateTime.parse(rs.getString("medical_report_date"));
    }


    @Override
    public String toString() {
        return "MedicalReports{" +
                "medical_report_id=" + medical_report_id +
                ", patient_id=" + patient_id +
                ", medical_report_details='" + medical_report_details + '\'' +
                ", medical_report_date=" + medical_report_date +
                '}';
    }


}
