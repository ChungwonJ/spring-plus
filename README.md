# SPRING PLUS

## Level. 1

### **1. @Transactional의 이해**
-할 일 저장 기능을 구현한 API(/todos)를 호출할 때 저장불가 에러발생

### 해결
### 위치 : [TodoService](‎src/main/java/org/example/expert/domain/todo/service/TodoService.java)
-Transactional 어노테이션이 읽기전용으로 설정 되어있는 부분 삭제

### **2. JWT의 이해**
- User의 정보에 nickname이 필요해졌어요.
- User 테이블에 nickname 컬럼을 추가해주세요.
- nickname은 중복 가능합니다.
- 프론트엔드 개발자가 JWT에서 유저의 닉네임을 꺼내 화면에 보여주길 원하고 있어요.

### 해결
### 위치 : [User](src/main/java/org/example/expert/domain/user/entity/User.java), 
[SignupRequest](src/main/java/org/example/expert/domain/auth/dto/request/SignupRequest.java),
[AuthService](src/main/java/org/example/expert/domain/auth/service/AuthService.java),
[JwtUtil](src/main/java/org/example/expert/config/JwtUtil.java),
[JwtFilter](src/main/java/org/example/expert/config/JwtFilter.java)
-User 엔티티에 nickname 추가
-회원가입시 nickname 추가
-JwtUtil에 빌드시 사용자 닉네임 추가
-JwtFilter에 닉네임 속성 설정
-AuthService에 토큰 반환시 nickname도 get할 수 있도록 수정

### **3. JPA의 이해**
- 할 일 검색 시 weather 조건으로도 검색할 수 있어야 한다.
    - weather 조건은 있을 수도 있고, 없을 수도 있다.
- 할 일 검색 시 수정일 기준으로 기간 검색이 가능해야 한다.
    - 기간의 시작과 끝 조건은 있을 수도 있고, 없을 수도 있다.
- JPQL을 사용하고, 쿼리 메소드 명은 자유롭게 지정해도 된다.

### 해결
### 위치 : [TodoController](src/main/java/org/example/expert/domain/todo/controller/TodoController.java),
[TodoService](src/main/java/org/example/expert/domain/todo/service/TodoService.java),
[TodoRepository](src/main/java/org/example/expert/domain/todo/repository/TodoRepository.java)
- TodoController에 RequestParam으로 weather, start, end 값을 추가함.
- 해당 값들은 required = false로 입력되지 않으면 null로 처리 됨.
- TodoService의 getTodos에서 start와 end에 대한 값을 LocalDateTime으로 변환하고 todoRepository에 weather, startDay, endDay 값을 파라미터로 사용하여
  요청
- TodoRepository에서 JPQL 쿼리를 새로 생성하여 findAllByCondition 이름을 통해 반환.

### **4. 컨트롤러 테스트의 이해**
- 테스트 패키지 org.example.expert.domain.todo.controller의 todo_단건_조회_시_todo가_존재하지_않아_예외가_발생한다() 테스트가 실패하고 있으므로, 수정하여 정상 동작하도록
  해야 한다.

### 해결
### 위치 : [TodoControllerTest](src/test/java/org/example/expert/domain/todo/controller/TodoControllerTest.java)
-TodoControllerTest에서 BAD_REQUEST가 되어야 400번 에러 테스트가 성공이니 기존에 ok되었던 부분들을 BAD_REQUEST로 수정

### **5. AOP의 이해**
- AOP는 UserAdminController 클래스의 changeUserRole() 메서드 실행 전 동작해야 한다.
- AdminAccessLoggingAspect 클래스에 있는 AOP를 수정하여 위 요구사항을 충족해야 한다.

### 해결
### 위치 : [AdminAccessLoggingAspect](src/main/java/org/example/expert/aop/AdminAccessLoggingAspect.java)
- AdminAccessLoggingAspect 부분에 @After 를 @Before로 바꿔 메서드 실행전 동작할 수 있도록 수정.

## Level. 2

### **6. JPA Cascade**
- 할 일을 새로 저장할 시, 할 일을 생성한 유저는 담당자로 자동 등록 되어야 한다.
- JPA의 Cascade 기능을 활용해 할 일을 생성한 유저가 담당자로 등록될 수 있게 하여야 한다.

### 해결
### 위치 : [Todo](src/main/java/org/example/expert/domain/todo/entity/Todo.java)
- managers에서 기존 @OneToMany(mappedBy = "todo") 를 @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)로 수정하여 manager까지
  함께 영속화 시키도록 수정함.

### **7. N + 1**
- CommentController 클래스의 getComments() API를 호출할 때 N + 1 문제가 발생하고 있으므로 수정해야 한다.

### 해결
### 위치 : [CommentRepository](src/main/java/org/example/expert/domain/comment/repository/CommentRepository.java)
-JPQL로 작성된 findByTodoIdWithUser를 JOIN에서 LEFT JOIN FETCH로 수정


### **8. QueryDSL**
- JPQL로 작성된 findByIdWithUser(TodoService)를 QueryDSL로 변경해야 한다.
- N + 1 문제가 발생해서는 안된다.

### 해결
### 위치 : [build](build.gradle),
[QueryDslConfig](src/main/java/org/example/expert/config/QueryDslConfig.java),
[TodoRepositoryCustom](src/main/java/org/example/expert/domain/todo/repository/TodoRepositoryCustom.java),
[TodoRepositoryCustomImpl](src/main/java/org/example/expert/domain/todo/repository/TodoRepositoryCustomImpl.java)
- QueryDSL을 사용하기 위해 gradle에 의존성을 추가.
- QueryDslConfig 클래스를 통해 JPAQueryFactory 빈을 설정.
- TodoRepositoryCustom Interface를 정의한 TodoRepositoryCustomImpl class 생성하여 findByIdWithUser 메서드 작성
- TodoRepositoryCustomImpl의 Q클래스를 이용하여 작성.

