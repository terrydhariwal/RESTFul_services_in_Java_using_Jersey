package com.terrydhariwal.client;

import com.terrydhariwal.model.Activity;
import com.terrydhariwal.model.ActivitySearch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ActivityClientTest {

    @Test
    public void testGet() throws Exception {
        ActivityClient client = new ActivityClient();
        Activity activity = client.get("1234");
        System.out.println(activity);
        assertNotNull(activity);
    }

    @Test(expected = RuntimeException.class)
    public void testGetWithBadRequest() {
        ActivityClient client = new ActivityClient();
        client.get("123");
    }

    @Test(expected = RuntimeException.class)
    public void testGetWithNotFound() {
        ActivityClient client = new ActivityClient();
        client.get("7777");
    }

    @Test
    public void testGetList() {
        ActivityClient client = new ActivityClient();
        List<Activity> activityList = client.get();
        System.out.println(activityList);
        assertNotNull(activityList);
    }

    @Test
    public void testCreate() {
        ActivityClient client = new ActivityClient();

        Activity activity = new Activity();
        activity.setDescription("Swimming");
        activity.setDuration(90);

        //Don't specify an id here because this is a POST (id should be generated by DB)
        // POST should be used if the server is supplying the ID
            // A POST is to a generic URL without specifying an ID,
            // e.g. http://localhost:8080/rest-exercise/webapi/activities/activity
            // the POST is to a generic URL
            // Each call will create a new Object
        // PUT should be used if you are supplying the ID for your object.
            // A PUT on the other hand should go to a specific URL,
            // e.g. http://localhost:8080/rest-exercise/webapi/activities/1234
            // So the same url except on the end instead of activity, we have the ID 1234
            // A call will either create a new or update an existing Object (its Idempotent)
            // So if you call the same PUT multiple times it will not change the state of the db/model

        Activity createdActivity =  client.create(activity); //create activity and return it

        assertNotNull(createdActivity);
    }

    @Test
    public void testPut() {

        //create a pretend activity that represents our PUT call
        Activity activity = new Activity();
        activity.setId("3456"); //i.e. we know the ID - because this is a PUT
        activity.setDescription("Bikram Yoga");
        activity.setDuration(90);

        ActivityClient client = new ActivityClient();

        activity = client.update(activity);

        assertNotNull(activity);
    }

    @Test
    public void testDelete() {
        ActivityClient client = new ActivityClient();

        client.delete("1234"); //i.e. we know the ID - because this is a DELETE

    }

    @Test
    public void testSearch() {
        ActivitySearchClient client = new ActivitySearchClient();

        // lets give it a few things to search off of even though
        // we've hardcoded the response
        String param = "description";
        List<String> searchValues = new ArrayList<String>();
        searchValues.add("swimming");
        searchValues.add("running");

        String secondParam = "durationFrom";
        int durationFrom = 30;

        String thirdParam = "durationTo";
        int durationTo = 55;

        List<Activity> activities = client.search(param, searchValues, secondParam, durationFrom, thirdParam, durationTo);

        System.out.println(activities);

        assertNotNull(activities);
    }

    @Test
    public void testSearchObject() {

        ActivitySearchClient client = new ActivitySearchClient();

        List<String> searchValues = new ArrayList<String>();
        searchValues.add("swimming");
        searchValues.add("biking");

        ActivitySearch search = new ActivitySearch();
        search.setDescriptions(searchValues);
        search.setDurationFrom(30);
        search.setDurationTo(40);

        List<Activity> activities = client.search(search);

        System.out.println(activities);

        assertNotNull(activities);

    }
}