package com.mlgmag.contacts.service;

import com.mlgmag.contacts.entity.Contact;
import com.mlgmag.contacts.repository.ContactRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    private static final long ZERO = 0L;
    private static final int BATCH_SIZE = 2;
    private static final String DEFAULT_PATTERN = StringUtils.EMPTY;
    private static final String CONTACT_NAME = "ContactName";

    private final Contact contact = new Contact();

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService testInstance;

    @BeforeEach
    void setUp() {
        testInstance.setBatchSize(BATCH_SIZE);

        contact.setName(CONTACT_NAME);
    }

    @Test
    void shouldGetContactsByNameFilter() {
        when(contactRepository.count()).thenReturn(3L);
        Page<Contact> page1 = new PageImpl<>(List.of(contact, contact));
        Page<Contact> page2 = new PageImpl<>(List.of(contact));
        when(contactRepository.findAll(PageRequest.of(0, BATCH_SIZE))).thenReturn(page1);
        when(contactRepository.findAll(PageRequest.of(1, BATCH_SIZE))).thenReturn(page2);
        List<Contact> expected = List.of(contact, contact, contact);

        List<Contact> actual = testInstance.getContactsByNameFilter(DEFAULT_PATTERN);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetContactsByNameFilterOnNoData() {
        when(contactRepository.count()).thenReturn(ZERO);

        List<Contact> actual = testInstance.getContactsByNameFilter(DEFAULT_PATTERN);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldGetContactsByNameFilterOnNullContactName() {
        Contact nullNameContact = new Contact();
        when(contactRepository.count()).thenReturn(2L);
        Page<Contact> page = new PageImpl<>(List.of(contact, nullNameContact));
        when(contactRepository.findAll(PageRequest.of(0, BATCH_SIZE))).thenReturn(page);
        List<Contact> expected = List.of(contact);

        List<Contact> actual = testInstance.getContactsByNameFilter(DEFAULT_PATTERN);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("shouldGetContactsByNameFilterData")
    void shouldGetContactsByNameFilter(String pattern, List<String> inputContactNames, List<String> expectedContactNames) {
        List<Contact> input = inputContactNames.stream().map(this::createContactWithName).collect(Collectors.toList());
        List<Contact> expected = expectedContactNames.stream().map(this::createContactWithName).collect(Collectors.toList());
        when(contactRepository.count()).thenReturn(3L);
        testInstance.setBatchSize(3);
        Page<Contact> page = new PageImpl<>(input);
        when(contactRepository.findAll(PageRequest.of(0, 3))).thenReturn(page);

        List<Contact> actual = testInstance.getContactsByNameFilter(pattern);

        assertThat(compareContacts(actual, expected)).isTrue();
    }

    private static Stream<Arguments> shouldGetContactsByNameFilterData() {
        return Stream.of(
                Arguments.of("^A.*$", List.of("Andriy", "Max", "Vova"), List.of("Max", "Vova")),
                Arguments.of("^.*[aei].*$", List.of("Andriy", "Don", "Vova"), List.of("Don")),
                Arguments.of(" ", List.of("Andriy", "Max", "Vova"), List.of("Andriy", "Max", "Vova")),
                Arguments.of(".*", List.of("Andriy", "Max", "Vova"), Collections.emptyList())
        );
    }


    private Contact createContactWithName(String name) {
        Contact newContact = new Contact();
        newContact.setName(name);
        return newContact;
    }

    private boolean compareContacts(List<Contact> contacts1, List<Contact> contacts2) {
        List<String> names1 = contacts1.stream().map(Contact::getName).collect(Collectors.toList());
        List<String> names2 = contacts2.stream().map(Contact::getName).collect(Collectors.toList());
        return names1.equals(names2);
    }
}