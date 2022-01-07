package com.api.simpleaccountbook.member.controller;

import com.api.simpleaccountbook.BaseControllerTest;
import com.api.simpleaccountbook.accountbook.repository.AccountBookRepository;
import com.api.simpleaccountbook.accountbook.service.AccountBookService;
import com.api.simpleaccountbook.member.repository.MemberRepository;
import com.api.simpleaccountbook.member.service.MemberService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

class MemberApiControllerTest extends BaseControllerTest {

    @Autowired
    MemberService memberService;

    @Autowired
    AccountBookService accountBookService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AccountBookRepository accountBookRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();
    }

    // 반환 값 utf-8 설정 불가
    /*
    @Test
    @DisplayName("정상적으로 계정 생성")
    public void createMember() throws Exception {
        // given

        MemberInput.MemberInputBuilder newMember = MemberInput.builder()
                .email("newmember@email.com")
                .password("password");

        // when
        ResultActions result = mockMvc.perform(post("/api/member/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMember))
        );

        // then
        result.andExpect(status().isCreated())
                .andDo(print())
        ;
    }
     */
}