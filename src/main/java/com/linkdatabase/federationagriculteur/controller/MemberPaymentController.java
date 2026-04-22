package com.linkdatabase.federationagriculteur.controller;

import com.linkdatabase.federationagriculteur.dto.CreateMemberPayment;
import com.linkdatabase.federationagriculteur.entity.MemberPayment;
import com.linkdatabase.federationagriculteur.exception.ConflictException;
import com.linkdatabase.federationagriculteur.service.MemberPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberPaymentController {

    private final MemberPaymentService memberPaymentService;

    public MemberPaymentController(MemberPaymentService memberPaymentService) {
        this.memberPaymentService = memberPaymentService;
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<List<MemberPayment>> createPayments(
            @PathVariable String id,
            @RequestBody List<CreateMemberPayment> requests) {
        List<MemberPayment> createdPayments = new ArrayList<>();

        try {
            for (CreateMemberPayment request : requests) {
                MemberPayment created = memberPaymentService.createPayment(request, id);
                createdPayments.add(created);
            }
            return new ResponseEntity<>(createdPayments, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (ConflictException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
        }
    }
}