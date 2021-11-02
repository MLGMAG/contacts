package com.mlgmag.contacts.controller;

import com.mlgmag.contacts.dto.ContactsResponseDto;
import com.mlgmag.contacts.entity.Contact;
import com.mlgmag.contacts.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public ContactsResponseDto getContactsByNameFilter(@RequestParam(name = "nameFilter") String nameFilter) {
        ContactsResponseDto response = new ContactsResponseDto();
        List<Contact> filteredContacts = contactService.getContactsByNameFilter(nameFilter);
        response.setContacts(filteredContacts);
        return response;
    }

}
