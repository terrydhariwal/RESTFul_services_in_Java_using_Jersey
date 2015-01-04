package com.terrydhariwal.View;


import com.terrydhariwal.model.Activity;
import com.terrydhariwal.model.ActivitySearch;
import com.terrydhariwal.repository.ActivityRepository;
import com.terrydhariwal.repository.ActivityRepositoryStub;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("search/activities")
public class ActivitySearchResource {

    private ActivityRepository activityRepository = new ActivityRepositoryStub();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    // The following line is new and needs to be explained:
    // @QueryParam( value = "description") List<String> description
    // This basically means, pull out the values of the query param called description
    // and add those values into a variable called descriptions which is a List of Strings
    public Response searchForActivities(@QueryParam( value = "description") List<String> descriptions,
                                        @QueryParam( value = "durationFrom") int durationFrom,
                                        @QueryParam( value = "durationTo") int durationTo) {

        System.out.println("descriptions = " + descriptions + " , durationFrom = " + durationFrom + " , durationTo = " + durationTo);

        List<Activity> activities = activityRepository.findByDescription(descriptions, durationFrom, durationTo);

        if(activities == null || activities.size() <= 0) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok().entity(new GenericEntity<List<Activity>>(activities) {
        }).build();
    }


    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response searchForActivities(ActivitySearch search) {

        System.out.println("descriptions = " + search.getDescriptions() + ", durationFrom = " + search.getDurationFrom() + " , durationTo = " + search.getDurationTo());

        List<Activity> activities =
                activityRepository.findByConstraints(search);

        if(activities == null || activities.size() <= 0) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok().entity(new GenericEntity<List<Activity>>(activities) {
        }).build();
    }
}
