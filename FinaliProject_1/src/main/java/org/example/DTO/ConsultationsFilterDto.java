package org.example.DTO;

import jakarta.ws.rs.QueryParam;

public class ConsultationsFilterDto {

    private @QueryParam("consultation_rating") Integer consultation_rating;
    private @QueryParam("consultation_status") String consultation_status;

    public ConsultationsFilterDto() {
    }


    public ConsultationsFilterDto(Integer consultation_rating, String consultation_status) {
        this.consultation_rating = consultation_rating;
        this.consultation_status = consultation_status;
    }

    public Integer getConsultation_rating() {
        return consultation_rating;
    }

    public void setConsultation_rating(Integer consultation_rating) {
        this.consultation_rating = consultation_rating;
    }

    public String getConsultation_status() {
        return consultation_status;
    }

    public void setConsultation_status(String consultation_status) {
        this.consultation_status = consultation_status;
    }


    @Override
    public String toString() {
        return "ConsultationsFilterDto{" +
                "consultation_rating=" + consultation_rating +
                ", consultation_status='" + consultation_status + '\'' +
                '}';
    }
}

