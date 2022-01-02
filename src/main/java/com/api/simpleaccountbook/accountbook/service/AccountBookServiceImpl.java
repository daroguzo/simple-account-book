package com.api.simpleaccountbook.accountbook.service;

import com.api.simpleaccountbook.accountbook.entity.AccountBook;
import com.api.simpleaccountbook.accountbook.repository.AccountBookRepository;
import com.api.simpleaccountbook.member.model.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountBookServiceImpl implements AccountBookService{

    private final AccountBookRepository accountBookRepository;


    @Override
    public List<AccountBook> getAccountBooks(MemberResponse memberResponse) {
        return null;
    }
}
