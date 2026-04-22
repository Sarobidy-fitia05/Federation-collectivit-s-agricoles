package com.linkdatabase.federationagriculteur.controller;

import com.linkdatabase.federationagriculteur.dto.NumberAndNameRequest;
import com.linkdatabase.federationagriculteur.entity.Collectivity;
import com.linkdatabase.federationagriculteur.dto.CreateCollectivityRequest;
import com.linkdatabase.federationagriculteur.service.CollectivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityService collectivityService;

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
}