package com.linkdatabase.federationagriculteur.controller;

import com.linkdatabase.federationagriculteur.dto.CreateMemberRequest;
import com.linkdatabase.federationagriculteur.entity.CollectivityTransaction;
import com.linkdatabase.federationagriculteur.entity.Member;
import com.linkdatabase.federationagriculteur.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<CollectivityTransaction>> getCollectivityTransactions(
            @PathVariable String id,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {

         if (!collectivityService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collectivity not found");
        }

         List<CollectivityTransaction> transactions = transactionService.getTransactions(id, from, to);

         return ResponseEntity.ok(transactions);
    }
}