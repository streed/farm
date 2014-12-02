package com.farm.service.resources;

import com.farm.service.managers.SensorManager;
import com.farm.service.models.SensorSlug;
import com.farm.service.views.FarmView;
import com.farm.service.views.FieldView;
import com.google.inject.Inject;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/readings")
public class SensorLog {

    private final SensorManager sensorManager;

    @Inject
    public SensorLog(final SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    @POST
    public void saveValues(final @Valid SensorSlug sensorSlug) {
        sensorManager.publish(sensorSlug);
    }

    @GET
    @Path("/{farmId}")
    public FarmView getFarmView(final @PathParam("farmId") int farmId) {
        return sensorManager.getFarmView(farmId);
    }

    @GET
    @Path("/{farmId}/{fieldId}")
    public FieldView getFieldView(final @PathParam("farmId") int farmId, final @PathParam("fieldId") int fieldId) {
        return sensorManager.getFieldView(farmId, fieldId);
    }

    @GET
    @Path("/{farmId}/{fieldId}/{sensorId}")
    public SensorSlug getSensorValue(final @PathParam("farmId") int farmId, final @PathParam("fieldId") int fieldId, final @PathParam("sensorId") int sensorId) {
        return sensorManager.getSensorSlug(farmId, fieldId, sensorId);
    }
}
