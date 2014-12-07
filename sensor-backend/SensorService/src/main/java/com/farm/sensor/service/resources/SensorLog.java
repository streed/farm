package com.farm.sensor.service.resources;

import com.farm.sensor.service.exceptions.SensorServiceException;
import com.farm.sensor.service.managers.SensorManager;
import com.farm.sensor.data.models.SensorSlug;
import com.google.inject.Inject;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/sensor")
public class SensorLog {

    private final SensorManager sensorManager;

    @Inject
    public SensorLog(final SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    @POST
    public void saveValues(final @Valid SensorSlug sensorSlug) throws SensorServiceException {
        sensorManager.publish(sensorSlug);
    }


    @GET
    @Path("/{ownerId}/{sensorId}")
    public SensorSlug getSensorValue(final @PathParam("ownerId") int ownerId, final @PathParam("sensorId") int sensorId) {
        return sensorManager.getSensorSlug(ownerId, sensorId);
    }
}