### **9. Spring Security**
- 기존 Filter와 Argument Resolver를 사용하던 코드를 Spring Security로 변경해야 한다.
    - 접근 권한 및 유저 권한 기능은 그대로 유지하여야 한다.
    - 권한은 Spring Security의 기능을 사용하여야 한다.
- JWT는 그대로 사용하여야 한다.

### 해결
### 위치 : [build](build.gradle),
[UserRole](src/main/java/org/example/expert/domain/user/enums/UserRole.java),
[JwtAuthenticationFilter](src/main/java/org/example/expert/config/JwtAuthenticationFilter.java),
[JwtAuthenticationToken](src/main/java/org/example/expert/config/JwtAuthenticationToken.java),
[JwtUtil](src/main/java/org/example/expert/config/JwtUtil.java),
[SecurityConfig](src/main/java/org/example/expert/config/SecurityConfig.java),
[AuthUser](src/main/java/org/example/expert/domain/common/dto/AuthUser.java),
[TodoController](src/main/java/org/example/expert/domain/todo/controller/TodoController.java)
- UserRole을 Spring Security에 맞게 변경.
- AuthUser에서 List 형태로 userRole을 반환할 수 있도록 변경.
- Security 보안을 통과하려면 SecurityContext에 AbstractAuthenticationToken을 set해주어야 하기 때문에,JWTAuthenticationToken을 생성하여 관리한다.
- Spring Security에 대한 필터를 등록하기 위하여 SecurityConfig 클래스를 생성하여 Bean으로 등록.

## Level. 3

### **10. QueryDSL을 사용하여 검색 기능 만들기**
- 새 API를 통해 만들어야 한다.
- 검색 조건은 다음을 포함해야 한다.
    - 일정의 제목으로 검색할 수 있어야 한다.
        - 일정의 제목은 부분적으로 일치해도 검색이 가능해야 한다.
    - 일정의 생성일 범위로 검색할 수 있어야 한다.
        - 생성일 최신순으로 정렬하여 반환하여야 한다.
    - 담당자의 닉네임으로도 검색이 가능하여야 한다.
        - 닉네임은 부분적으로 일치해도 검색이 가능하여야 한다.
    - 검색 결과는 다음 내용을 포함하여 반환하여야 한다.
        - 일정의 제목
        - 해당 일정의 담당자 수
        - 해당 일정의 총 댓글 개수
    - 검색 결과는 페이징 처리되어 반환되도록 하여야 한다.

### API
| HTTP 메서드 | 기능                  | URL               | 인증 필요 | 파라미터                                                        | 요청 데이터 | 응답 코드 및 설명                  | 응답 데이터                                                                      |
|----------|---------------------|-------------------|-------|-------------------------------------------------------------|--------|-----------------------------|-----------------------------------------------------------------------------|
| GET      | QueryDSL을 이용한 할일 검색 | `/todos/search` | YES   | Query - String : title, String createdAt, String : nickname | none   | `200 OK`, `400 Bad Request` | `{ "title": string, "managerCount" : long, "commentCount" : long }` |

### 해결
### 위치 : [TodoController](src/main/java/org/example/expert/domain/todo/controller/TodoController.java),
[TodoSearchResponse](src/main/java/org/example/expert/domain/todo/dto/response/TodoSearchResponse.java),
[TodoRepositoryCustom](src/main/java/org/example/expert/domain/todo/repository/TodoRepositoryCustom.java),
[TodoRepositoryCustomImpl](src/main/java/org/example/expert/domain/todo/repository/TodoRepositoryCustomImpl.java),
[TodoService](src/main/java/org/example/expert/domain/todo/service/TodoService.java),
- TodoController에 새 API를 생성한다.
- 해당 API에는 쿼리파라미터로 page, size, title, createdAt, nickname이 들어가고, title createdAt, nickname은 없으면 null로 처리되도록 구성.
- TodoService에  searchTodos 메서드 생성.
- TodoRepositoryCustom에 todo의 id, title, 담당자 수, 댓글 수를 담을 수 있도록 구성.
- TodoRepositoryCustomImpl에 searchTodos를 구현.

### **11. Transaction 심화**
- 매니저 등록 요청을 기록하는 로그 테이블을 만들어야 한다.
    - DB 테이블 명은 log로 한다.
- 매니저 등록과는 별개로 로그 테이블에는 항상 요청 로그가 남아야 한다.
    - 매니저 등록이 실패하더라도 로그는 반드시 저장되어야 한다.
    - 로그 생성 시간은 반드시 필요하다.
    - 그 외 로그에 들어가는 내용은 원하는 정보를 자유롭게 넣는다.
    - 본인이 구성한 정보
        - 로그 내용
        - 로그 요청 시각
        - 성공 실패 여부

### 해결
### 위치 : [Log](src/main/java/org/example/expert/domain/log/entity/Log.java),
[LogRepository](src/main/java/org/example/expert/domain/log/repository/LogRepository.java),
[LogService](src/main/java/org/example/expert/domain/log/service/LogService.java),
[ManagerService](src/main/java/org/example/expert/domain/manager/service/ManagerService.java)
-Log를 저장할 수 있게 entity와 repository 설정.
-LogService에서 @Transactional(propagation = Propagation.REQUIRES_NEW)를 통해 매니저 등록 Transaction이 실패하더라도 등록이 될 수 있게 구현.
-ManagerService에서 logService.logAction이 성공하면 true 실패하면 false로 저장.