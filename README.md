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
  - 로그인: POST /api/member/login

### 인증 사용자용 API
  * 로그인 사용자 인증 정보로 사용자용 API를 호출할 수 있습니다.   
  * 인증 사용자용 API를 호출하기 위해 로그인 후 인증 토큰을 반환받으세요.   
  * 요청 헤더에 Authorization항목을 추가하고, 값으로 인증 토큰을 Bearer 키워드를 앞에 붙여 요청합니다.   
#### account book(가계부)   
  - 간단한 가계부 목록 GET /api/account-book/list
  - 간단한 (삭제된)가계부 목록 GET /api/account-book/deleted-book
  - 가계부 작성 POST /api/account-book/post
  - 가계부 상세 GET /api/account-book/detail/{id}
  - 가계부 수정 PUT /api/account-book/modify/{id}
  - 가계부 삭제 PUT /api/account-book/delete/{id}
  - 가계부 복구 PUT /api/account-book/restore/{id}
---
