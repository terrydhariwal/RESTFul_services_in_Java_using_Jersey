package com.terrydhariwal.View;

import com.terrydhariwal.model.Activity;
import com.terrydhariwal.model.User;
import com.terrydhariwal.repository.ActivityRepository;
import com.terrydhariwal.repository.ActivityRepositoryStub;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

// http://localhost:8080/{app-context}/{servlet-url-mapping}/activities
@Path("activities") // http://localhost:8080/rest-exercise/webapi/activities
public class ActivityResource {

    private ActivityRepository activityRepository = new ActivityRepositoryStub();

//    @GET
//    @Produces(MediaType.APPLICATION_XML)
//    public List<Activity> getAllActivities_XML(){
//        return activityRepository.findAllActivities();
//    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Activity> getAllActivities_JSON(){
//        return activityRepository.findAllActivities();
//    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    // The browser by default sets accept to application/xml,
    // therefore you need to change accept to application/json for the server to return json
    // I use postman plugin for chrome to achieve this
    public List<Activity> getAllActivities(){
        return activityRepository.findAllActivities();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{activityId}") // http://localhost:8080/rest-exercise/webapi/activities/1234
    public Activity getActivity(@PathParam("activityId") String activityId){
        return activityRepository.findActivity(activityId);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{activityId}/user") // http://localhost:8080/rest-exercise/webapi/activities/1234/user
    public User getActivityUser(@PathParam("activityId") String activityId){
        return activityRepository.findActivity(activityId).getUser(); //return nested object of the activity object
    }

}
