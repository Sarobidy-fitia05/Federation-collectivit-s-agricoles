package com.linkdatabase.federationagriculteur.service;

import com.linkdatabase.federationagriculteur.dto.CreateCollectivityRequest;
import com.linkdatabase.federationagriculteur.dto.NumberAndNameRequest;
import com.linkdatabase.federationagriculteur.entity.*;
import com.linkdatabase.federationagriculteur.exception.ConflictException;
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
            if (request.getId() == null || request.getId().isBlank()) {
                throw new IllegalArgumentException("Collectivity id is required");
            }
            validateBusinessRules(request);
            validateAllMembersExist(request);

            Collectivity collectivity = new Collectivity();
            collectivity.setId(request.getId());
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

            created.add(collectivityRepository.findById(saved.getId()));
        }

        return created;
    }


    public Collectivity assignNumberAndName(String collectivityId, NumberAndNameRequest request) {
        validateNumberAndNameRequest(request);

        Collectivity existing = collectivityRepository.findById(collectivityId);
        if (existing == null) {
            throw new RuntimeException("Collectivity not found with id: " + collectivityId);
        }

        if (existing.getNumber() != null || existing.getName() != null) {
            throw new IllegalArgumentException(
                    "Collectivity already has a number or name assigned. It cannot be modified."
            );
        }

        if (collectivityRepository.existsByNumber(request.getNumber())) {
            throw new ConflictException("Number already in use: " + request.getNumber());
        }

        if (collectivityRepository.existsByName(request.getName())) {
            throw new ConflictException("Name already in use: " + request.getName());
        }

        return collectivityRepository.updateNumberAndName(
                collectivityId,
                request.getNumber(),
                request.getName()
        );
    }

    private void validateNumberAndNameRequest(NumberAndNameRequest request) {
        if (request.getNumber() == null || request.getNumber().isBlank()) {
            throw new IllegalArgumentException("Number is required and must not be empty.");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required and must not be empty.");
        }
    }

    private void validateBusinessRules(CreateCollectivityRequest request) {
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
        List<String> allMemberIds = new ArrayList<>(request.getMembers());
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