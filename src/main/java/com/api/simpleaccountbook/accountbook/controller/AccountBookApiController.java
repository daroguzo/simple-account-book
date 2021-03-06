package com.api.simpleaccountbook.accountbook.controller;

import com.api.simpleaccountbook.accountbook.model.AccountBookDetailResponse;
import com.api.simpleaccountbook.accountbook.model.AccountBookInput;
import com.api.simpleaccountbook.accountbook.model.AccountBookSimpleResponse;
import com.api.simpleaccountbook.accountbook.model.ModifyAccountBookInput;
import com.api.simpleaccountbook.accountbook.service.AccountBookService;
import com.api.simpleaccountbook.response.ResponseError;
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

    @GetMapping("/")
    public ResponseEntity<?> getList() {
        String email = getMemberEmail();
        List<AccountBookSimpleResponse> accountBookList = accountBookService.getAccountBookList(email);

        Message message = Message.builder()
                .statusCode(StateEnum.OK.getStatusCode())
                .code(StateEnum.OK)
                .message("가계부 목록")
                .data(accountBookList)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/deleted-list")
    public ResponseEntity<?> getDeletedList() {
        String email = getMemberEmail();
        List<AccountBookSimpleResponse> deletedAccountBookList = accountBookService.getDeletedAccountBookList(email);

        Message message = Message.builder()
                .statusCode(StateEnum.OK.getStatusCode())
                .code(StateEnum.OK)
                .message("삭제된 가계부 목록")
                .data(deletedAccountBookList)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> postAccountBook(@RequestBody @Valid AccountBookInput accountBookInput,
                                             Errors errors) {
        ResponseEntity<?> responseErrorList = getErrorResponseEntityList(errors);
        if (responseErrorList != null) return responseErrorList;

        String email = getMemberEmail();
        accountBookService.postAccountBook(accountBookInput, email);

        Message message = Message.builder()
                .statusCode(StateEnum.CREATED.getStatusCode())
                .code(StateEnum.CREATED)
                .message("새로운 가계부가 등록되었습니다.")
                .build();
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {

        String email = getMemberEmail();
        AccountBookDetailResponse detailAccountBook = accountBookService.getDetailAccountBook(id, email);

        Message message = Message.builder()
                .statusCode(StateEnum.OK.getStatusCode())
                .code(StateEnum.OK)
                .message(id + "번 가계부 세부 내역")
                .data(detailAccountBook)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountBook(@PathVariable Long id) {

        String email = getMemberEmail();
        Message message;

        if (accountBookService.deleteAccountBook(id, email)) {
            message = Message.builder()
                    .statusCode(StateEnum.OK.getStatusCode())
                    .code(StateEnum.OK)
                    .message(id + "번 가계부가 정상적으로 삭제처리 되었습니다.")
                    .build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        message = Message.builder()
                .statusCode(StateEnum.BAD_REQUEST.getStatusCode())
                .code(StateEnum.BAD_REQUEST)
                .message(id + "번 가계부는 이미 삭제처리된 가계부입니다.")
                .build();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<?> restoreAccountBook(@PathVariable Long id) {

        String email = getMemberEmail();
        Message message;

        if (accountBookService.restoreAccountBook(id, email)) {
            message = Message.builder()
                    .statusCode(StateEnum.OK.getStatusCode())
                    .code(StateEnum.OK)
                    .message(id + "번 가계부가 정상적으로 복구 되었습니다.")
                    .build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        message = Message.builder()
                .statusCode(StateEnum.BAD_REQUEST.getStatusCode())
                .code(StateEnum.BAD_REQUEST)
                .message(id + "번 가계부는 이미 존재하는 가계부입니다.")
                .build();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyAccountBook(@RequestBody @Valid ModifyAccountBookInput modifyAccountBookInput,
            Errors errors,
            @PathVariable Long id) {
        ResponseEntity<?> responseErrorList = getErrorResponseEntityList(errors);
        if (responseErrorList != null) return responseErrorList;

        String email = getMemberEmail();

        AccountBookDetailResponse response = accountBookService.modifyAccountBook(modifyAccountBookInput, id, email);

        Message message = Message.builder()
                .statusCode(StateEnum.OK.getStatusCode())
                .code(StateEnum.OK)
                .message("수정된 " + id + "번 가계부 세부 내역")
                .data(response)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * model 안에 에러가 있다면 에러정보 리스트로 반환
      */
    private ResponseEntity<?> getErrorResponseEntityList(Errors errors) {
        List<ResponseError> responseErrorList = new ArrayList<>();

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            Message message = Message.builder()
                    .statusCode(StateEnum.BAD_REQUEST.getStatusCode())
                    .code(StateEnum.BAD_REQUEST)
                    .message("잘못된 요청이 포함되어있습니다.")
                    .data(responseErrorList)
                    .build();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    /**
     * Authentication 통해 토큰에 저장된 유저 정보 가져오기
     */
    private String getMemberEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}
