
package org.example.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.DAO.ConsultationsDao;
import org.example.DTO.Consultations2_Update_Dto;
import org.example.DTO.ConsultationsDto;
import org.example.DTO.ConsultationsFilterDto;
import org.example.exceptions.DataNotFoundException;
import org.example.mappers.ConsultationMapper;
import org.example.models.Consultations;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/consultations")
public class ConsultationsController {

    @Inject ConsultationsDao dao;
    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;


// dto
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,"text/csv"})
    public Response GET_ALL_CONSULTATIONS(@BeanParam ConsultationsFilterDto filter) {
        try {
            GenericEntity<ArrayList<Consultations>> entity = new GenericEntity<ArrayList<Consultations>>(dao.SELECT_ALL_CONSULTATIONS(filter)) {};

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



// dto
    @GET
    @Path("{consultation_id}")
    public Response GET_ONE_CONSULTATIONS(@PathParam("consultation_id") int consultation_id) throws SQLException {
        try {
            Consultations consultations = dao.SELECT_ONE_CONSULTATIONS(consultation_id);
            if (consultations == null) {
                throw new DataNotFoundException("CONSULTATIONS with ID " + consultation_id + " not found");
            }

            ConsultationsDto dto = ConsultationMapper.INSTANCE.toConDto(consultations);
            addLinks(dto);

            return Response.ok(dto).build();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }






    private void addLinks(ConsultationsDto dto) {
        URI selfUri = uriInfo.getAbsolutePath();
        URI docUri = uriInfo.getBaseUriBuilder().path(ConsultationsController.class).build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(docUri.toString(), "doctors");
    }


    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response INSERT_CONSULTATIONS(Consultations consultations) {
        try {
            dao.INSERT_CONSULTATIONS(consultations);
            NewCookie cookie = new NewCookie.Builder("username").value("OOOOO").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(consultations.getConsultation_id())).build();
            return Response.created(uri).cookie(cookie).header("Created by", "Wael").build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
// dto for the consultation
    @PUT
    @Path("{consultation_id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    // here i change
    public Response UPDATE_CONSULTATIONS(@PathParam("consultation_id") int consultation_id, Consultations2_Update_Dto consultations) {
        try {
            consultations.setConsultation_id(consultation_id);
            dao.UPDATE_CONSULTATIONS(consultations);
            return Response.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}




