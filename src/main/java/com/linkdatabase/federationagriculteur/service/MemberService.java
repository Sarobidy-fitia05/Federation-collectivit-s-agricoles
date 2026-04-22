package com.linkdatabase.federationagriculteur.service;

import com.linkdatabase.federationagriculteur.dto.CreateMemberRequest;
import com.linkdatabase.federationagriculteur.entity.Member;
import com.linkdatabase.federationagriculteur.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> createMembers(List<CreateMemberRequest> requests) {
        List<Member> created = new ArrayList<>();

        for (CreateMemberRequest request : requests) {
            validateBusinessRules(request);

            List<Member> resolvedReferees = resolveReferees(request.getReferees());

            Member member = buildMemberFromRequest(request);
            Member savedMember = memberRepository.insertMember(member);

            memberRepository.insertRefereeLinks(savedMember.getId(), request.getReferees());

            savedMember.setReferees(resolvedReferees);
            created.add(savedMember);
        }

        return created;
    }

    private void validateBusinessRules(CreateMemberRequest request) {
        if (request.getReferees() == null || request.getReferees().size() < 2) {
            throw new IllegalArgumentException("A member must have at least 2 referees.");
        }

        if (!Boolean.TRUE.equals(request.getRegistrationFeePaid())) {
            throw new IllegalArgumentException("Registration fee must be paid.");
        }

        if (!Boolean.TRUE.equals(request.getMembershipDuesPaid())) {
            throw new IllegalArgumentException("Membership dues must be paid.");
        }
    }

    private List<Member> resolveReferees(List<String> refereeIds) {
        List<Member> referees = memberRepository.findByIds(refereeIds);

        if (referees.size() != refereeIds.size()) {
            List<String> foundIds = referees.stream().map(Member::getId).toList();
            List<String> missingIds = refereeIds.stream().filter(id -> !foundIds.contains(id)).toList();
            throw new RuntimeException("Referees not found with IDs: " + missingIds);
        }

        return referees;
    }

    private Member buildMemberFromRequest(CreateMemberRequest request) {
        Member member = new Member();
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setBirthDate(request.getBirthDate());
        member.setGender(request.getGender());
        member.setAddress(request.getAddress());
        member.setProfession(request.getProfession());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setEmail(request.getEmail());
        member.setOccupation(request.getOccupation());
        return member;
    }
}