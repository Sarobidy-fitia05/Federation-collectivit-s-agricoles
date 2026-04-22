package com.linkdatabase.federationagriculteur.service;

import com.linkdatabase.federationagriculteur.entity.*;
import com.linkdatabase.federationagriculteur.repository.CollectivityRepository;
import com.linkdatabase.federationagriculteur.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectivityService {

    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public CollectivityService(CollectivityRepository collectivityRepository,
                               MemberRepository memberRepository) {
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
    }

    public List<Collectivity> createCollectivities(List<CreateCollectivityRequest> requests) {
        List<Collectivity> created = new ArrayList<>();

        for (CreateCollectivityRequest request : requests) {
            validateBusinessRules(request);
            validateAllMembersExist(request);

            Collectivity collectivity = new Collectivity();
            collectivity.setName(request.getName());          // ← nom donné par la fédération
            collectivity.setLocation(request.getLocation());

            Collectivity saved = collectivityRepository.insertCollectivity(collectivity);

            collectivityRepository.insertCollectivityMembers(saved.getId(), request.getMembers());
            collectivityRepository.insertCollectivityStructure(
                    saved.getId(),
                    request.getStructure().getPresident(),
                    request.getStructure().getVicePresident(),
                    request.getStructure().getTreasurer(),
                    request.getStructure().getSecretary()
            );

            Collectivity fullCollectivity = collectivityRepository.findById(saved.getId());
            created.add(fullCollectivity);
        }

        return created;
    }

    private void validateBusinessRules(CreateCollectivityRequest request) {
         if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Collectivity name must be provided by the federation.");
        }
        if (!Boolean.TRUE.equals(request.getFederationApproval())) {
            throw new IllegalArgumentException("Federation approval must be true.");
        }
        if (request.getStructure() == null) {
            throw new IllegalArgumentException("Structure is required.");
        }
        if (request.getStructure().getPresident() == null ||
                request.getStructure().getVicePresident() == null ||
                request.getStructure().getTreasurer() == null ||
                request.getStructure().getSecretary() == null) {
            throw new IllegalArgumentException("All structure positions must be provided.");
        }
    }

    private void validateAllMembersExist(CreateCollectivityRequest request) {
        List<String> allMemberIds = new ArrayList<>();
        allMemberIds.addAll(request.getMembers());
        allMemberIds.add(request.getStructure().getPresident());
        allMemberIds.add(request.getStructure().getVicePresident());
        allMemberIds.add(request.getStructure().getTreasurer());
        allMemberIds.add(request.getStructure().getSecretary());

        List<Member> foundMembers = memberRepository.findByIds(allMemberIds);
        if (foundMembers.size() != allMemberIds.stream().distinct().count()) {
            List<String> foundIds = foundMembers.stream().map(Member::getId).toList();
            List<String> missing = allMemberIds.stream()
                    .distinct()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            throw new RuntimeException("Some members not found: " + missing);
        }
    }
}