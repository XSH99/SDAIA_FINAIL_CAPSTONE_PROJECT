package org.example.DAO;
import org.example.DTO.Consultations2_Update_Dto;
import org.example.DTO.Consultations3_Select_Dto;
import org.example.DTO.ConsultationsFilterDto;
import org.example.DTO.RateDto;
import org.example.db.MCPConnection;
import org.example.models.Consultations;

import java.sql.*;
import java.util.ArrayList;

public class ConsultationsDao {

    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\FinaliProject_1\\FinalProject4.db";
    private static final String SELECT_ALL_CONSULTATIONS = "select * from consultations";
    private static final String SELECT_ONE_CONSULTATIONS = "select * from consultations where consultation_id = ?";
    private static final String INSERT_CONSULTATIONS = "insert into consultations (Doctor_id,Patient_id,Consultation_request_time,Consultation_time,Consultation_status,Consultation_diagnosis,Consultation_rating) values  (?,?,?,?,?,?,?)";
//    private static final String UPDATE_CONSULTATIONS = "update consultations set doctor_id = ?, patient_id = ?, consultation_request_time = ?, consultation_time = ?, consultation_status = ?, consultation_diagnosis = ?, consultation_rating = ? where consultation_id = ?";
private static final String UPDATE_CONSULTATIONS = "update consultations set consultation_rating = ? where consultation_id = ?";

    private static final String DELETE_CONSULTATIONS = "delete from consultations where consultation_id = ?";
    //========================================================================================
    private static final String SELECT_CONSULTATIONS_BY_RATING = "select * from consultations where consultation_rating = ?";
    private static final String SELECT_CONSULTATIONS_BY_STATUS = "select * from consultations where consultation_status = ?";
    //=========================================================================================
    private static final String SELECT_CONSULTATIONS_BY_RATING2 = "SELECT * from doctor where doctor_id in (select DISTINCT (doctor_id)) FROM schedules WHERE schedules.schedule_is_available in (?);";
   //=============================================================================================
    private static final String SEARCH_DOCTOR_RATE = "SELECT DISTINCT doctor_id FROM consultations WHERE consultation_rating = ?";
//================================================================================================
    public void INSERT_CONSULTATIONS(Consultations d) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(INSERT_CONSULTATIONS);
//        st.setInt(1, d.getConsultation_id());
        st.setInt(1, d.getDoctor_id());
        st.setInt(2, d.getPatient_id());
        st.setString(3, d.getConsultation_request_time().toString());
        st.setString(4, d.getConsultation_time().toString());
        st.setString(5, d.getConsultation_status());
        st.setString(6, d.getConsultation_diagnosis());
        st.setInt(7, d.getConsultation_rating());
        st.executeUpdate();
        st.close();
        conn.close();
    }


//    public void UPDATE_CONSULTATIONS(Consultations d) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = MCPConnection.getConn();
//        PreparedStatement st = conn.prepareStatement(UPDATE_CONSULTATIONS);
//        st.setInt(1, d.getDoctor_id());
//        st.setInt(2, d.getPatient_id());
//        st.setString(3, d.getConsultation_request_time().toString());
//        st.setString(4, d.getConsultation_time().toString());
//        st.setString(5, d.getConsultation_status());
//        st.setString(6, d.getConsultation_diagnosis());
//        st.setInt(7, d.getConsultation_rating());
//        st.setInt(8, d.getConsultation_id());
//        st.executeUpdate();
//        st.close();
//        conn.close();
//    }


    // here i change
    public void UPDATE_CONSULTATIONS(Consultations2_Update_Dto d) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(UPDATE_CONSULTATIONS);
        st.setInt(1, d.getConsultation_rating());
        st.setInt(2, d.getConsultation_id());
        st.executeUpdate();
        st.close();
        conn.close();
    }


    public Consultations SELECT_ONE_CONSULTATIONS(int consultation_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_CONSULTATIONS);
        st.setInt(1, consultation_id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return new Consultations(rs);
        } else {
            return null;
        }
    }





// dto
    public ArrayList<Consultations> SELECT_ALL_CONSULTATIONS(ConsultationsFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st;
        if (filter.getConsultation_rating() != null) {
            st = conn.prepareStatement(SELECT_CONSULTATIONS_BY_RATING2);
            st.setInt(1, filter.getConsultation_rating());


        } else if (filter.getConsultation_status() != null) {
            st = conn.prepareStatement(SELECT_CONSULTATIONS_BY_STATUS);
            st.setString(1, filter.getConsultation_status());


        } else {
            st = conn.prepareStatement(SELECT_ALL_CONSULTATIONS);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<Consultations> consultations = new ArrayList<>();
        while (rs.next()) {
            consultations.add(new Consultations(rs));
        }

        return consultations;
    }


    public ArrayList<RateDto> SEARCH_DOCTOR_RATE(int consultation_rating) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(SEARCH_DOCTOR_RATE);
        st.setInt(1,consultation_rating);
        ResultSet rs = st.executeQuery();
        ArrayList<RateDto> rateDtos = new ArrayList<>();
        while (rs.next()) {
            rateDtos.add(new RateDto(rs));
        }
        return rateDtos;
    }
}
