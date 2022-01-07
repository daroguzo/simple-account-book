package com.api.simpleaccountbook.member.controller;

import com.api.simpleaccountbook.response.ResponseError;
import com.api.simpleaccountbook.member.model.MemberInput;
import com.api.simpleaccountbook.member.model.MemberLogin;
import com.api.simpleaccountbook.member.model.MemberLoginToken;
import com.api.simpleaccountbook.member.service.MemberService;
import com.api.simpleaccountbook.response.Message;
import com.api.simpleaccountbook.response.StateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/api/member", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid MemberInput memberInput,
                                      Errors errors) {

        ResponseEntity<?> responseErrorList = getErrorResponseEntityList(errors);
        if (responseErrorList != null) return responseErrorList;

        memberService.register(memberInput);

        Message message = Message.builder()
                .statusCode(StateEnum.CREATED.getStatusCode())
                .code(StateEnum.CREATED)
                .message("사용자가 등록되었습니다.")
                .build();

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLogin memberLogin,
                                   Errors errors) {
        ResponseEntity<?> responseErrorList = getErrorResponseEntityList(errors);
        if (responseErrorList != null) return responseErrorList;

        MemberLoginToken token = memberService.login(memberLogin);

        Message message = Message.builder()
                .statusCode(StateEnum.OK.getStatusCode())
                .code(StateEnum.OK)
                .message("로그인에 성공하였습니다.")
                .data(token)
                .build();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // model 안에 에러가 있다면 에러정보 리스트로 반환
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
}
