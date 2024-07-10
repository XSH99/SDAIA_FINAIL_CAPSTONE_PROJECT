//package org.example.DTO;
//
//import org.example.models.Consultations;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//public class MedicalReportsDto {
//
//    private int medical_report_id;
//    private int patient_id;
//    private String medical_report_details;
//    private LocalDateTime medical_report_date;
//    //==========================
////    private ArrayList<Consultations> consultations;
////===============================================
//    private ArrayList<LinkDto> links = new ArrayList<>();
//
////    public MedicalReportsDto() {
////    }
////    public void setConsultations(ArrayList<Consultations> consultations){this.consultations=consultations;}
//
//
//    public MedicalReportsDto(int medical_report_id, int patient_id, String medical_report_details, LocalDateTime medical_report_date) {
//        this.medical_report_id = medical_report_id;
//        this.patient_id = patient_id;
//        this.medical_report_details = medical_report_details;
//        this.medical_report_date = medical_report_date;
//    }
//
//    public int getMedical_report_id() {
//        return medical_report_id;
//    }
//
//    public void setMedical_report_id(int medical_report_id) {
//        this.medical_report_id = medical_report_id;
//    }
//
//    public int getPatient_id() {
//        return patient_id;
//    }
//
//    public void setPatient_id(int patient_id) {
//        this.patient_id = patient_id;
//    }
//
//    public String getMedical_report_details() {
//        return medical_report_details;
//    }
//
//    public void setMedical_report_details(String medical_report_details) {
//        this.medical_report_details = medical_report_details;
//    }
//
//    public LocalDateTime getMedical_report_date() {
//        return medical_report_date;
//    }
//
//    public void setMedical_report_date(LocalDateTime medical_report_date) {
//        this.medical_report_date = medical_report_date;
//    }
//
//    public ArrayList<LinkDto> getLinks() {
//        return links;
//    }
//
//    public void setLinks(ArrayList<LinkDto> links) {
//        this.links = links;
//    }
//
//    public MedicalReportsDto(ResultSet rs) throws SQLException {
//        medical_report_id = rs.getInt("medical_report_id");
//        patient_id = rs.getInt("patient_id");
//        medical_report_details = rs.getString("medical_report_details");
//        medical_report_date = LocalDateTime.parse(rs.getString("medical_report_date"));
//    }
//
//
//    @Override
//    public String toString() {
//        return "MedicalReportsDto{" +
//                "medical_report_id=" + medical_report_id +
//                ", patient_id=" + patient_id +
//                ", medical_report_details='" + medical_report_details + '\'' +
//                ", medical_report_date=" + medical_report_date +
//                '}';
//    }
//
//    public void addLink(String url, String rel){
//        LinkDto link = new LinkDto();
//        link.setLink(url);
//        link.setRel(rel);
//        links.add(link);
//    }
//}


package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MedicalReportsDto {

    private int report_id;
    private int patient_id;
    private String report_details;
    private LocalDateTime report_date;


        private ArrayList<LinkDto> links = new ArrayList<>();

    public MedicalReportsDto() {
        // Default constructor
    }

    public MedicalReportsDto(int report_id, int patient_id, String report_details, LocalDateTime report_date) {
        this.report_id = report_id;
        this.patient_id = patient_id;
        this.report_details = report_details;
        this.report_date = report_date;
    }

    public MedicalReportsDto(java.sql.ResultSet resultSet) {
        // Constructor that takes a ResultSet
        try {
            this.report_id = resultSet.getInt("report_id");
            this.patient_id = resultSet.getInt("patient_id");
            this.report_details = resultSet.getString("report_details");
            this.report_date = resultSet.getTimestamp("report_date").toLocalDateTime();
        } catch (Exception e) {
            throw new RuntimeException("Error creating MedicalReportsDto from ResultSet", e);
        }
    }

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getReport_details() {
        return report_details;
    }

    public void setReport_details(String report_details) {
        this.report_details = report_details;
    }

    public LocalDateTime getReport_date() {
        return report_date;
    }

    public void setReport_date(LocalDateTime report_date) {
        this.report_date = report_date;
    }

    public ArrayList<LinkDto> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<LinkDto> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "MedicalReportsDto{" +
                "report_id=" + report_id +
                ", patient_id=" + patient_id +
                ", report_details='" + report_details + '\'' +
                ", report_date=" + report_date +
                '}';
    }

public void addLink(String url, String rel){
    LinkDto link = new LinkDto();
    link.setLink(url);
    link.setRel(rel);
    links.add(link);
}
}





