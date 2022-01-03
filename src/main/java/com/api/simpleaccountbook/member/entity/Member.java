package com.api.simpleaccountbook.member.entity;

import com.api.simpleaccountbook.accountbook.entity.AccountBook;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<AccountBook> accountBooks = new ArrayList<>();

    public void addAccountBook(AccountBook accountBook) {
        accountBook.setMember(this);
        this.accountBooks.add(accountBook);
    }
}
