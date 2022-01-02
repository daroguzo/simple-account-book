package com.api.simpleaccountbook.accountbook.repository;

import com.api.simpleaccountbook.accountbook.entity.AccountBook;
import com.api.simpleaccountbook.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    List<AccountBook> findByMember(Member member);
}
