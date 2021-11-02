package com.mlgmag.contacts.controller;

import com.mlgmag.contacts.dto.ContactsResponseDto;
import com.mlgmag.contacts.entity.Contact;
import com.mlgmag.contacts.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    private static final String NAME_FILTER = ".*";

    private final Contact contact = new Contact();

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController testInstance;

    @Test
    void shouldGetContactsByNameFilter() {
        when(contactService.getContactsByNameFilter(NAME_FILTER)).thenReturn(List.of(contact));
        ContactsResponseDto expected = new ContactsResponseDto();
        expected.setContacts(List.of(contact));

        ContactsResponseDto actual = testInstance.getContactsByNameFilter(NAME_FILTER);

        assertThat(actual).isEqualTo(expected);
    }
}