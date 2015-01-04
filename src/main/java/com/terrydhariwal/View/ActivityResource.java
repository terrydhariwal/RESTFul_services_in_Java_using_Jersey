package com.terrydhariwal.View;

import com.terrydhariwal.model.Activity;
import com.terrydhariwal.model.User;
import com.terrydhariwal.repository.ActivityRepository;
import com.terrydhariwal.repository.ActivityRepositoryStub;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
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

    /** The browser by default sets accept to application/xml,
     *  therefore you need to change accept to application/json for the server to return json
     *  I use postman plugin for chrome to achieve this
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    // http://localhost:8080/rest-exercise/webapi/activities
    public List<Activity> getAllActivities(){
        return activityRepository.findAllActivities();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{activityId}") // http://localhost:8080/rest-exercise/webapi/activities/1234
    public Response getActivity(@PathParam("activityId") String activityId){

        System.out.println("Getting activity ID: " + activityId);

        if(activityId == null || activityId.length() < 4) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Activity activity = activityRepository.findActivity(activityId);

        if(activity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //return Response.status(Response.Status.OK).entity(activity).build(); //this also works
        return Response.ok().entity(activity).build();

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{activityId}/user") // http://localhost:8080/rest-exercise/webapi/activities/1234/user
    public User getActivityUser(@PathParam("activityId") String activityId){
        return activityRepository.findActivity(activityId).getUser(); //return nested object of the activity object
    }

    //From html forms
    @POST
    @Path("activity") // http://localhost:8080/rest-exercise/webapi/activities/activity
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Activity createActivityParams(MultivaluedMap<String, String> formParams) {

//        System.out.println(formParams.getFirst("description"));
//        System.out.println(formParams.getFirst("duration"));
        Activity activity = new Activity();
        activity.setDescription(formParams.getFirst("description"));
        activity.setDuration(Integer.parseInt(formParams.getFirst("duration")));
        activityRepository.create(activity);
        return activity;
    }

    /** Binding to a JSON object saves you from having to parse types from strings (like from html forms)
     *  test by sending a JSON but using the XMLElement names as apposed to property names (i.e. "desc" instead of "description")
     *  I've used PostMan to test this:
     *  Headers:
     *      Content-Type = application/json //this maps to consumes
     *      Accept = application/json (or application/xml) //this maps to produces
     *  Body (raw):
     *      { "desc" : "swimming", "duration" : 55 }
    */
    @POST
    @Path("activity") // http://localhost:8080/rest-exercise/webapi/activities/activity
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Activity createActivity(Activity activity){

        System.out.println(activity.getDescription());
        System.out.println(activity.getDuration());
        activityRepository.create(activity); //we pretend that this works - in reality we should check the response form the db
        return activity;
    }

    @PUT
    @Path("{activityId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateActivity(Activity activity) {
        System.out.println(activity.getId());

        //mock database
        activity = activityRepository.update(activity);

        return Response.ok().entity(activity).build();
    }


    @DELETE
    @Path("{activityId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response delete(@PathParam("activityId") String activityId) {
        System.out.println(activityId);

        //mock delete
        activityRepository.delete(activityId);

        return Response.ok().build();
    }
}
