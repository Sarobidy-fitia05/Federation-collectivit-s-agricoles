package com.linkdatabase.federationagriculteur.controller;

import com.linkdatabase.federationagriculteur.dto.CreateMembershipFee;
import com.linkdatabase.federationagriculteur.dto.NumberAndNameRequest;
import com.linkdatabase.federationagriculteur.entity.Collectivity;
import com.linkdatabase.federationagriculteur.dto.CreateCollectivityRequest;
import com.linkdatabase.federationagriculteur.entity.MembershipFee;
import com.linkdatabase.federationagriculteur.service.CollectivityService;
import com.linkdatabase.federationagriculteur.service.MembershipFeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private  CollectivityService collectivityService;
    private  MembershipFeeService membershipFeeService;
    public CollectivityController(CollectivityService collectivityService) {
        this.collectivityService = collectivityService;
    }

    @PostMapping
    public ResponseEntity<List<Collectivity>> createCollectivities(
            @RequestBody List<CreateCollectivityRequest> requests) {
        List<Collectivity> collectivities = collectivityService.createCollectivities(requests);
        return new ResponseEntity<>(collectivities, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/informations")
    public ResponseEntity<Collectivity> updateCollectivityInformation(
            @PathVariable String id,
            @RequestBody NumberAndNameRequest request) {

        Collectivity updated = collectivityService.assignNumberAndName(id, request);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/membershipFees")
    public ResponseEntity<List<MembershipFee>> getMembershipFees(@PathVariable String id) {
        List<MembershipFee> fees = membershipFeeService.getMembershipFees(id);
        return ResponseEntity.ok(fees);
    }

    @PostMapping("/{id}/membershipFees")
    public ResponseEntity<List<MembershipFee>> createMembershipFees(
            @PathVariable String id,
            @RequestBody List<CreateMembershipFee> requests) {

        try {
            List<MembershipFee> created = membershipFeeService.createMembershipFees(id, requests);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
             String msg = e.getMessage().toLowerCase();
            if (msg.contains("not found")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            } else if (msg.contains("already exists") || msg.contains("required") || msg.contains("must be")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
            }
        }
    }
}