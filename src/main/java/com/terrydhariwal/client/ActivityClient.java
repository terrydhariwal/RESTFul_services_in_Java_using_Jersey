package com.terrydhariwal.client;

import com.terrydhariwal.model.Activity;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class ActivityClient {

    private Client client;

    public ActivityClient() {
        client = ClientBuilder.newClient();
    }

    public Activity get(String id) {
        WebTarget target = client.target("http://localhost:8080/rest-exercise/webapi/");

//        //By default the request will return xml if you're asking for a String
//        String response_xml = target.path("activities/" + id).request().get(String.class);
//        System.out.println("response_default: " + response_xml + "\n");
//
//        //If you want to see JSON then you need to ask for that
//        String response_json = target.path("activities/" + id).request(MediaType.APPLICATION_JSON).get(String.class);
//        System.out.println("response_json: " + response_json + "\n");

        //If you return an object, the response is automatically converted to an object using binding
        Activity response_binary = target.path("activities/" + id).request().get(Activity.class);
        System.out.println("response_binary: " + response_binary + "\n");

        return response_binary;
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
}
