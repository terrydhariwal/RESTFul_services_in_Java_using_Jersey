package com.terrydhariwal.repository;

import com.terrydhariwal.model.Activity;

import java.util.List;

public interface ActivityRepository {
    List<Activity> findAllActivities();

    Activity findActivity(String activityId);
}
