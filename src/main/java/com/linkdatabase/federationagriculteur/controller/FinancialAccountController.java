package com.linkdatabase.federationagriculteur.controller;

import com.linkdatabase.federationagriculteur.entity.FinancialAccount;
import com.linkdatabase.federationagriculteur.service.FinancialAccountService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities/{collectivityId}/financialAccounts")
public class FinancialAccountController {

    private final FinancialAccountService financialAccountService;

    public FinancialAccountController(FinancialAccountService financialAccountService) {
        this.financialAccountService = financialAccountService;
    }

    @GetMapping
    public List<FinancialAccount> getFinancialAccounts(
            @PathVariable String collectivityId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate at
    ) {
        return financialAccountService.getAccountsByCollectivityAtDate(collectivityId, at);
    }
}