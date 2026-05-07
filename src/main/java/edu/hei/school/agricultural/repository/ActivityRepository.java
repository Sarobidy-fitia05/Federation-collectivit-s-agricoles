package edu.hei.school.agricultural.repository;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ActivityRepository {

    private final List<Map<String, Object>> db = new ArrayList<>();

    public Map<String, Object> save(String collectivityId, Map<String, Object> activity) {
        activity.put("id", UUID.randomUUID().toString());
        activity.put("collectivityId", collectivityId);
        db.add(activity);
        return activity;
    }

    public List<Map<String, Object>> findByCollectivity(String id) {
        return db.stream()
                .filter(a -> a.get("collectivityId").equals(id))
                .toList();
    }
}