package edu.hei.school.agricultural.service;


import edu.hei.school.agricultural.controller.dto.*;
import edu.hei.school.agricultural.controller.mapper.CollectivityStatisticsMapper;
import edu.hei.school.agricultural.repository.CollectivityRepository;
import edu.hei.school.agricultural.repository.MemberRepository;
import edu.hei.school.agricultural.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository repository;
    private final CollectivityStatisticsMapper mapper;

    public List<CollectivityLocalStatistics> getLocalStats(
            String collectivityId,
            LocalDate from,
            LocalDate to
    ) throws Exception {

        List<Map<String, Object>> earned =
                repository.getEarnedAmountByMember(collectivityId, from, to);

        List<Map<String, Object>> unpaid =
                repository.getUnpaidAmountByMember(collectivityId, to);

        Map<String, Double> unpaidMap = new HashMap<>();

        for (Map<String, Object> u : unpaid) {
            unpaidMap.put(
                    (String) u.get("id"),
                    ((Number) u.get("unpaid_amount")).doubleValue()
            );
        }

        List<CollectivityLocalStatistics> result = new ArrayList<>();

        for (Map<String, Object> e : earned) {

            String memberId = (String) e.get("id");

            result.add(mapper.toLocalStats(
                    memberId,
                    (String) e.get("first_name"),
                    (String) e.get("last_name"),
                    ((Number) e.get("earned_amount")).doubleValue(),
                    unpaidMap.getOrDefault(memberId, 0.0)
            ));
        }

        return result;
    }

    public List<CollectivityOverallStatistics> getGlobalStats(
            LocalDate from,
            LocalDate to
    ) throws Exception {

        List<Map<String, Object>> percentage =
                repository.getCurrentMembersPercentage();

        List<Map<String, Object>> newMembers =
                repository.getNewMembers(from, to);

        Map<String, Integer> newMembersMap = new HashMap<>();

        for (Map<String, Object> n : newMembers) {
            newMembersMap.put(
                    (String) n.get("collectivity_id"),
                    ((Number) n.get("new_members")).intValue()
            );
        }

        List<CollectivityOverallStatistics> result = new ArrayList<>();

        for (Map<String, Object> p : percentage) {

            String collectivityId = (String) p.get("collectivity_id");

            result.add(mapper.toGlobalStats(
                    collectivityId,
                    ((Number) p.get("percentage")).doubleValue(),
                    newMembersMap.getOrDefault(collectivityId, 0)
            ));
        }

        return result;
    }
}