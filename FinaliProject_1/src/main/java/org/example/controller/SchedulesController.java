package org.example.controller;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.DAO.SchedulesDao;
import org.example.DTO.SchedulesDto;
import org.example.DTO.SchedulesFilterDto;
import org.example.exceptions.DataNotFoundException;
import org.example.mappers.SchedulesMapper;
import org.example.models.Consultations;
import org.example.models.MedicalReports;
import org.example.models.Schedules;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Path("/SCHEDULES")
public class SchedulesController {



    @Inject SchedulesDao dao;
    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response GET_ALL_SSCHEDULES(@BeanParam SchedulesFilterDto filter) {
        try {
            GenericEntity<ArrayList<Schedules>> entity = new GenericEntity<ArrayList<Schedules>>(dao.SELECT_ALL_SCHEDULES(filter)) {};

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
    @Path("/{schedule_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response GET_ONE_SCHEDULES(@PathParam("schedule_id") int schedule_id) throws SQLException, ClassNotFoundException {
        try {
            Schedules schedules = dao.SELECT_ONE_SCHEDULES(schedule_id);
            if (schedules == null) {
                throw new DataNotFoundException("SCHEDULES with ID " + schedule_id + " not found");
            }

            SchedulesDto schedulesDto = SchedulesMapper.INSTANCE.toSchDto(schedules);

            return Response.ok(schedulesDto).build();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @PUT
    @Path("/{schedule_id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response UPDATE_SCHEDULES(@PathParam("schedule_id") int schedule_id, Schedules schedules) {
        try {
            schedules.setSchedule_id(schedule_id);
            dao.UPDATE_SCHEDULES(schedules);
            return Response.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response INSERT_SCHEDULES(Schedules schedules) {
        try {
            dao.INSERT_SCHEDULES(schedules);
            NewCookie cookie = new NewCookie.Builder("username").value("OOOOO").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(schedules.getSchedule_id())).build();
            return Response.created(uri).cookie(cookie).header("Created by", "Wael").build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


