package com.api.simpleaccountbook.accountbook.entity;

import com.api.simpleaccountbook.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountBook {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private int usedMoney;

    @Lob
    private String memo;

    private LocalDateTime regDt;

    private boolean isDeleted;
}
