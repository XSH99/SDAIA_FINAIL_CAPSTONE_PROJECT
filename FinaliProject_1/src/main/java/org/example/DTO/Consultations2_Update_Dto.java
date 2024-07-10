package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Consultations2_Update_Dto {


    private int consultation_id;
    private int consultation_rating;

    public Consultations2_Update_Dto() {
    }

    public Consultations2_Update_Dto(int consultation_id, int consultation_rating) {
        this.consultation_id = consultation_id;

        this.consultation_rating = consultation_rating;
    }

    public int getConsultation_id() {
        return consultation_id;
    }

    public void setConsultation_id(int consultation_id) {
        this.consultation_id = consultation_id;
    }

    public int getConsultation_rating() {
        return consultation_rating;
    }

    public void setConsultation_rating(int consultation_rating) {
        this.consultation_rating = consultation_rating;
    }
    // cheek line 104 and 103
    public Consultations2_Update_Dto(ResultSet rs)throws SQLException {
        consultation_id = rs.getInt("consultation_id");
        consultation_rating = rs.getInt("consultation_rating");


    }

    @Override
    public String toString() {
        return "Consultations{" +
                "consultation_id=" + consultation_id +
                ", consultation_rating=" + consultation_rating +
                '}';
    }













}
