package com.api.simpleaccountbook.member.controller;

import com.api.simpleaccountbook.member.exception.PasswordNotMatchException;
import com.api.simpleaccountbook.member.exception.ResponseError;
import com.api.simpleaccountbook.member.model.MemberInput;
import com.api.simpleaccountbook.member.model.MemberLogin;
import com.api.simpleaccountbook.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid MemberInput memberInput,
                                      Errors errors) {

        ResponseEntity<List<ResponseError>> responseErrorList = getErrorResponseEntityList(errors);
        if (responseErrorList != null) return responseErrorList;

        memberService.register(memberInput);

        return new ResponseEntity<>("사용자가 등록되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLogin memberLogin,
                                   Errors errors) {
        ResponseEntity<List<ResponseError>> responseErrorList = getErrorResponseEntityList(errors);
        if (responseErrorList != null) return responseErrorList;

        memberService.login(memberLogin);

        return new ResponseEntity<>("로그인 성공!", HttpStatus.OK);
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

    // 해당되는 사용자가 없는 경우 예외 처리
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> UsernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 비밀번호가 일치하지 않는 경우 예외 처리
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> PasswordNotMatchExceptionHandler(PasswordNotMatchException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
