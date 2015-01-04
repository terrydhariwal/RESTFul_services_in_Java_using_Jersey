package com.terrydhariwal.client;

import com.terrydhariwal.model.Activity;
import com.terrydhariwal.model.ActivitySearch;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

public class ActivitySearchClient {

    private Client client;

    public ActivitySearchClient(){
        client = ClientBuilder.newClient();
    }

    public List<Activity> search(String param, List<String> searchValues, String secondParam, int durationFrom, String thirdParam, int durationTo) {

        URI uri = UriBuilder.fromUri("http://localhost:8080/rest-exercise/webapi")
                .path("search/activities")
                .queryParam(param, searchValues)
                .queryParam(secondParam, durationFrom)
                .queryParam(thirdParam, durationTo)
                .build();

        System.out.println("uri = " + uri);

        WebTarget target = client.target(uri);

        //Accepts JSON as a response
        List<Activity> response = target.request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});

        System.out.println(response);

        return response;

    }

    public List<Activity> search(ActivitySearch search) {

        URI uri = UriBuilder.fromUri("http://localhost:8080/rest-exercise/webapi")
                .path("search/activities")
                .build();

        System.out.println("uri = " + uri);

        WebTarget target = client.target(uri);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(search, MediaType.APPLICATION_JSON));

        System.out.println(response);

        if(response.getStatus() != Response.Status.OK.getStatusCode()) { //See http://jersey.576304.n2.nabble.com/Reponse-Status-response-getStatusInfo-td7581629.html
            throw new RuntimeException(response.getStatus() + " (" + response.getStatusInfo() + ")" + " there was an error on the server.");
        }

        return response.readEntity(new GenericType<List<Activity>>() {});
    }
}
