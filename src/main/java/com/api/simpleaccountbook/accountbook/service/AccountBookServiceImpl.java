package com.api.simpleaccountbook.accountbook.service;

import com.api.simpleaccountbook.accountbook.entity.AccountBook;
import com.api.simpleaccountbook.accountbook.exception.AccountBookNotFoundException;
import com.api.simpleaccountbook.accountbook.exception.UnAuthorizedAccountBookException;
import com.api.simpleaccountbook.accountbook.model.AccountBookInput;
import com.api.simpleaccountbook.accountbook.model.AccountBookDetailResponse;
import com.api.simpleaccountbook.accountbook.model.AccountBookSimpleResponse;
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
    public List<AccountBookSimpleResponse> getAccountBookList(String email) {
        Member member = getMember(email);

        List<AccountBook> byMember = accountBookRepository.findByMember(member);
        List<AccountBookSimpleResponse> list = new ArrayList<>();

        byMember.forEach((e) -> {
            if (!e.isDeleted()) {
                list.add(AccountBookSimpleResponse.of(e));
            }
        });
        return list;
    }

    @Override
    public List<AccountBookSimpleResponse> getDeletedAccountBookList(String email) {
        Member member = getMember(email);

        List<AccountBook> byMember = accountBookRepository.findByMember(member);
        List<AccountBookSimpleResponse> deletedList = new ArrayList<>();

        byMember.forEach((e) -> {
            if (e.isDeleted()) {
                deletedList.add(AccountBookSimpleResponse.of(e));
            }
        });

        return deletedList;
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
    public AccountBookDetailResponse getDetailAccountBook(Long accountBookId, String email) {
        Member member = getMember(email);
        AccountBook accountBook = getAccountBook(accountBookId, member);

        return AccountBookDetailResponse.builder()
                .id(accountBook.getId())
                .subject(accountBook.getSubject())
                .usedMoney(accountBook.getUsedMoney())
                .memo(accountBook.getMemo())
                .regDt(accountBook.getRegDt())
                .build();
    }

    @Transactional
    @Override
    public boolean deleteAccountBook(Long accountBookId, String email) {
        Member member = getMember(email);
        AccountBook accountBook = getAccountBook(accountBookId, member);

        // ?????? ????????? ??????????????? ???????????? ?????? ?????????.
        if (accountBook.isDeleted()) {
            return false;
        }

        // ???????????? ??? ??????
        accountBook.setDeleted(true);
        accountBookRepository.save(accountBook);
        return true;
    }

    @Transactional
    @Override
    public boolean restoreAccountBook(Long accountBookId, String email) {
        Member member = getMember(email);
        AccountBook accountBook = getAccountBook(accountBookId, member);

        // ?????? ???????????? ??????????????? ???????????? ?????? ?????????.
        if (!accountBook.isDeleted()) {
            return false;
        }

        // ???????????? ??? ??????
        accountBook.setDeleted(false);
        accountBookRepository.save(accountBook);
        return true;
    }

    @Transactional
    @Override
    public AccountBookDetailResponse modifyAccountBook(ModifyAccountBookInput modifyAccountBookInput, Long accountBookId, String email) {
        Member member = getMember(email);
        AccountBook accountBook = getAccountBook(accountBookId, member);

        accountBook.setUsedMoney(modifyAccountBookInput.getUsedMoney());
        accountBook.setMemo(modifyAccountBookInput.getMemo());
        accountBookRepository.save(accountBook);

        return AccountBookDetailResponse.builder()
                .id(accountBook.getId())
                .subject(accountBook.getSubject())
                .usedMoney(accountBook.getUsedMoney())
                .memo(accountBook.getMemo())
                .regDt(accountBook.getRegDt())
                .build();
    }

    /**
     * ?????? email ????????? ?????? ?????? ??????
     */
    private Member getMember(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        return byEmail.orElseThrow(() -> new UsernameNotFoundException("???????????? ???????????? ????????????."));
    }

    /**
     * 1. ?????? id??? ????????? ?????? ?????? ??????
     * 2. ???????????? ?????? id??? ????????? ??? ????????? ?????? ??????
     */
    private AccountBook getAccountBook(Long accountBookId, Member member) {
        Optional<AccountBook> byId = accountBookRepository.findById(accountBookId);
        AccountBook accountBook = byId.orElseThrow(() -> new AccountBookNotFoundException("???????????? ???????????? ????????????."));

        if (!accountBook.getMember().equals(member)) {
            throw new UnAuthorizedAccountBookException("????????? ??????????????? ????????? ??? ????????????.");
        }
        return accountBook;
    }
}
