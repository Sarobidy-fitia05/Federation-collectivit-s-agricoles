package com.linkdatabase.federationagriculteur.service;

import com.linkdatabase.federationagriculteur.entity.CollectivityTransaction;
import com.linkdatabase.federationagriculteur.exception.EntityNotFoundException;
import com.linkdatabase.federationagriculteur.repository.CollectivityRepository;
import com.linkdatabase.federationagriculteur.repository.CollectivityTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CollectivityTransactionService {

    private final CollectivityTransactionRepository transactionRepository;
    private final CollectivityRepository collectivityRepository;


    public CollectivityTransactionService(
            CollectivityTransactionRepository transactionRepository,
            CollectivityRepository collectivityRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.collectivityRepository = collectivityRepository;
    }


    public List<CollectivityTransaction> getTransactions(
            String collectivityId,
            LocalDate from,
            LocalDate to
    ) {

         boolean exists = collectivityRepository.existsById(collectivityId);

        if (!exists) {
            throw new EntityNotFoundException(
                    "Collectivity not found with id: " + collectivityId
            );
        }

         return transactionRepository.findByCollectivityIdAndDateBetween(
                collectivityId,
                from,
                to
        );
    }
}