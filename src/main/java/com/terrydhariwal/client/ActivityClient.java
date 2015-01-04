package com.terrydhariwal.client;

import com.terrydhariwal.model.Activity;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

public class ActivityClient {

    private Client client;

    public ActivityClient() {
        client = ClientBuilder.newClient();
    }

    public Activity get(String id) {
        WebTarget target = client.target("http://localhost:8080/rest-exercise/webapi/");

        //By default the request will return xml if you're asking for a String
        String response_xml = target.path("activities/" + id).request().get(String.class);
        System.out.println("response_default: " + response_xml);

        //If you want to see JSON then you need to ask for that
        String response_json = target.path("activities/" + id).request(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println("response_json: " + response_json);

        //If you return an object, the response is automatically converted to an object using binding
        Activity response_binary = target.path("activities/" + id).request().get(Activity.class);
        System.out.println("response_binary: " + response_binary);

        Response response = target.path("activities/" + id).request(MediaType.APPLICATION_JSON).get(Response.class);
        if(response.getStatus() != Status.OK.getStatusCode()) { //See http://jersey.576304.n2.nabble.com/Reponse-Status-response-getStatusInfo-td7581629.html
            throw new RuntimeException(response.getStatus() + " (" + response.getStatusInfo() + ")" + " there was an error on the server.");
        }
        return response.readEntity(Activity.class);
    }

    public List<Activity> get(){
        WebTarget target = client.target("http://localhost:8080/rest-exercise/webapi/");

        // Generics break our code
        // List<Activity> response = target.path("activities").request(MediaType.APPLICATION_JSON).get(List.class);
        // Any generic types that need to be returned will need to be wrapped inside of a GenericType,
        // GenericType is a helper function that Jersey provides
        // Need to wrap our type to return inside a new GenericType<List<Activity>>(){}
        List<Activity> response = target.path("activities").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Activity>>() {});

        return response;
    }

    public Activity create(Activity activity) {

        //http://localhost:8080/rest-exercise/webapi/activities/activity (when used with POST a new activity is created)
        WebTarget target = client.target("http://localhost:8080/rest-exercise/webapi/");

        Response response = target.path("activities/activity")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(activity, MediaType.APPLICATION_JSON));

        if(response.getStatus() != Status.OK.getStatusCode()) { //See http://jersey.576304.n2.nabble.com/Reponse-Status-response-getStatusInfo-td7581629.html
            throw new RuntimeException(response.getStatus() + " (" + response.getStatusInfo() + ")" + " there was an error on the server.");
        }

        return response.readEntity(Activity.class);
    }


    public Activity update(Activity activity) {

        WebTarget target = client.target("http://localhost:8080/rest-exercise/webapi/");

        Response response = target.path("activities/" + activity.getId())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(activity, MediaType.APPLICATION_JSON));

        return response.readEntity(Activity.class);
    }


    public void delete(String activityId) {
        WebTarget target = client.target("http://localhost:8080/rest-exercise/webapi/");

        Response response = target.path("activities/" + activityId)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if(response.getStatus() != Status.OK.getStatusCode()) { //See http://jersey.576304.n2.nabble.com/Reponse-Status-response-getStatusInfo-td7581629.html
            throw new RuntimeException(response.getStatus() + " (" + response.getStatusInfo() + ")" + " there was an error on the server.");
        }
    }
}
