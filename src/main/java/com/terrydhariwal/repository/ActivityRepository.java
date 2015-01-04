package com.terrydhariwal.repository;

import com.terrydhariwal.model.Activity;

import java.util.List;

public interface ActivityRepository {
    List<Activity> findAllActivities();

    Activity findActivity(String activityId);

    void create(Activity activity);

    Activity update(Activity activity);

    void delete(String activityId);

    List<Activity> findByDescription(List<String> descriptions, int durationFrom, int durationTo);

    List<Activity> findByConstraints(List<String> descriptions, int durationFrom, int durationTo);
}
