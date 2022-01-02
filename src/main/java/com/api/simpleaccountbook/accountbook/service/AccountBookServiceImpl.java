package com.api.simpleaccountbook.accountbook.service;

import com.api.simpleaccountbook.accountbook.entity.AccountBook;
import com.api.simpleaccountbook.accountbook.model.AccountBookResponse;
import com.api.simpleaccountbook.accountbook.repository.AccountBookRepository;
import com.api.simpleaccountbook.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountBookServiceImpl implements AccountBookService{

    private final AccountBookRepository accountBookRepository;

    @Override
    public List<AccountBookResponse> getAccountBooks(Member member) {
        List<AccountBook> byMember = accountBookRepository.findByMember(member);
        List<AccountBookResponse> list = new ArrayList<>();

        byMember.forEach((e) -> {
            if (!e.isDeleted()) {
                list.add(AccountBookResponse.of(e));
            }
        });

        return list;
    }

    @Override
    public List<AccountBookResponse> getDeletedAccountBooks(Member member) {
        List<AccountBook> byMember = accountBookRepository.findByMember(member);
        List<AccountBookResponse> list = new ArrayList<>();

        byMember.forEach((e) -> {
            if (e.isDeleted()) {
                list.add(AccountBookResponse.of(e));
            }
        });

        return list;
    }
}
