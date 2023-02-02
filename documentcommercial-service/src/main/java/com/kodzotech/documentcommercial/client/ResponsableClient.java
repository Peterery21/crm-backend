package com.kodzotech.documentcommercial.client;

import com.kodzotech.documentcommercial.dto.ResponsableDto;
import com.kodzotech.security.interceptor.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "utilisateur-service",
        configuration = FeignClientInterceptor.class)
public interface ResponsableClient {

    @GetMapping("/utilisateurs/byId")
    public List<ResponsableDto> getResponsablesById(@RequestParam List<Long> ids);

    @GetMapping("/utilisateurs/{id}")
    public ResponsableDto getResponsable(@PathVariable Long id);
}
