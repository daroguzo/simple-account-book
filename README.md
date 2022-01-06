# simple-account-book   
REST API 기반 간단한 가계부
---
### 목적 및 용도   
사용자의 소비내역을 기록/관리하는 가계부
---
#### 개발 환경   
- 개발 언어: Java 11
- 서버 프레임워크: Spring Boot 2.6.2
- 데이터베이스: mysql 5.7
- 인증: auth0 jwt token
- 테스트 프레임워크: JUnit5
---
### 응답 포멧 예시   
- statusCode, code 필드를 통해 요청의 성공/실패 여부를 반환합니다.
- message 필드를 통해 어떤 응답이 반환되었는지 알려줍니다.
- data 필드를 통해 사용자가 요청한 데이터가 반환됩니다. 응답할 결과가 없을 경우 null로 표현됩니다.
```json
{
    "statusCode": 200,
    "code": "OK",
    "message": "삭제된 가계부 목록",
    "data": [
        {
            "id": 4,
            "subject": "티셔츠",
            "regDt": "2022-01-05T16:09:59.439225"
        },
        {
            "id": 5,
            "subject": "맥도날드 빅맥 세트",
            "regDt": "2022-01-05T16:14:39.306603"
        }
    ]
}
```
```json
{
    "statusCode": 400,
    "code": "BAD_REQUEST",
    "message": "이미 등록된 이메일입니다.",
    "data": null
}
```
---
### 공개용 API   
#### member(사용자)   
  - 가입: POST /api/member/register
    1. email: 이메일 형식, 4자 이상, 필수 입력
    2. password: 4자 이상, 필수 입력
    * Response Body
```json
{
    "statusCode": 200,
    "code": "OK",
    "message": "사용자가 등록되었습니다.",
    "data": null
}
```
    Exceptioin: 400(중복된 이메일, 형식 불일치)
  - 로그인: POST /api/member/login
    1. email: 이메일 형식, 필수 입력
    2. password: 필수 입력
    * Response Body
```json
{
    "statusCode": 200,
    "code": "OK",
    "message": "로그인에 성공하였습니다.",
    "data": {
        "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYXJvZ3V6b0BuYXZlci5jb20iLCJleHAiOjE2NDE0NjAyNDZ9.h9YvOSm2myi-PhGo2WbXhKiviFTTnUJaSmLNuKLlfc1h5A4kgGZtREABU0JeB35vsb8sjDhP3Swx3eMDVerCxw",
        "refreshToken": null
    }
}
```
    Exception: 400(형식 불일치, 없는 사용자, 비밀번호 불일치)
### 인증 사용자용 API
  * 로그인 사용자 인증 정보로 사용자용 API를 호출할 수 있습니다.(30분간 유효)   
  * 인증 사용자용 API를 호출하기 위해 로그인 후 인증 토큰을 반환받으세요.   
  * 요청 헤더에 Authorization항목을 추가하고, 값으로 인증 토큰을 Bearer 키워드를 앞에 붙여 요청합니다.
  * 공통 토큰 오류(401, 유효기간이 지난 토큰 사용, 인증 정보 불일치 토큰)   
```json
{
    "code": "UNAUTHORIZED",
    "message": "잘못된 토큰입니다.",
    "statusCode": 401
}
```
#### account book(가계부)   
  - 간단한 가계부 목록 GET /api/account-book/list
```json
{
    "statusCode": 200,
    "code": "OK",
    "message": "삭제된 가계부 목록",
    "data": [
        {
            "id": 4,
            "subject": "티셔츠",
            "regDt": "2022-01-05T16:09:59.439225"
        },
        {
            "id": 5,
            "subject": "맥도날드 빅맥 세트",
            "regDt": "2022-01-05T16:14:39.306603"
        }
    ]
}
```
  - 간단한 (삭제된)가계부 목록 GET /api/account-book/deleted-book
```json
{
    "statusCode": 200,
    "code": "OK",
    "message": "삭제된 가계부 목록",
    "data": [
        {
            "id": 8,
            "subject": "아메리카노 톨 사이즈",
            "regDt": "2022-01-05T16:14:39.306603"
        },
    ]
}
```
  - 가계부 작성 POST /api/account-book/post
    1. subject: 가계부 제목, 필수 입력
    2. usedMoney: 사용한 금액, 정수형, 필수 입력
    3. memo: 상세
    4. regDt: 등록 일시
    * Response Body
```json
{
    "statusCode": 200,
    "code": "OK",
    "message": "새로운 가계부가 등록되었습니다.",
    "data": null
}
```
    Exception: 400(형식 불일치)  
   #### 상세, 수정, 삭제, 복구 공통
   #### 게시글 id (필수 입력)
```
    Exception: 400(해당되는 가계부가 없는 경우), 401(타인의 가계부에 접근하는 경우)
```
  - 가계부 상세 GET /api/account-book/detail/{id}
```json
{
    "statusCode": 200,
    "code": "OK",
    "message": "가계부 세부 내역",
    "data": {
        "id": 4,
        "usedMoney": 13900,
        "subject": "티셔츠",
        "memo": "노란색 카라 티",
        "regDt": "2022-01-05T16:09:59.439225"
    }
}
```
  - 가계부 수정 PUT /api/account-book/modify/{id}
```json
{
    "statusCode": 200,
    "code": "OK",
    "message": "수정된 4번 가계부 세부 내역",
    "data": {
        "id": 4,
        "usedMoney": 25000,
        "subject": "옷",
        "memo": "검은 정장 바지",
        "regDt": "2022-01-05T16:09:59.439225"
    }
}
```
    Exception: 400(usedMoney, memo 필수 입력)
  - 가계부 삭제 PUT /api/account-book/delete/{id}
```json
{
    "statusCode": 200,
    "code": "OK",
    "message": "6번 가계부가 정상적으로 삭제처리 되었습니다.",
    "data": null
}
```
    Exception: 400(이미 삭제 처리된 가계부를 요청할 경우)
  - 가계부 복구 PUT /api/account-book/restore/{id} 
```json
{
    "statusCode": 200,
    "code": "OK",
    "message": "4번 가계부가 정상적으로 복구 되었습니다.",
    "data": null
}
```
    Exception: 400(이미 복구 처리된 가계부를 요청할 경우)   
---
