
package org.example.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.DAO.PatientDao;
import org.example.DTO.PatientDto;
import org.example.DTO.PatientFilterDto;
import org.example.exceptions.DataNotFoundException;
import org.example.mappers.PatientMapper;
import org.example.models.Patient;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/PATIENTS")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
public class PatientController {
    @Inject PatientDao dao;
    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response GET_ALL_PATIENTS(@BeanParam PatientFilterDto filter) {
        try {
            GenericEntity<ArrayList<Patient>> entity = new GenericEntity<ArrayList<Patient>>(dao.SELECT_ALL_PATIENTS(filter)) {};

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
    @Path("/{patient_id}")
    public Response GET_ONE_PATIENTS(@PathParam("patient_id") int patient_id) throws SQLException {
        try {
            Patient patient = dao.SELECT_ONE_PATIENTS(patient_id);
            if (patient == null) {
                throw new DataNotFoundException("PATIENT with ID " + patient_id + " not found");
            }

            PatientDto dto = PatientMapper.INSTANCE.toPatDto(patient);
            addLinks(dto);

            return Response.ok(dto).build();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addLinks(PatientDto dto) {
        URI selfUri = uriInfo.getAbsolutePath();
        URI docUri = uriInfo.getBaseUriBuilder().path(PatientController.class).build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(docUri.toString(), "patients");
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response INSERT_PATIENTS(Patient patient) {
        try {
            dao.INSERT_PATIENTS(patient);
            NewCookie cookie = new NewCookie.Builder("username").value("OOOOO").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(patient.getPatient_id())).build();
            return Response.created(uri).cookie(cookie).header("Created by", "Wael").build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("/{patient_id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response UPDATE_PATIENTS(@PathParam("patient_id") int patient_id, Patient patient) {
        try {
            patient.setPatient_id(patient_id);
            dao.UPDATE_PATIENTS(patient);
            return Response.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


