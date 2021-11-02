package com.mlgmag.contacts.service;

import com.mlgmag.contacts.entity.Contact;
import com.mlgmag.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
public class ContactService {

    @Value("${db.batch.size}")
    private int batchSize;

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContactsByNameFilter(String nameFilter) {

        int rowsCount = (int) contactRepository.count();

        List<Contact> result = new ArrayList<>();

        int pages = (rowsCount / batchSize) + 1;
        for (int i = 0; i < pages; i++) {
            List<Contact> batchContacts = contactRepository.findAll(PageRequest.of(i, batchSize)).getContent();
            result.addAll(filterContactsByNames(batchContacts, nameFilter));
        }

        return result;
    }

    private List<Contact> filterContactsByNames(List<Contact> contacts, String regEx) {
        return contacts.stream()
                .filter(contact -> contact.getName() != null)
                .filter(contact -> isFalse(isNameMatchRegEx(contact.getName(), regEx)))
                .collect(Collectors.toList());
    }

    private boolean isNameMatchRegEx(String name, String regEx) {
        return name.matches(regEx);
    }
}
