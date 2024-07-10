package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SchedulesDto {

    private int schedule_id;
    private int doctor_id;
    private LocalDateTime schedule_start_time;
    private LocalDateTime schedule_end_time;
    private boolean schedule_is_available;

    private ArrayList<LinkDto> links = new ArrayList<>();

    public SchedulesDto() {
    }

    public SchedulesDto(int schedule_id, int doctor_id, LocalDateTime schedule_start_time, LocalDateTime schedule_end_time, boolean schedule_is_available) {
        this.schedule_id = schedule_id;
        this.doctor_id = doctor_id;
        this.schedule_start_time = schedule_start_time;
        this.schedule_end_time = schedule_end_time;
        this.schedule_is_available = schedule_is_available;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public LocalDateTime getSchedule_start_time() {
        return schedule_start_time;
    }

    public void setSchedule_start_time(LocalDateTime schedule_start_time) {
        this.schedule_start_time = schedule_start_time;
    }

    public LocalDateTime getSchedule_end_time() {
        return schedule_end_time;
    }

    public void setSchedule_end_time(LocalDateTime schedule_end_time) {
        this.schedule_end_time = schedule_end_time;
    }

    public boolean isSchedule_is_available() {
        return schedule_is_available;
    }

    public void setSchedule_is_available(boolean schedule_is_available) {
        this.schedule_is_available = schedule_is_available;
    }

    @Override
    public String toString() {
        return "SchedulesDto{" +
                "schedule_id=" + schedule_id +
                ", doctor_id=" + doctor_id +
                ", schedule_start_time=" + schedule_start_time +
                ", schedule_end_time=" + schedule_end_time +
                ", schedule_is_available=" + schedule_is_available +
                '}';
    }

    public SchedulesDto(ResultSet rs) throws SQLException {
        schedule_id = rs.getInt("schedule_id");
        doctor_id = rs.getInt("doctor_id");
        schedule_start_time = LocalDateTime.parse(rs.getString("schedule_start_time"));
        schedule_end_time = LocalDateTime.parse(rs.getString("schedule_end_time"));
        schedule_is_available = rs.getBoolean("schedule_is_available");
    }

    public void addLink(String url, String rel){
        LinkDto link = new LinkDto();
        link.setLink(url);
        link.setRel(rel);
        links.add(link);
    }

}
