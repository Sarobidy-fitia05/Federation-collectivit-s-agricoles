package com.linkdatabase.federationagriculteur.service;

import com.linkdatabase.federationagriculteur.entity.FinancialAccount;
import com.linkdatabase.federationagriculteur.repository.FinancialAccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialAccountService {

    private final FinancialAccountRepository financialAccountRepository;

    public FinancialAccountService(FinancialAccountRepository financialAccountRepository) {
        this.financialAccountRepository = financialAccountRepository;
    }

    public List<FinancialAccount> getAccountsByCollectivityAtDate(String collectivityId, LocalDate at) {

        List<FinancialAccount> accounts =
                financialAccountRepository.findByCollectivityId(collectivityId);

         if (at == null) {
            return accounts;
        }

         for (FinancialAccount account : accounts) {

            account.setAmount(
                    financialAccountRepository.calculateBalanceAt(account.getId(), at)
            );
        }

        return accounts;
    }
}