package com.linkdatabase.federationagriculteur.controller;

import com.linkdatabase.federationagriculteur.dto.CreateMembershipFee;
import com.linkdatabase.federationagriculteur.dto.NumberAndNameRequest;
import com.linkdatabase.federationagriculteur.dto.CreateCollectivityRequest;

import com.linkdatabase.federationagriculteur.entity.Collectivity;
import com.linkdatabase.federationagriculteur.entity.CollectivityTransaction;
import com.linkdatabase.federationagriculteur.entity.MembershipFee;

import com.linkdatabase.federationagriculteur.exception.EntityNotFoundException;

import com.linkdatabase.federationagriculteur.service.CollectivityService;
import com.linkdatabase.federationagriculteur.service.MembershipFeeService;
import com.linkdatabase.federationagriculteur.service.CollectivityTransactionService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityService collectivityService;
    private final MembershipFeeService membershipFeeService;
    private final CollectivityTransactionService transactionService;

    public CollectivityController(
            CollectivityService collectivityService,
            MembershipFeeService membershipFeeService,
            CollectivityTransactionService transactionService
    ) {
        this.collectivityService = collectivityService;
        this.membershipFeeService = membershipFeeService;
        this.transactionService = transactionService;
    }


    @PostMapping
    public ResponseEntity<List<Collectivity>> createCollectivities(
            @RequestBody List<CreateCollectivityRequest> requests) {

        List<Collectivity> collectivities =
                collectivityService.createCollectivities(requests);

        return new ResponseEntity<>(collectivities, HttpStatus.CREATED);
    }


    @PutMapping("/{id}/informations")
    public ResponseEntity<Collectivity> updateCollectivityInformation(
            @PathVariable String id,
            @RequestBody NumberAndNameRequest request) {

        Collectivity updated =
                collectivityService.assignNumberAndName(id, request);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/membershipFees")
    public ResponseEntity<List<MembershipFee>> getMembershipFees(@PathVariable String id) {
        try {
            List<MembershipFee> fees = membershipFeeService.getMembershipFees(id);
            return ResponseEntity.ok(fees);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @PostMapping("/{id}/membershipFees")
    public ResponseEntity<List<MembershipFee>> createMembershipFees(
            @PathVariable String id,
            @RequestBody List<CreateMembershipFee> requests) {

        try {
            List<MembershipFee> created =
                    membershipFeeService.createMembershipFees(id, requests);

            return new ResponseEntity<>(created, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            String msg = e.getMessage().toLowerCase();

            if (msg.contains("not found")) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        e.getMessage()
                );

            } else if (msg.contains("already exists")
                    || msg.contains("required")
                    || msg.contains("must be")) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        e.getMessage()
                );

            } else {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Unexpected error"
                );
            }
        }
    }


    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<CollectivityTransaction>> getTransactions(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        if (from == null || to == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Query parameters 'from' and 'to' are required"
            );
        }

        if (from.isAfter(to)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "'from' date must be before or equal to 'to' date"
            );
        }

        try {
            List<CollectivityTransaction> transactions =
                    transactionService.getTransactions(id, from, to);

            return ResponseEntity.ok(transactions);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );

        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error"
            );
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Collectivity> getCollectivity(@PathVariable String id) {
        try { Collectivity collectivity = collectivityService.getCollectivityById(id);
            return ResponseEntity.ok(collectivity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // 🔥 très important
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }
    }
}