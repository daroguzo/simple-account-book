package com.api.simpleaccountbook.accountbook.service;

import com.api.simpleaccountbook.accountbook.entity.AccountBook;
import com.api.simpleaccountbook.accountbook.model.AccountBookInput;
import com.api.simpleaccountbook.accountbook.model.AccountBookResponse;
import com.api.simpleaccountbook.accountbook.model.ModifyAccountBookInput;
import com.api.simpleaccountbook.accountbook.repository.AccountBookRepository;
import com.api.simpleaccountbook.member.entity.Member;
import com.api.simpleaccountbook.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountBookServiceImpl implements AccountBookService{

    private final AccountBookRepository accountBookRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<AccountBookResponse> getAccountBookList(String email) {
        Member member = getMember(email);

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
    public List<AccountBookResponse> getDeletedAccountBookList(String email) {
        Member member = getMember(email);

        List<AccountBook> byMember = accountBookRepository.findByMember(member);
        List<AccountBookResponse> list = new ArrayList<>();

        byMember.forEach((e) -> {
            if (e.isDeleted()) {
                list.add(AccountBookResponse.of(e));
            }
        });

        return list;
    }

    @Transactional
    @Override
    public void postAccountBook(AccountBookInput accountBookInput, String email) {
        Member member = getMember(email);

        AccountBook newAccountBook = AccountBook.builder()
                .subject(accountBookInput.getSubject())
                .usedMoney(accountBookInput.getUsedMoney())
                .memo(accountBookInput.getMemo())
                .regDt(LocalDateTime.now())
                .isDeleted(false)
                .build();

        member.addAccountBook(newAccountBook);
        accountBookRepository.save(newAccountBook);
    }

    @Override
    public AccountBookResponse getAccountBook(Long accountBookId) {
        return null;
    }

    @Override
    public boolean deleteAccountBook(Long accountBookId) {
        return false;
    }

    @Override
    public boolean restoreAccountBook(Long accountBookId) {
        return false;
    }

    @Override
    public void modifyAccountBook(ModifyAccountBookInput modifyAccountBookInput) {

    }

    private Member getMember(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        return byEmail.orElseThrow(() -> new UsernameNotFoundException("일치하는 이메일이 없습니다."));
    }
}
