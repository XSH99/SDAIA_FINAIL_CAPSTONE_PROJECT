

package org.example.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.DAO.ConsultationsDao;
import org.example.DAO.DoctorDao;
import org.example.DAO.SchedulesDao;
import org.example.DTO.AvailableDto;
import org.example.DTO.DoctorDto;
import org.example.DTO.DoctorFilterDto;
import org.example.DTO.RateDto;
import org.example.exceptions.DataNotFoundException;
import org.example.mappers.DoctorMapper;
import org.example.models.Doctor;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/doctors")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
public class DoctorController {

    @Inject
    ConsultationsDao ConDao;

    @Inject
    SchedulesDao schedulesDao;

    @Inject
    DoctorDao dao;

    @Context
    UriInfo uriInfo;

    @Context
    HttpHeaders headers;

    @GET
    public Response GET_ALL_DOCTORS(@BeanParam DoctorFilterDto filter) {
        try {
            ArrayList<Doctor> doctorList = dao.SELECT_ALL_DOCTORS(filter);
            GenericEntity<ArrayList<Doctor>> entity = new GenericEntity<ArrayList<Doctor>>(doctorList) {};

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
    @Path("{doctor_id}")
    public Response GET_ONE_DOCTOR(@PathParam("doctor_id") int doctor_id) throws SQLException {
        try {
            Doctor doctor = dao.SELECT_ONE_DOCTOR(doctor_id);
            if (doctor == null) {
                throw new DataNotFoundException("Doctor with ID " + doctor_id + " not found");
            }

            DoctorDto dto = DoctorMapper.INSTANCE.toDocDto(doctor);
            addLinks(dto);

            return Response.ok(dto).build();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addLinks(DoctorDto dto) {
        URI selfUri = uriInfo.getAbsolutePath();
        URI docUri = uriInfo.getBaseUriBuilder().path(DoctorController.class).build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(docUri.toString(), "doctors");
    }

    @DELETE
    @Path("{doctor_id}")
    public void DELETE_DOCTORS(@PathParam("doctor_id") int doctor_id) {
        try {
            dao.DELETE_DOCTORS(doctor_id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Doctor with ID " + doctor_id, e);
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response INSERT_DOCTORS(Doctor doctor) {
        try {
            dao.INSERT_DOCTORS(doctor);
            NewCookie cookie = new NewCookie.Builder("username").value("OOOOO").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(doctor.getDoctor_id())).build();
            return Response.created(uri).cookie(cookie).header("Created by", "Wael").build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("{doctor_id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response UPDATE_DOCTORS(@PathParam("doctor_id") int doctor_id, Doctor doctor) {
        try {
            doctor.setDoctor_id(doctor_id);
            dao.UPDATE_DOCTORS(doctor);
            return Response.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/consultation_rating")
    public Response getDoctorByRate(@QueryParam("consultation_rating") int consultation_rating) {
        ArrayList<Doctor> doctors = new ArrayList<>();
        try {
            ArrayList<RateDto> rateDtos = ConDao.SEARCH_DOCTOR_RATE(consultation_rating);
            for (RateDto rateDto : rateDtos) {
                doctors.add(dao.SELECT_ONE_DOCTOR(rateDto.getDoctor_id()));
            }
            return Response.ok(doctors).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/schedule_is_available")
    public Response getDoctorByAvailable(@QueryParam("schedule_is_available") boolean schedule_is_available) {
        ArrayList<Doctor> doctors = new ArrayList<>();
        try {
            ArrayList<AvailableDto> availableDtos = schedulesDao.SEARCH_DOCTOR_IS_AVAILABLE(schedule_is_available);
            for (AvailableDto availableDto : availableDtos) {
                doctors.add(dao.SELECT_ONE_DOCTOR(availableDto.getDoctor_id()));
            }
            return Response.ok(doctors).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
