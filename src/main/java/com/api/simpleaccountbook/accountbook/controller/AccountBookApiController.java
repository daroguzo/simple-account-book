package com.api.simpleaccountbook.accountbook.controller;

import com.api.simpleaccountbook.accountbook.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/account-book")
@RestController
public class AccountBookApiController {

    private final AccountBookService accountBookService;

    // TODO 가계부 반환
//    @GetMapping("/list")
//    public ResponseEntity<?> getList() {
//    }
}
