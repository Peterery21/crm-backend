package com.kodzotech.compte.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "transaction-service")
public interface TransactionClient {

    @GetMapping("/transactions/compte/{id}")
    public boolean checkUsedCompte(@PathVariable Long id);
}
