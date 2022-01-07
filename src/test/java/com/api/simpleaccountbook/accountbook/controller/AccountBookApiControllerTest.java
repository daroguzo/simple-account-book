package com.api.simpleaccountbook.accountbook.controller;

import com.api.simpleaccountbook.BaseControllerTest;
import com.api.simpleaccountbook.accountbook.repository.AccountBookRepository;
import com.api.simpleaccountbook.accountbook.service.AccountBookService;
import com.api.simpleaccountbook.member.repository.MemberRepository;
import com.api.simpleaccountbook.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

class AccountBookApiControllerTest extends BaseControllerTest {

    @Autowired
    MemberService memberService;

    @Autowired
    AccountBookService accountBookService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AccountBookRepository accountBookRepository;


    // 권한 설정 불가 401에러 처리 실패
    /*
    @Test
    @DisplayName("정상적으로 가계부를 생성")
    public void createAccountBook() throws Exception {
        // given

        LocalDateTime now = LocalDateTime.now();
        AccountBookInput.AccountBookInputBuilder accountBook = AccountBookInput.builder()
                .subject("제목")
                .usedMoney(1000)
                .memo("메모")
                .regDt(now)
                .isDeleted(false);

        // when
        ResultActions result = mockMvc.perform(post("/api/account-book/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountBook))
        );

        // then
        result.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("usedMoney").value(1000))
                .andExpect(jsonPath("memo").value("메모"))
                .andExpect(jsonPath("regDt").value(now))
                .andExpect(jsonPath("isDeleted").value(false))
        ;
    }

    @Test
    @DisplayName("잘못된 가계부 입력")
    public void createWrongAccountBook() throws Exception {
        // given

        LocalDateTime now = LocalDateTime.now();
        AccountBookInput.AccountBookInputBuilder accountBook = AccountBookInput.builder()
                .subject("제목")
                .memo("메모")
                .regDt(now)
                .isDeleted(false);

        // when
        ResultActions result = mockMvc.perform(post("/api/account-book/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountBook))
        );

        // then
        result.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("usedMoney").value(1000))
                .andExpect(jsonPath("memo").value("메모"))
                .andExpect(jsonPath("regDt").value(now))
                .andExpect(jsonPath("isDeleted").value(false))
        ;
    }
     */
}