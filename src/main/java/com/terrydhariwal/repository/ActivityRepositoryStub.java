package com.terrydhariwal.repository;

import com.terrydhariwal.model.Activity;
import com.terrydhariwal.model.User;

import java.util.List;
import java.util.ArrayList;

/**
 * A stub is a common term for mimicking a backend data base
 */
public class ActivityRepositoryStub implements ActivityRepository {

    @Override
    public List<Activity> findAllActivities() {
        List<Activity> activities = new ArrayList<Activity>();

        Activity activity1 = new Activity();
        activity1.setDescription("Swimming");
        activity1.setDuration(55);
        activities.add(activity1);

        Activity activity2 = new Activity();
        activity2.setDescription("Cycling");
        activity2.setDuration(120);
        activities.add(activity2);

        return activities;
    }

    @Override
    public Activity findActivity(String activityId) {

        // I'm just hard coding this for now
        // Also note that you shouldn't really return an id to the end-user,
        // but just to show that I'm returning this object, I will return it
        Activity activity1 = new Activity();
        activity1.setId("1234");
        activity1.setDescription("Swimming");
        activity1.setDuration(55);

        User user = new User();
        user.setId("5678");
        user.setName("terry");
        activity1.setUser(user);

        return activity1;
    }

    @Override
    public void create(Activity activity) {
        //should issue an insert statement to the db...
    }
}
