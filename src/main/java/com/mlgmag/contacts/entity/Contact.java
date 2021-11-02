package com.mlgmag.contacts.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Contact")
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(generator = "contact_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "contact_id_seq", sequenceName = "contact_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name")
    private String name;
}
