## SpringBoot 2.7 + MyBatis 3 + Oracle/MariaDB 기반 웹 프로젝트 템플릿 ##
본 샘플은  SpringBoot 2.7 + MyBatis 3 + Oracle/MariaDB (Maven) 기반으로  제작한 웹 프로젝트 템플릿이다.

웹 개발시 많이 사용되는 다양한 기능들 미리 구현해 두고 복사해서 사용하기 위해 제작하였다.

좀더 자세한 설명은 [여기](http://forest71.tistory.com/78)에서 얻을 수 있다.
참조한 소스 기반으로 프레임워크가 변경되었습니다.
오라클버전은 DB 전환후 일부 기능의 오류가 있을수 있습니다.

블로그에 공유된 3개의 프로젝트를 1개로 합쳤습니다.
project9 - 3> 멀티게시판
pms9 - 2) 프로젝트관리
groupware9 - 1)전자결제

### 주요 구현 기능 ###
#### 1) 전자결제 
- 일정: 월간일정
- 결제: 기안하기, 결재 받을 문서, 결재 할 문서
- 전자우편: 새메일, 받은 메일, 보낸 메일
- 게시판
#### 2) 프로젝트관리
- 과제 생성 및 개인별 할당
- 과제 상제 정보 보기(과제, 일정, 사용자 중심)
- 개인별 과제 진행 사항 입력
#### 3) 멀티 게시판, 사용자/그룹관리
- 멀티 게시판 (무한 댓글, 좋아요 등)
- 회원 기능: 모든 페이지는 회원만 접속 가능. 로그인/로그 아웃 기능 (로그 저장). 회원관리 등
- 보안 기능: 일반사용자(U)와 관리자(A)로 구분하여 일반 사용자는 관리자 페이지에 접근 불가.
- 사용자 선택: 부서, 사용자 선택 기능 (팝업)
- 날짜 선택 및 챠트 사용법 샘플
- 엑셀 다운로드(jXLS) 샘플
- 다국어 처리
- 디자인: 부트스트랩기반 반응형 웹 적용 (SB-Admin)
- 공통 에러 페이지 처리(404, 500)
- 로그 처리(logback, log4jdbc)

### 주요 LIB  ###
- JQuery-2.2.3
- CKEditor 4.5.10 
- SB-Admin 2, morris v0.5.0, DatePicker
- DynaTree 1.2.4
- jQuery EasyUI 1.4.3
- FullCalendar v5 

### 개발 환경 ###
    Programming Language - Java 1.8
    IDE - intelliJ
    DB - Oracle/MariaDB
    Framework - MyBatis, SpringBoot 2.7
    Build Tool - Maven

### 설치 ###
- OracleDB에 데이터 베이스(mts)를 생성(user_database_oracle.sql) 하고 tables_oracle.sql, tableData_oracle.sql를 실행하여 테이블과 데이터를 생성한다.
- MariaDB에 데이터 베이스(mts)를 생성(user_database_myriadb.sql) 하고 tables_myriadb.sql, tableData_myriadb.sql를 실행하여 테이블과 데이터를 생성한다.
- applicationContext.xml에 적절한 접속 정보를 입력한다.
- 톰캣이나 이클립스에서 mts를 실행
- http://localhost:9090/mts/ 로 접속
- ID/PW: admin/admin, user1/user1, user2/user2 ...
  Oracle PW소스는 변경되지 않아 PW는 1234로 입력된다.
### License ###
MIT
  
  
