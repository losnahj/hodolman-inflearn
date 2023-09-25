# 23.04.05

* MockMvc의 역할
    * 컨트롤러를 테스트하기 위한 객체
    * 컨트롤러의 동작을 테스트하여 요청을 생성하고 그에 대한 응답을 검증 할 수 있다.


* 컨트롤러 테스트를 하기 위해 @WebMcvTest가 필요한 이유
    * @WebMvcTest가 스프링 컨테이너(Controller를 테스트하기 위해 축소된)를 생성한다.
    * 컨테이너가 MockMvc 객체를 관리한다.


* 성공 테스트의 결과에 대한 Summary 출력 방법
  ```java
  mockMvc.perform(~)
            .andDo(print())
  ```
  
* Content-type 은 <u>헤더에 명시</u>해주어 데이터가 어떠한 형태로 표현되는지 알려준다.
  

* 데이터 검증의 필요성
  1. Client 개발자의 실수로 잘못된 값이 들어올 경우
  2. Client Bug로 값이 누락될 수 있다.
  3. 외부에서 조작된 값을 넣을 수 있다.
  4. DB 저장 시, 의도치 않은 오류
  5. 서버 개발자의 편안함을 위해



![img.png](img.png)
* if 문을 통한 검증이 좋지 않은 이유
  1. 검증해야할 것들이 너무 많다.
  2. 누락의 가능성

  * @NotBlank 사용 시 의존성 추가
    ```groovy
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    ```
  
* validation을 추가한 parameter 가 빈값으로 올 경우 디버그해도 못찾는 이유
  * 스프링에서 컨트롤러에 넘어오기전에 검증을 하고 400 에러를 반환
  * 해결법
  ![img_1.png](img_1.png)
  ![img_2.png](img_2.png)

---

# 23-04-06

* log.error() VS log.info()
  * .error() -> 예외 처리 에러 발생 시
  * .info() -> 예외 처리를 제외한 기록할만한 정보들


* API 서버에서 에러 응답은 Json 형태로 Response Body에 보내는 이점
  * 클라이언트에서 처리하기 쉽다.
  * 개발자가 디버깅하기 쉽다
    * 로그를 통해 에러를 확인하면 서버에서 처리한 내용만을 볼 수 있기 때문..


* @RequestBody를 통해 Body부분에 응답 클래스를 Json 형태로 반환해주고 싶다면?
  * 응답 클래스에 <u>Getter가 포함</u> 되어야 한다!!

  </br></br>
```json
  {
  "code": "400",
  "message": "잘못된 요청입니다.",
  "validation": {
    "title": "올바른 값을 입력해주세요."
  }
}
```
* 응답 클래스에 validation이 필요한 이유
  * title 또는 content 둘 중 어떠한 데이터가 잘못되었는지 모른다.
  * 따라서 화면단에서도 잘못된 데이터에 대한 피드백을 정확하게 줄 수 없다.

---
## 2023.04.14

* ❗절대❗ Entity 객체에 서비스 정책을 넣으면 안된다!!
  * 조졸두 강의에선 Domain(Entity)객체가 서비스 로직을 가진다 했는데?
  * 