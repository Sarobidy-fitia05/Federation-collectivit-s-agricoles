package edu.hei.school.agricultural.controller;

import edu.hei.school.agricultural.controller.dto.CollectivityLocalStatistics;
import edu.hei.school.agricultural.controller.dto.CollectivityOverallStatistics;
import edu.hei.school.agricultural.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService service;

    @GetMapping("/collectivites/{id}/statistics")
    public List<CollectivityLocalStatistics> getLocal(
            @PathVariable String id,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return service.getLocalStats(id, from, to);
    }

    @GetMapping("/collectivites/statistics")
    public List<CollectivityOverallStatistics> getGlobal(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return service.getGlobalStats(from, to);
    }
}
