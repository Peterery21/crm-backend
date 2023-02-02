package com.kodzotech.compte.controller;

import com.kodzotech.compte.dto.ContactDto;
import com.kodzotech.compte.dto.ContactResponse;
import com.kodzotech.compte.dto.StatContactDto;
import com.kodzotech.compte.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody ContactDto contactDto) {
        contactService.save(contactDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody ContactDto contactDto) {
        contactDto.setId(id);
        contactService.save(contactDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDto getContact(@PathVariable Long id) {
        return contactService.getContact(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> getAllContact() {
        return contactService.getAllContact();
    }

    @GetMapping("/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> getAllCompte(@PathVariable Integer page,
                                              @PathVariable Integer size) {
        return contactService.getAllContact(page, size);
    }

    @GetMapping("/parResponsable/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> getAllContactParResponsable(@PathVariable Long id) {
        return contactService.getAllContactParResponsable(id);
    }

    @GetMapping("/parCompte/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> getAllContactParCompte(@PathVariable Long id) {
        return contactService.getAllContactParCompte(id);
    }

    @GetMapping("/nonAssigne")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> getContactNonAssigne() {
        return contactService.getAllContactNonAssigne();
    }

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public Long getCountAllContact() {
        return contactService.getCountAllContact();
    }
//
//    @GetMapping("/total/parResponsable/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Long getCountAllContactParResponsable(@PathVariable Long id) {
//        return contactService.getCountAllContactParResponsable(id);
//    }
//
//    @GetMapping("/total/parCompte/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Long getCountAllContactParCompte(@PathVariable Long id) {
//        return contactService.getCountAllContactParCompte(id);
//    }
//
//    @GetMapping("/total/nonAssigne")
//    @ResponseStatus(HttpStatus.OK)
//    public Long getCountAllContactNonAssigne() {
//        return contactService.getCountAllContactNonAssigne();
//    }

    @GetMapping("/stat/{idResponsable}")
    @ResponseStatus(HttpStatus.OK)
    public StatContactDto getNombreCompte(@PathVariable Long idResponsable) {
        return contactService.getStatContact(idResponsable);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }


    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactDto> getContactsById(@RequestParam List<Long> ids) {
        return contactService.getContactsById(ids);
    }

}
