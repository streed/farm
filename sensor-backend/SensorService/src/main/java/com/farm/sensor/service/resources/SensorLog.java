package com.farm.sensor.service.resources;

import com.farm.sensor.data.exceptions.SensorServiceException;
import com.farm.sensor.data.managers.SensorManager;
import com.farm.sensor.data.models.SensorSlug;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;
import java.util.List;

@Path("/sensor")
public class SensorLog {

    private final SensorManager sensorManager;

    @Inject
    public SensorLog(final SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    @POST
    public void saveValues(final @Valid SensorSlug sensorSlug) throws SensorServiceException, IOException {
        sensorManager.publish(sensorSlug);
    }


    @GET
    @Path("/{ownerId}/{sensorId}")
    public SensorSlug getSensorValue(final @PathParam("ownerId") int ownerId, final @PathParam("sensorId") int sensorId) throws IOException {
        Preconditions.checkArgument(ownerId > 0, "OwnerId must be greater than 0");
        Preconditions.checkArgument(sensorId > 0, "OwnerId must be greater than 0");
        return sensorManager.getSensorSlug(ownerId, sensorId);
    }

    @GET
    @Path("/{ownerId}")
    public List<SensorSlug> getSensorSlugsForUser(final @PathParam("ownerId") int ownerId) throws IOException {
        Preconditions.checkArgument(ownerId > 0, "OwnerId must be greater than 0");
        return sensorManager.getSensorSlugsForOwner(ownerId);
    }
}
