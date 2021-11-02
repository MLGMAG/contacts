package com.mlgmag.contacts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @GetMapping("/")
    public String getFilteredContact() {
        return "Hello!";
    }

}