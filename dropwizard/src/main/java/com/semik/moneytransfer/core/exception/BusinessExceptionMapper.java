package com.semik.moneytransfer.core.exception;

import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {
    public Response toResponse(BusinessException exception) {
        Response.Status status = Response.Status.PRECONDITION_FAILED;
        return Response.status(status)
                .entity(new ErrorMessage(status.getStatusCode(), exception.getMessage()))
                .build();
    }
}