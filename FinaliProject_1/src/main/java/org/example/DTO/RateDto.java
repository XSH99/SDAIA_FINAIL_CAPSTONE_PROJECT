package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RateDto {

    private Integer doctor_id;


    public RateDto(ResultSet rs)throws SQLException{

        doctor_id = rs.getInt("doctor_id");
    }

    public RateDto(int doctor_id){
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
        return "RateDto{" +
                "doctor_id=" + doctor_id +
                '}';
    }
}
