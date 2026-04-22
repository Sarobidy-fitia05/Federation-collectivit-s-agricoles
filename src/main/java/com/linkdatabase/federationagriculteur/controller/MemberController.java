package com.linkdatabase.federationagriculteur.controller;

import com.linkdatabase.federationagriculteur.dto.CreateMemberRequest;
import com.linkdatabase.federationagriculteur.entity.Member;
import com.linkdatabase.federationagriculteur.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<List<Member>> createMembers(@RequestBody List<CreateMemberRequest> requests) {
        List<Member> members = memberService.createMembers(requests);
        return new ResponseEntity<>(members, HttpStatus.CREATED);
    }
}