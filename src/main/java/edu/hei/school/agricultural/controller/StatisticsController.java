package edu.hei.school.agricultural.controller;


import edu.hei.school.agricultural.controller.dto.CollectivityLocalStatistics;
import edu.hei.school.agricultural.controller.dto.CollectivityOverallStatistics;
import edu.hei.school.agricultural.exception.BadRequestException;
import edu.hei.school.agricultural.exception.NotFoundException;
import edu.hei.school.agricultural.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService service;

     @GetMapping("/collectivites/{id}/statistics")
    public ResponseEntity<?> getCollectivityMemberStatistics(
            @PathVariable String id,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        try {
            List<CollectivityLocalStatistics> stats = service.getLocalStats(id, from, to);
            return ResponseEntity.ok(stats);
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/collectivites/statistics")
    public ResponseEntity<?> getOverallStatistics(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        try {
            List<CollectivityOverallStatistics> stats = service.getGlobalStats(from, to);
            return ResponseEntity.ok(stats);
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}