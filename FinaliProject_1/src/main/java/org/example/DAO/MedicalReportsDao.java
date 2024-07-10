

package org.example.DAO;

import org.example.DTO.MedicalReportsFilterDto;
import org.example.db.MCPConnection;
import org.example.models.MedicalReports;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MedicalReportsDao {

    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\FinaliProject_1\\FinalProject4.db";
    private static final String SELECT_ALL_MEDICAL_REPORTS = "select * from MEDICAL_REPORTS";
    private static final String SELECT_ONE_MEDICAL_REPORTS = "select * from MEDICAL_REPORTS where medical_report_id = ?";
    private static final String INSERT_MEDICAL_REPORTS = "insert into MEDICAL_REPORTS (Patient_id,Medical_report_details,Medical_report_date) values  (?,?,?)";
    private static final String UPDATE_MEDICAL_REPORTS = "update MEDICAL_REPORTS set patient_id = ? ,medical_report_details = ?, medical_report_date = ? where medical_report_id =?";
    private static final String DELETE_MEDICAL_REPORTS = "delete from MEDICAL_REPORTS where medical_report_id = ?";
    private static final String SELECT_MEDICAL_REPORTS_BY_PATIENT_ID = "select * from MEDICAL_REPORTS where patient_id = ?";
    private static final String SELECT_MEDICAL_REPORTS_BY_MEDICAL_REPORT_DETAILS = "select * from MEDICAL_REPORTS where lower(medical_report_details) = ?";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


//    public void INSERT_MEDICAL_REPORTS(MedicalReports d) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        try (Connection conn = MCPConnection.getConn();
//             PreparedStatement st = conn.prepareStatement(INSERT_MEDICAL_REPORTS)) {
////            st.setInt(1, d.getMedical_report_id());
//            st.setInt(1, d.getPatient_id());
//            st.setString(2, d.getMedical_report_details());
//            st.setString(3, d.getMedical_report_date().format(formatter));  // Assuming d.getMedical_report_date() returns LocalDateTime
//            st.executeUpdate();
//        }
//    }
//
//    public void UPDATE_MEDICAL_REPORTS(MedicalReports d) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        try (Connection conn = MCPConnection.getConn();
//             PreparedStatement st = conn.prepareStatement(UPDATE_MEDICAL_REPORTS)) {
//            st.setInt(1, d.getPatient_id());
//            st.setString(2, d.getMedical_report_details());
//            st.setString(3, d.getMedical_report_date().format(formatter));  // Assuming d.getMedical_report_date() returns LocalDateTime
//            st.setInt(4, d.getMedical_report_id());
//            st.executeUpdate();
//        }
//    }


    public void INSERT_MEDICAL_REPORTS(MedicalReports d) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(INSERT_MEDICAL_REPORTS)) {
//            st.setInt(1, d.getMedical_report_id());
            st.setInt(1, d.getPatient_id());
            st.setString(2, d.getMedical_report_details());
            st.setString(3, d.getMedical_report_date().format(formatter));  // Assuming d.getMedical_report_date() returns LocalDateTime
            st.executeUpdate();
        }
    }


    public void UPDATE_MEDICAL_REPORTS(MedicalReports d) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(UPDATE_MEDICAL_REPORTS)) {
            st.setInt(1, d.getPatient_id());
            st.setString(2, d.getMedical_report_details());
            st.setString(3, d.getMedical_report_date().format(formatter));  // Assuming d.getMedical_report_date() returns LocalDateTime
            st.setInt(4, d.getMedical_report_id());
            st.executeUpdate();
        }
    }


    public static MedicalReports SELECT_ONE_MEDICAL_REPORTS(int medical_report_id) throws SQLException, ClassNotFoundException {
    Class.forName("org.sqlite.JDBC");
    Connection conn = MCPConnection.getConn();
    PreparedStatement st = conn.prepareStatement(SELECT_ONE_MEDICAL_REPORTS);
        st.setInt(1, medical_report_id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return new MedicalReports(rs.getInt("medical_report_id"),
                    rs.getInt("patient_id"),
                    rs.getString("medical_report_details"),
                    LocalDateTime.parse(rs.getString("medical_report_date"), formatter));
        } else {
            return null;
        }
    }




//Connection conn = MCPConnection.getConn(); it is show a line in ylo
    public ArrayList<MedicalReports> SELECT_ALL_MEDICAL_REPORTS(MedicalReportsFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st;
        if (filter.getPatient_id() != null) {
            st = conn.prepareStatement(SELECT_MEDICAL_REPORTS_BY_PATIENT_ID);
            st.setInt(1, filter.getPatient_id());

        } else if (filter.getMedical_report_details() != null) {
            st = conn.prepareStatement(SELECT_MEDICAL_REPORTS_BY_MEDICAL_REPORT_DETAILS);
            st.setString(1, filter.getMedical_report_details().toLowerCase());

        } else {
            st = conn.prepareStatement(SELECT_ALL_MEDICAL_REPORTS);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<MedicalReports> medicalReports = new ArrayList<>();
        while (rs.next()) {
            medicalReports.add(new MedicalReports(rs.getInt("medical_report_id"),
                    rs.getInt("patient_id"),
                    rs.getString("medical_report_details"),
                    LocalDateTime.parse(rs.getString("medical_report_date"), formatter)));
        }
        return medicalReports;
    }
}

