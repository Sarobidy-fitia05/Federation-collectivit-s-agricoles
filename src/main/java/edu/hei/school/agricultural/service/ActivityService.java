package edu.hei.school.agricultural.service;

import edu.hei.school.agricultural.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository repository;

    public Map<String, Object> create(String collectivityId, Map<String, Object> activity) {
        return repository.save(collectivityId, activity);
    }

    public List<Map<String, Object>> get(String collectivityId) {
        return repository.findByCollectivity(collectivityId);
    }
}