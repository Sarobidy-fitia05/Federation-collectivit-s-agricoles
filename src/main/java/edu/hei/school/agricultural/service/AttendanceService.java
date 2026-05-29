package edu.hei.school.agricultural.service;

import edu.hei.school.agricultural.controller.dto.*;
import edu.hei.school.agricultural.exception.BadRequestException;
import edu.hei.school.agricultural.exception.NotFoundException;
import edu.hei.school.agricultural.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository repository;

    public List<ActivityMemberAttendanceDto> createAttendance(
            String collectivityId,
            String activityId,
            List<CreateActivityMemberAttendanceDto> requests
    ) throws Exception {

        if (!repository.activityBelongsToCollectivity(collectivityId, activityId)) {
            throw new NotFoundException("Activity or collectivity not found");
        }

        List<ActivityMemberAttendanceDto> result = new ArrayList<>();

        for (CreateActivityMemberAttendanceDto req : requests) {

            String status = req.getAttendanceStatus().name();
            Map<String, Object> row = repository.upsertAttendance(
                    activityId, req.getMemberIdentifier(), status
            );

            if (row == null) {
                // Statut déjà figé (ATTENDED ou MISSING)
                throw new BadRequestException(
                        "Attendance already confirmed for member: "
                                + req.getMemberIdentifier()
                );
            }

            result.add(toDto(row));
        }

        return result;
    }

    public List<ActivityMemberAttendanceDto> getAttendance(
            String collectivityId,
            String activityId
    ) throws Exception {

        if (!repository.activityBelongsToCollectivity(collectivityId, activityId)) {
            throw new NotFoundException("Activity or collectivity not found");
        }

        List<Map<String, Object>> rows =
                repository.getAttendanceByActivity(activityId);

        List<ActivityMemberAttendanceDto> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            result.add(toDtoFull(row));
        }
        return result;
    }

    // --- mappers internes ---

    private ActivityMemberAttendanceDto toDto(Map<String, Object> row) {
        ActivityMemberAttendanceDto dto = new ActivityMemberAttendanceDto();
        dto.setId((String) row.get("id"));
        dto.setAttendanceStatus(
                AttendanceStatus.valueOf((String) row.get("attendance_status"))
        );
        MemberDescription desc = new MemberDescription();
        desc.setId((String) row.get("member_id"));
        dto.setMemberDescription(desc);
        return dto;
    }

    private ActivityMemberAttendanceDto toDtoFull(Map<String, Object> row) {
        ActivityMemberAttendanceDto dto = new ActivityMemberAttendanceDto();
        dto.setId((String) row.get("id"));
        dto.setAttendanceStatus(
                AttendanceStatus.valueOf((String) row.get("attendance_status"))
        );
        MemberDescription desc = new MemberDescription();
        desc.setId((String)     row.get("member_id"));
        desc.setFirstName((String) row.get("first_name"));
        desc.setLastName((String)  row.get("last_name"));
        desc.setEmail((String)     row.get("email"));
        desc.setOccupation((String) row.get("occupation"));
        dto.setMemberDescription(desc);
        return dto;
    }
}