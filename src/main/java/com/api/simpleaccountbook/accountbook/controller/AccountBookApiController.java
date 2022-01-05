package com.api.simpleaccountbook.accountbook.controller;

import com.api.simpleaccountbook.accountbook.model.AccountBookInput;
import com.api.simpleaccountbook.accountbook.model.AccountBookResponse;
import com.api.simpleaccountbook.accountbook.service.AccountBookService;
import com.api.simpleaccountbook.member.exception.ResponseError;
import com.api.simpleaccountbook.response.Message;
import com.api.simpleaccountbook.response.StateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/api/account-book", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AccountBookApiController {

    private final AccountBookService accountBookService;

    @GetMapping("/list")
    public ResponseEntity<?> getList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        List<AccountBookResponse> accountBookList = accountBookService.getAccountBookList(principal);

        Message message = Message.builder()
                .statusCode(StateEnum.OK.getStatusCode())
                .code(StateEnum.OK)
                .message("가계부 리스트")
                .data(accountBookList)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/deleted-list")
    public ResponseEntity<?> getDeletedList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        List<AccountBookResponse> accountBookList = accountBookService.getDeletedAccountBookList(principal);

        Message message = Message.builder()
                .statusCode(StateEnum.OK.getStatusCode())
                .code(StateEnum.OK)
                .message("삭제된 가계부 리스트")
                .data(accountBookList)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<?> postAccountBook(@RequestBody @Valid AccountBookInput accountBookInput,
                                             Errors errors) {
        ResponseEntity<List<ResponseError>> responseErrorList = getErrorResponseEntityList(errors);
        if (responseErrorList != null) return responseErrorList;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        List<AccountBookResponse> accountBookList = accountBookService.getAccountBookList(principal);

        accountBookService.postAccountBook(accountBookInput, principal);

        Message message = Message.builder()
                .statusCode(StateEnum.OK.getStatusCode())
                .code(StateEnum.OK)
                .message("새로운 가계부가 등록되었습니다.")
                .data("")
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // model 안에 에러가 있다면 에러정보 리스트로 반환
    private ResponseEntity<List<ResponseError>> getErrorResponseEntityList(Errors errors) {
        List<ResponseError> responseErrorList = new ArrayList<>();

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
