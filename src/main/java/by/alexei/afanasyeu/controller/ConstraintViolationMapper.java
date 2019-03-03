package by.alexei.afanasyeu.controller;

import com.sun.deploy.util.StringUtils;

import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Singleton
@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<?> violation : violations) {
            messages.add(violation.getMessage());

        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new Object(){
            public List<String> errors = messages;
        }).build();
    }
}
