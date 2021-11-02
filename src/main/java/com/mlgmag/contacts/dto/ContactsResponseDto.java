package com.mlgmag.contacts.dto;

import com.mlgmag.contacts.entity.Contact;
import lombok.Data;

import java.util.List;

@Data
public class ContactsResponseDto {
    private List<Contact> contacts;
}
