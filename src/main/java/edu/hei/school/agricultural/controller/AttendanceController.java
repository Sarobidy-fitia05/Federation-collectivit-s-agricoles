package edu.hei.school.agricultural.controller;

import edu.hei.school.agricultural.controller.dto.*;
import edu.hei.school.agricultural.exception.BadRequestException;
import edu.hei.school.agricultural.exception.NotFoundException;
import edu.hei.school.agricultural.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collectivities/{id}/activities/{activityId}/attendance")
public class AttendanceController {

    private final AttendanceService service;

    @PostMapping
    public ResponseEntity<?> createAttendance(
            @PathVariable String id,
            @PathVariable String activityId,
            @RequestBody List<CreateActivityMemberAttendanceDto> body
    ) {
        try {
            List<ActivityMemberAttendanceDto> result =
                    service.createAttendance(id, activityId, body);
            return ResponseEntity.status(CREATED).body(result);
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAttendance(
            @PathVariable String id,
            @PathVariable String activityId
    ) {
        try {
            List<ActivityMemberAttendanceDto> result =
                    service.getAttendance(id, activityId);
            return ResponseEntity.ok(result);
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}