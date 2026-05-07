// ActivityMemberAttendanceDto.java
package edu.hei.school.agricultural.controller.dto;

import lombok.Data;

@Data
public class ActivityMemberAttendanceDto {
    private String id;
    private MemberDescription memberDescription;
    private AttendanceStatus attendanceStatus;
}