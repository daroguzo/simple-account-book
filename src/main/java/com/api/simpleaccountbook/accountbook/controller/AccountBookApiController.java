package com.api.simpleaccountbook.accountbook.controller;

import com.api.simpleaccountbook.accountbook.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/account-book", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AccountBookApiController {

    private final AccountBookService accountBookService;

    @GetMapping("/list")
    public ResponseEntity<?> getList() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
