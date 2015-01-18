package com.farm.sensor.service.mapper;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class FarmExceptionMapper implements ExceptionMapper<RuntimeException> {

    private final ObjectMapper objectMapper;

    private static class ExceptionView {
        private String error;
        private String message;

        public ExceptionView(Exception exception) {
            this.error = exception.getClass().getName();
            this.message = exception.getMessage();
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

    }

    @Inject
    public FarmExceptionMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Response toResponse(RuntimeException exception) {
        try {
            return Response.status(Status.ERROR)
                    .entity(objectMapper.writeValueAsString(new ExceptionView(exception)))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Status.ERROR)
                    .entity("server error")
                    .build();
        }
    }
}
