package com.mlgmag.contacts.controller;

import com.mlgmag.contacts.model.Contact;
import com.mlgmag.contacts.service.ContactService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<Contact> getContactsByNameFilter(@RequestParam(name = "nameFilter") String nameFilter) {
        return contactService.getContactsByNameFilter(nameFilter);
    }

}
