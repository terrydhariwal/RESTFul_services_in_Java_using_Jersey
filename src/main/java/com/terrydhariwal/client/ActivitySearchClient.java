package com.terrydhariwal.client;

import com.terrydhariwal.model.Activity;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

public class ActivitySearchClient {

    private Client client;

    public ActivitySearchClient(){
        client = ClientBuilder.newClient();
    }

    public List<Activity> search(String param, List<String> searchValues) {

        URI uri = UriBuilder.fromUri("http://localhost:8080/rest-exercise/webapi")
                .path("search/activities")
                .queryParam(param, searchValues)
                .build();

        System.out.println("uri = " + uri);

        WebTarget target = client.target(uri);

        //Accepts JSON as a response
        List<Activity> response = target.request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});

        System.out.println(response);

        return response;

    }
}
