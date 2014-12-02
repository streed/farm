package com.farm.service.resources;

import com.farm.service.models.SensorSlug;
import com.google.inject.Inject;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/sensor")
public class SensorLog {

    @Inject
    public SensorLog() {}

    @POST
    public void saveValues(final @Valid SensorSlug sensorValue) {
        //send to flume
    }

    @GET
    @Path("/{teamId}/{sensorId}")
    public SensorSlug getSensorValue(final @PathParam("teamId") int teamId, final @PathParam("sensorId") long sensorId) {
        return null;
    }
}
