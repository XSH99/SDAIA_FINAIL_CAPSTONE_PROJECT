package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AvailableDto {

    private Integer doctor_id;


    public AvailableDto(ResultSet rs)throws SQLException {

        doctor_id = rs.getInt("doctor_id");
    }

    public AvailableDto(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Integer getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    @Override
    public String toString() {
        return "AvailableDto{" +
                "doctor_id=" + doctor_id +
                '}';
    }
}
