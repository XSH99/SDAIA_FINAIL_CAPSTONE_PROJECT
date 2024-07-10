//package org.example.helpers;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.container.ContainerRequestContext;
//import jakarta.ws.rs.container.ContainerRequestFilter;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.ext.Provider;
//import org.example.DAO.DoctorDao;
//import org.example.DAO.PatientDao;
//import org.example.DTO.ErrorMessage;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Base64;
//import java.util.List;
//import java.util.StringTokenizer;
//
//
//@Provider
//public class AuthFilter implements ContainerRequestFilter {
//
//    @Inject
//    private DoctorDao doct = new DoctorDao();
//    private PatientDao pati = new PatientDao();
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        if(!requestContext.getUriInfo().getPath().contains("secures")) return;
//
//        List<String> authHeaders = requestContext.getHeaders().get("Authorization");
//        if (authHeaders != null && authHeaders.size() != 0) {
//            String authHeader = authHeaders.get(0);
//            authHeader = authHeader.replace("Basic ", "");
//            authHeader = new String(Base64.getDecoder().decode(authHeader));
//            StringTokenizer tokenizer = new StringTokenizer(authHeader, ":");
//            String username = tokenizer.nextToken();
//            String password = tokenizer.nextToken();
//
//            try {
//                if (doct.SELECT_email_pass_DOCTOR(username,password)!= null) {
//                    return;
//                }else {
//                    pati.SELECT_PASS_EMAIL(username, password);
//                    return;
//                }
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        ErrorMessage err = new ErrorMessage();
//        err.setErrorContent("Please login");
//        err.setErrorCode(Response.Status.UNAUTHORIZED.getStatusCode());
//        err.setDocumentationUrl("https://google.com");
//
//        Response res = Response.status(Response.Status.UNAUTHORIZED)
//                .entity(err)
//                .build();
//
//        requestContext.abortWith(res);
//    }
//}


package org.example.helpers;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.example.DAO.DoctorDao;
import org.example.DAO.PatientDao;
import org.example.DTO.ErrorMessage;
//import org.example.dao.DoctorDao;
//import org.example.dao.PatientDao;
//import org.example.dto.ErrorMessage;

import java.io.IOException;
import java.util.*;

@Provider
public class AuthFilter implements ContainerRequestFilter {

//    @Inject
//    DoctorDao docdao;
//    @Inject
//    PatientDao patdao;
    private DoctorDao docdao = new DoctorDao();
    private PatientDao patdao = new PatientDao();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(!requestContext.getUriInfo().getPath().contains("secures")) return;

        List<String> authHeaders = requestContext.getHeaders().get("Authorization");
        if (authHeaders != null && authHeaders.size() != 0) {
            String authHeader = authHeaders.get(0);
            authHeader = authHeader.replace("Basic ", "");
            authHeader = new String(Base64.getDecoder().decode(authHeader));
            StringTokenizer tokenizer = new StringTokenizer(authHeader, ":");
            String user_email = tokenizer.nextToken();
            String user_password = tokenizer.nextToken();

            try {
                if (docdao.DoctorLogin(user_email,user_password) != null){
                    return;
                } else if (patdao.PatientsLogin(user_email,user_password)!= null) {
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ErrorMessage err = new ErrorMessage();
        err.setErrorContent("Please login");
        err.setErrorCode(Response.Status.UNAUTHORIZED.getStatusCode());
        err.setDocumentationUrl("https://google.com");

        Response res = Response.status(Response.Status.UNAUTHORIZED)
                .entity(err)
                .build();

        requestContext.abortWith(res);
    }
}