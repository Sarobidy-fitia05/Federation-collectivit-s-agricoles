package edu.hei.school.agricultural.controller.dto;
import lombok.Data;

@Data
public class CreateActivityMemberAttendanceDto {
    private String memberIdentifier;
    private AttendanceStatus attendanceStatus;
}
