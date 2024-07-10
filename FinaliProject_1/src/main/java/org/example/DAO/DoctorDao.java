
package org.example.DAO;

import org.example.DTO.AvailableDto;
import org.example.DTO.DoctorFilterDto;
import org.example.db.MCPConnection;
import org.example.models.Doctor;

import java.sql.*;
import java.util.ArrayList;

public class DoctorDao {

    private static final String SELECT_ALL_DOCTORS = "SELECT * FROM doctors";
    private static final String SELECT_ONE_DOCTOR = "SELECT * FROM doctors WHERE doctor_id = ?";
    private static final String INSERT_DOCTORS = "INSERT INTO doctors (doctor_name, doctor_specialty, doctor_email, doctor_password, doctor_phone) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_DOCTORS = "UPDATE doctors SET doctor_name = ?, doctor_specialty = ?, doctor_email = ?, doctor_password = ?, doctor_phone = ? WHERE doctor_id = ?";
    private static final String DELETE_DOCTORS = "DELETE FROM doctors WHERE doctor_id = ?";

    private static final String SELECT_ONE_DOCTOR_BY_NAME = "SELECT * FROM doctors  where lower(doctor_name) = ?";
    private static final String SELECT_ONE_DOCTOR_BY_SPECIALTY = "SELECT * FROM doctors  where lower(doctor_specialty) = ?";

    private static final String SELECT_PASS_EMAIL = "select * from DOCTORS where doctor_email = ? , doctor_password=? ";
    private static final String LOGIN_DOC = "select * from DOCTORS where doctor_email = ? AND doctor_password = ?";
    //===========================================================================================================
    private static final String SEARCH_DOCTOR_IS_AVAILABLE = "SELECT DISTINCT doctor_id FROM schedules WHERE schedule_is_available = ?";


    public void INSERT_DOCTORS(Doctor doctor) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(INSERT_DOCTORS)) {
            st.setString(1, doctor.getDoctor_name());
            st.setString(2, doctor.getDoctor_specialty());
            st.setString(3, doctor.getDoctor_email());
            st.setString(4, doctor.getDoctor_password());
            st.setString(5, doctor.getDoctor_phone());
            st.executeUpdate();
        }
    }

    public void UPDATE_DOCTORS(Doctor doctor) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(UPDATE_DOCTORS)) {
            st.setString(1, doctor.getDoctor_name());
            st.setString(2, doctor.getDoctor_specialty());
            st.setString(3, doctor.getDoctor_email());
            st.setString(4, doctor.getDoctor_password());
            st.setString(5, doctor.getDoctor_phone());
            st.setInt(6, doctor.getDoctor_id());
            st.executeUpdate();
        }
    }

    public void DELETE_DOCTORS(int doctor_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(DELETE_DOCTORS)) {
            st.setInt(1, doctor_id);
            st.executeUpdate();
        }
    }

    public Doctor SELECT_ONE_DOCTOR(int doctor_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(SELECT_ONE_DOCTOR)) {
            st.setInt(1, doctor_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Doctor(rs);
            } else {
                return null;
            }
        }
    }

    public ArrayList<Doctor> SELECT_ALL_DOCTORS(DoctorFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = prepareStatementWithFilter(conn, filter)) {
            ResultSet rs = st.executeQuery();
            ArrayList<Doctor> doctors = new ArrayList<>();
            while (rs.next()) {
                doctors.add(new Doctor(rs));
            }
            return doctors;
        }
    }
 // dto
    private PreparedStatement prepareStatementWithFilter(Connection conn, DoctorFilterDto filter) throws SQLException {
        PreparedStatement st;
        if (filter.getDoctor_name() != null) {
            st = conn.prepareStatement(SELECT_ONE_DOCTOR_BY_NAME);
            st.setString(1, filter.getDoctor_name().toLowerCase());
        } else if (filter.getDoctor_specialty() != null) {
            st = conn.prepareStatement(SELECT_ONE_DOCTOR_BY_SPECIALTY);
            st.setString(1, filter.getDoctor_specialty().toLowerCase());
        } else {
            st = conn.prepareStatement(SELECT_ALL_DOCTORS);
        }
        return st;
    }





    public Doctor DoctorLogin(String docEmail,String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(LOGIN_DOC)) {
            st.setString(1, docEmail);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Doctor(rs);
            } else {
                return null;
            }
        }
    }


    public ArrayList<AvailableDto> SEARCH_DOCTOR_IS_AVAILABLE(boolean schedule_is_available) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(SEARCH_DOCTOR_IS_AVAILABLE);
        st.setBoolean(1, schedule_is_available);
        ResultSet rs = st.executeQuery();
        ArrayList<AvailableDto> availableDtos = new ArrayList<>();
        while (rs.next()) {
            availableDtos.add(new AvailableDto(rs));
        }
        return availableDtos;
    }


}
