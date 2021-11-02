package com.mlgmag.contacts.service;

import com.mlgmag.contacts.entity.Contact;
import com.mlgmag.contacts.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContactsByNameFilter(String nameFilter) {
        return contactRepository.findAll().stream()
                .filter(contact -> contact.getName() != null)
                .filter(contact -> isFalse(isNameMatchRegEx(contact.getName(), nameFilter)))
                .collect(Collectors.toList());
    }

    private boolean isNameMatchRegEx(String name, String regEx) {
        return name.matches(regEx);
    }
}
