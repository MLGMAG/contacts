package com.mlgmag.contacts.service;

import com.mlgmag.contacts.model.Contact;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ContactService {
    public List<Contact> getContactsByNameFilter(String nameFilter) {
        return Collections.emptyList();
    }
}
