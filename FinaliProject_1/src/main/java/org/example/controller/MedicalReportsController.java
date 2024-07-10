
package org.example.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.DAO.MedicalReportsDao;
import org.example.DTO.MedicalReportsDto;
import org.example.DTO.MedicalReportsFilterDto;
import org.example.exceptions.DataNotFoundException;
import org.example.mappers.MedicalReportsMapper;
import org.example.models.MedicalReports;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/MEDICAL_REPORTS")
public class MedicalReportsController {

    @Inject
    MedicalReportsDao dao;
    @Context
    UriInfo uriInfo;
    @Context
    HttpHeaders headers;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response GET_ALL_MEDICAL_REPORTS(@BeanParam MedicalReportsFilterDto filter) {
        try {
            GenericEntity<ArrayList<MedicalReports>> entity = new GenericEntity<ArrayList<MedicalReports>>(dao.SELECT_ALL_MEDICAL_REPORTS(filter)) {};

            if (headers.getAcceptableMediaTypes().contains(MediaType.APPLICATION_XML)) {
                return Response.ok(entity).type(MediaType.APPLICATION_XML).build();
            } else if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
                return Response.ok(entity).type("text/csv").build();
            }

            return Response
                    .ok(entity, MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET,POST,DELETE,PUT")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GET
    @Path("/{medical_report_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response GET_ONE_MEDICAL_REPORTS(@PathParam("medical_report_id") int medical_report_id) throws SQLException, ClassNotFoundException {
        try {
            MedicalReports medicalReports = MedicalReportsDao.SELECT_ONE_MEDICAL_REPORTS(medical_report_id);
            if (medicalReports == null) {
                throw new DataNotFoundException("medical_report with ID " + medical_report_id + " not found");
            }

            MedicalReportsDto medicalReportDto = MedicalReportsMapper.INSTANCE.toMedDto(medicalReports);

            return Response.ok(medicalReportDto).build();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



//    @PUT
//    @Path("/{medical_report_id}")
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response UPDATE_MEDICAL_REPORTS(@PathParam("medical_report_id") int medical_report_id, MedicalReports medicalReports) {
//        try {
//            medicalReports.setMedical_report_id(medical_report_id);
//            dao.UPDATE_MEDICAL_REPORTS(medicalReports);
//            return Response.noContent().build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @POST
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response INSERT_MEDICAL_REPORTS(MedicalReports medicalReports) {
//        try {
//            dao.INSERT_MEDICAL_REPORTS(medicalReports);
//            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(medicalReports.getMedical_report_id())).build();
//            return Response.created(uri).build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


    @PUT
    @Path("/{medical_report_id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response UPDATE_MEDICAL_REPORTS(@PathParam("medical_report_id") int medical_report_id, MedicalReports medicalReports) {
        try {
            medicalReports.setMedical_report_id(medical_report_id);
            dao.UPDATE_MEDICAL_REPORTS(medicalReports);
            return Response.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response INSERT_MEDICAL_REPORTS(MedicalReports medicalReports) {
        try {
            dao.INSERT_MEDICAL_REPORTS(medicalReports);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(medicalReports.getMedical_report_id())).build();
            return Response.created(uri).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}