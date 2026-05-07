package edu.hei.school.agricultural.controller.mapper;

import edu.hei.school.agricultural.controller.dto.*;
import org.springframework.stereotype.Component;

@Component
public class CollectivityStatisticsMapper {

    public CollectivityLocalStatistics toLocalStats(
            String memberId,
            String firstName,
            String lastName,
            double earnedAmount,
            double unpaidAmount,
            double assiduityPercentage   // NEW
    ) {
        CollectivityLocalStatistics dto = new CollectivityLocalStatistics();

        dto.setMemberId(memberId);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEarnedAmount(earnedAmount);
        dto.setUnpaidAmount(unpaidAmount);
        dto.setAssiduityPercentage(assiduityPercentage); // NEW

        return dto;
    }

    public CollectivityOverallStatistics toGlobalStats(
            String collectivityId,
            double percentage,
            int newMembers,
            double overallMemberAssiduityPercentage   // NEW
    ) {
        CollectivityOverallStatistics dto = new CollectivityOverallStatistics();

        CollectivityInformation info = new CollectivityInformation();
        info.setName("Collectivity " + collectivityId);
        info.setNumber(0);

        dto.setCollectivityInformation(info);
        dto.setOverallMemberCurrentDuePercentage(percentage);
        dto.setNewMembersNumber(newMembers);
        dto.setOverallMemberAssiduityPercentage(overallMemberAssiduityPercentage); // NEW

        return dto;
    }
}