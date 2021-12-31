package com.api.simpleaccountbook.member.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String password;
}
