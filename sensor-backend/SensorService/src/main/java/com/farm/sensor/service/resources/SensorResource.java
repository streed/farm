package com.farm.sensor.service.resources;

import com.farm.sensor.data.exceptions.SensorServiceException;
import com.farm.sensor.data.managers.SensorManager;
import com.farm.sensor.data.models.SensorSlug;
import com.google.common.base.Optional;
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
public class SensorResource {

    private final SensorManager sensorManager;

    @Inject
    public SensorResource(final SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    @POST
    public void saveValues(final @Valid SensorSlug sensorSlug) throws SensorServiceException, IOException {
        Preconditions.checkArgument(sensorSlug.getOwnerId() > 0, "OwnerId must be greater than 0");
        sensorManager.publish(sensorSlug);
    }


    @GET
    @Path("/{ownerId}/{timestamp}")
    public Optional<SensorSlug> getSensorValue(final @PathParam("ownerId") int ownerId, final @PathParam("timestamp") long timestamp) throws IOException {
        Preconditions.checkArgument(ownerId > 0, "OwnerId must be greater than 0");
        return sensorManager.getSensorSlug(ownerId, timestamp);
    }

    @GET
    @Path("/{ownerId}")
    public List<SensorSlug> getSensorSlugsForUser(final @PathParam("ownerId") int ownerId) throws IOException {
        Preconditions.checkArgument(ownerId > 0, "OwnerId must be greater than 0");
        return sensorManager.getSensorSlugsForOwner(ownerId);
    }
}
