package com.kodzotech.documentcommercial.client;

import com.kodzotech.documentcommercial.dto.ContactDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "contact-service")
public interface ContactClient {

    @GetMapping("/contacts/{id}")
    public ContactDto getContact(@PathVariable Long id);

    @GetMapping("/contacts/byId")
    public List<ContactDto> getContactsById(@RequestParam List<Long> ids);
}
