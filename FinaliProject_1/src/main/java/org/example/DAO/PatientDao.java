
package org.example.DAO;

import org.example.DTO.PatientFilterDto;
import org.example.db.MCPConnection;
import org.example.models.Patient;

import java.sql.*;
import java.util.ArrayList;

public class PatientDao {
    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\FinaliProject_1\\FinalProject4.db";
    private static final String SELECT_ALL_PATIENTS = "select * from patients";
    private static final String SELECT_ONE_PATIENTS = "select * from patients where patient_id = ?";
    private static final String INSERT_PATIENTS = "insert into patients (patient_name, patient_email, patient_password, patient_phone, patient_birth_date) values (?, ?, ?, ?, ?)";
    private static final String UPDATE_PATIENTS = "update patients set patient_name = ?, patient_email = ?, patient_password = ?, patient_phone = ?, patient_birth_date = ? where patient_id = ?";
    private static final String DELETE_PATIENTS = "delete from patients where patient_id = ?";
    private static final String SELECT_ONE_PATIENTS_BY_PATIENTS_NAME = "select * from patients where lower(patient_name) = ?";
    private static final String SELECT_PASS_EMAIL = "select * from PATIENTS where patient_email = ? , patient_password=? ";
    private static final String LOGIN_PAT = "select * from PATIENTS where patient_email = ? AND patient_password = ?";

    public void INSERT_PATIENTS(Patient d) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(INSERT_PATIENTS);
        st.setString(1, d.getPatient_name());
        st.setString(2, d.getPatient_email());
        st.setString(3, d.getPatient_password());
        st.setString(4, d.getPatient_phone());
        st.setString(5, d.getPatient_birth_date());
        st.executeUpdate();
        st.close();
        conn.close();
    }

    public void UPDATE_PATIENTS(Patient d) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(UPDATE_PATIENTS);
        st.setString(1, d.getPatient_name());
        st.setString(2, d.getPatient_email());
        st.setString(3, d.getPatient_password());
        st.setString(4, d.getPatient_phone());
        st.setString(5, d.getPatient_birth_date());
        st.setInt(6, d.getPatient_id());
        st.executeUpdate();
        st.close();
        conn.close();
    }

    public Patient SELECT_ONE_PATIENTS(int patient_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_PATIENTS);
        st.setInt(1, patient_id);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            Patient patient = new Patient(rs);
            rs.close();
            st.close();
            conn.close();
            return patient;
        } else {
            rs.close();
            st.close();
            conn.close();
            return null;
        }
    }

    public ArrayList<Patient> SELECT_ALL_PATIENTS(PatientFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st;
        if (filter.getPatient_name() != null) {
            st = conn.prepareStatement(SELECT_ONE_PATIENTS_BY_PATIENTS_NAME);
            st.setString(1, filter.getPatient_name().toLowerCase());
        } else {
            st = conn.prepareStatement(SELECT_ALL_PATIENTS);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<Patient> patients = new ArrayList<>();
        while (rs.next()) {
            patients.add(new Patient(rs));
        }
        rs.close();
        st.close();
        conn.close();
        return patients;
    }



    public Patient PatientsLogin(String patEmail, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(LOGIN_PAT)) {
            st.setString(1, patEmail);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Patient(rs);
            } else {
                return null;
            }





        }

    }


}

