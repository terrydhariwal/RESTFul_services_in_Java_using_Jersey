package com.terrydhariwal.client;

import com.terrydhariwal.model.Activity;
import org.junit.Test;

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

    @Test
    public void testGetList() {
        ActivityClient client = new ActivityClient();

        List<Activity> activityList = client.get();

        System.out.println(activityList);

        assertNotNull(activityList);
    }
}