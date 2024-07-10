

package org.example.DAO;

import org.example.DTO.AvailableDto;
import org.example.DTO.SchedulesFilterDto;
import org.example.db.MCPConnection;
import org.example.models.Schedules;

import java.sql.*;
import java.util.ArrayList;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SchedulesDao {

    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\FinaliProject_1\\FinalProject4.db";
    private static final String SELECT_ALL_SCHEDULES = "select * from schedules ";
    private static final String SELECT_ONE_SCHEDULES = "select * from schedules where schedule_id = ?";
    private static final String INSERT_SCHEDULES = "insert into schedules (Doctor_id,Schedule_start_time,Schedule_end_time,Schedule_is_available) values  (?,?,?,?)";
    private static final String UPDATE_SCHEDULES = "update schedules set schedule_start_time = ?, schedule_end_time = ? , schedule_is_available =? where schedule_id =?";
    private static final String DELETE_SCHEDULES = "delete from schedules where schedule_id = ?";
    private static final String SELECT_SCHEDULES_BY_DOCTOR_ID = "select * from schedules where doctor_id = ?";
    private static final String SELECT_SCHEDULES_BY_AVALIBILITY = "select * from schedules where schedule_is_available = ?";
    private static final String SELECT_SCHEDULES_BY_AVALIBILITY_AND_DOCID = "select * from schedules where doctor_id AND schedule_is_available = ?";
    private static final String SEARCH_DOCTOR_IS_AVAILABLE = "SELECT DISTINCT doctor_id FROM schedules WHERE schedule_is_available = ?";

    public void INSERT_SCHEDULES(Schedules d) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(INSERT_SCHEDULES);
        st.setInt(1, d.getDoctor_id());
        st.setString(2, d.getSchedule_start_time().format(Schedules.formatter));
        st.setString(3, d.getSchedule_end_time().format(Schedules.formatter));
        st.setBoolean(4, d.isSchedule_is_available());
        st.executeUpdate();
    }

    public void UPDATE_SCHEDULES(Schedules d) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(UPDATE_SCHEDULES);
        st.setInt(4, d.getSchedule_id());
        st.setString(1, d.getSchedule_start_time().format(Schedules.formatter));
        st.setString(2, d.getSchedule_end_time().format(Schedules.formatter));
        st.setBoolean(3, d.isSchedule_is_available());
        st.executeUpdate();
    }

    public Schedules SELECT_ONE_SCHEDULES(int schedule_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_SCHEDULES);
        st.setInt(1, schedule_id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return new Schedules(rs);
        } else {
            return null;
        }
    }

    public ArrayList<Schedules> SELECT_ALL_SCHEDULES(SchedulesFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st;

        if (filter.getDoctor_id() != null) {
            st = conn.prepareStatement(SELECT_SCHEDULES_BY_DOCTOR_ID);
            st.setInt(1, filter.getDoctor_id());
        } else if (filter.getSchedule_is_available() != null) {
            st = conn.prepareStatement(SELECT_SCHEDULES_BY_AVALIBILITY);
            st.setBoolean(1, filter.getSchedule_is_available());
        } else if (filter.getSchedule_is_available() != null && filter.getDoctor_id() != null) {
            st = conn.prepareStatement(SELECT_SCHEDULES_BY_AVALIBILITY_AND_DOCID);
            st.setInt(1, filter.getDoctor_id());
            st.setBoolean(2, filter.getSchedule_is_available());
        } else {
            st = conn.prepareStatement(SELECT_ALL_SCHEDULES);
        }

        ResultSet rs = st.executeQuery();
        ArrayList<Schedules> schedules = new ArrayList<>();
        while (rs.next()) {
            schedules.add(new Schedules(rs));
        }

        return schedules;
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

