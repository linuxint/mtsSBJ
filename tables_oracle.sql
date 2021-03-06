
-- start DATA CART TABLE
-- DROP TABLE BK_DATA_CART CASCADE CONSTRAINTS;
CREATE TABLE BK_DATA_CART
(
    DATA_CART_ID        VARCHAR2(50),
    USER_ID             VARCHAR2(50),
    CATG_MST_DATA_ID    VARCHAR2(100),
    CATG_MST_DATA_NM    VARCHAR2(1000),
    CATEGORY_DESC       VARCHAR2(1000),
    KEYWORD_NM          VARCHAR2(1000),
    CHARG_DEPT_CD       VARCHAR2(10),
    CAHRG_DEPT_NM       VARCHAR2(100),
    DATASTWD_ID         VARCHAR2(10),
    DATASWD_NM          VARCHAR2(100),
    VIEW_COUNT          VARCHAR2(10),
    USE_YN              VARCHAR2(1) DEFAULT 'Y',
    REG_DT              DATE DEFAULT SYSDATE,
    CONSTRAINT PK_BK_DATA_CART PRIMARY KEY (DATA_CART_ID,USER_ID)
);

COMMENT ON TABLE BK_DATA_CART IS '데이터 열람 요청 카트';
COMMENT ON COLUMN BK_DATA_CART.DATA_CART_ID       IS '데이터 열람 요청 SEQ';
COMMENT ON COLUMN BK_DATA_CART.USER_ID            IS '열람 요청자 ID';
COMMENT ON COLUMN BK_DATA_CART.CATG_MST_DATA_ID   IS '데이터 ID';
COMMENT ON COLUMN BK_DATA_CART.CATG_MST_DATA_NM   IS '데이터 명';
COMMENT ON COLUMN BK_DATA_CART.CATEGORY_DESC  IS '카테고리';
COMMENT ON COLUMN BK_DATA_CART.KEYWORD_NM         IS '키워드명';
COMMENT ON COLUMN BK_DATA_CART.CHARG_DEPT_CD      IS '데이터 소유 부서';
COMMENT ON COLUMN BK_DATA_CART.CAHRG_DEPT_NM      IS '데이터 소유 부서명';
COMMENT ON COLUMN BK_DATA_CART.DATASTWD_ID        IS '데이터 스튜어드 아이디';
COMMENT ON COLUMN BK_DATA_CART.DATASWD_NM         IS '데이터 스튜어드명';
COMMENT ON COLUMN BK_DATA_CART.VIEW_COUNT         IS '조회수';
COMMENT ON COLUMN BK_DATA_CART.USE_YN             IS '사용여부';
COMMENT ON COLUMN BK_DATA_CART.REG_DT             IS '등록일';

-- DROP SEQUENCE BK_DATA_CART_SEQ;

CREATE SEQUENCE BK_DATA_CART_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;

-- end DATA CART TABLE



-- DROP TABLE PRJ_PROJECT CASCADE CONSTRAINTS ;
CREATE TABLE PRJ_PROJECT
(
    PRNO        NUMBER(10, 0) NOT NULL, -- AUTO_INCREMENT PRNO_SEQ
    PRSTARTDATE VARCHAR2(10),
    PRENDDATE   VARCHAR2(10),
    PRTITLE     VARCHAR2(100),
    PRDATE      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    USERNO      NUMBER(10, 0),
    PRSTATUS    CHAR(1),
    DELETEFLAG  CHAR(1)      DEFAULT 'N',
    CONSTRAINT PK_PRJ_PROJECT PRIMARY KEY (PRNO)
);
COMMENT ON TABLE PRJ_PROJECT IS '프로젝트';
COMMENT ON COLUMN PRJ_PROJECT.PRNO IS '프로젝트 번호';
COMMENT ON COLUMN PRJ_PROJECT.PRSTARTDATE IS '시작일자';
COMMENT ON COLUMN PRJ_PROJECT.PRENDDATE IS '종료일자';
COMMENT ON COLUMN PRJ_PROJECT.PRTITLE IS '프로젝트 제목';
COMMENT ON COLUMN PRJ_PROJECT.PRDATE IS '작성일자';
COMMENT ON COLUMN PRJ_PROJECT.USERNO IS '사용자번호';
COMMENT ON COLUMN PRJ_PROJECT.PRSTATUS IS '상태';
COMMENT ON COLUMN PRJ_PROJECT.DELETEFLAG IS '삭제';
-- DROP SEQUENCE PRNO_SEQ;
CREATE SEQUENCE PRNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;

-- DROP TABLE PRJ_TASK CASCADE CONSTRAINTS;
CREATE TABLE PRJ_TASK
(
    PRNO        NUMBER(10, 0),
    TSNO        NUMBER(19, 0) NOT NULL, --AUTO_INCREMENT TSNO_SEQ
    TSPARENT    NUMBER(19, 0),
    TSSORT      NUMBER(7, 0),
    TSTITLE     VARCHAR2(100),
    TSSTARTDATE VARCHAR2(10),
    TSENDDATE   VARCHAR2(10),
    TSENDREAL   VARCHAR2(10),
    TSRATE      NUMBER(5, 0),
    OLDNO       NUMBER(19, 0),
    DELETEFLAG  CHAR(1) DEFAULT 'N',
    CONSTRAINT PK_PRJ_TASK PRIMARY KEY (TSNO)
);
COMMENT ON TABLE PRJ_TASK IS '프로젝트 업무';
COMMENT ON COLUMN PRJ_TASK.PRNO IS '프로젝트 번호';
COMMENT ON COLUMN PRJ_TASK.TSNO IS '업무번호';
COMMENT ON COLUMN PRJ_TASK.TSPARENT IS '부모업무번호';
COMMENT ON COLUMN PRJ_TASK.TSSORT IS '정렬';
COMMENT ON COLUMN PRJ_TASK.TSTITLE IS '업무 제목';
COMMENT ON COLUMN PRJ_TASK.TSSTARTDATE IS '시작일자';
COMMENT ON COLUMN PRJ_TASK.TSENDDATE IS '종료일자';
COMMENT ON COLUMN PRJ_TASK.TSENDREAL IS '종료일자(실제)';
COMMENT ON COLUMN PRJ_TASK.TSRATE IS '진행율';
COMMENT ON COLUMN PRJ_TASK.OLDNO IS '이전업무번호';
COMMENT ON COLUMN PRJ_TASK.DELETEFLAG IS '삭제';
-- DROP SEQUENCE TSNO_SEQ;
CREATE SEQUENCE TSNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;


-- DROP TABLE PRJ_TASKUSER CASCADE CONSTRAINTS;
CREATE TABLE PRJ_TASKUSER
(
    TSNO   NUMBER(19, 0) NOT NULL,
    USERNO NUMBER(10, 0),
    CONSTRAINT PK_PRJ_TASKUSER PRIMARY KEY (TSNO, USERNO)
);
COMMENT ON TABLE PRJ_TASKUSER IS '업무할당';
COMMENT ON COLUMN PRJ_TASKUSER.TSNO IS '업무번호';
COMMENT ON COLUMN PRJ_TASKUSER.USERNO IS '사용자번호';



-- DROP TABLE PRJ_TASKFILE CASCADE CONSTRAINTS;
CREATE TABLE PRJ_TASKFILE
(
    TSNO     NUMBER(19, 0),
    FILENO   NUMBER(10, 0) NOT NULL, -- AUTO_INCREMENT FILENO_TASK_SEQ
    FILENAME VARCHAR2(100),
    REALNAME VARCHAR2(30),
    FILESIZE NUMBER(10, 0),
    CONSTRAINT PK_PRJ_TASKFILE PRIMARY KEY (FILENO)
);
COMMENT ON TABLE PRJ_TASKFILE IS '첨부파일';
COMMENT ON COLUMN PRJ_TASKFILE.TSNO IS '업무번호';
COMMENT ON COLUMN PRJ_TASKFILE.FILENO IS '파일번호';
COMMENT ON COLUMN PRJ_TASKFILE.FILENAME IS '파일명';
COMMENT ON COLUMN PRJ_TASKFILE.REALNAME IS '실제파일명';
COMMENT ON COLUMN PRJ_TASKFILE.FILESIZE IS '파일크기';
CREATE SEQUENCE FILENO_TASK_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;

-- ------------------------- Project 9 ---------------------------
-- CODE TABLE V1
-- DROP TABLE COM_CODE CASCADE CONSTRAINTS;
CREATE TABLE COM_CODE
(
    CLASSNO NUMBER(10, 0) NOT NULL, -- 대분류
    CODECD  VARCHAR2(10)  NOT NULL, -- 코드
    CODENM  VARCHAR2(30),           -- 코드명
    CONSTRAINT PK_COM_CODE PRIMARY KEY (CLASSNO, CODECD)
);
COMMENT ON TABLE COM_CODE IS '공통코드';
COMMENT ON COLUMN COM_CODE.CLASSNO IS '그릅코드';
COMMENT ON COLUMN COM_CODE.CODECD IS '코드번호';
COMMENT ON COLUMN COM_CODE.CODENM IS '코드명';

-- CODE TABLE V2
-- DROP TABLE COM_CODE_V2 CASCADE CONSTRAINTS;
CREATE TABLE COM_CODE_V2
(
    CODECD     VARCHAR2(5),         -- 코드
    PCODECD    VARCHAR2(5),         -- 코드시컨스
    CODENM     VARCHAR2(40),        -- 코드명
    CODEDESC   VARCHAR2(100),       -- 코드설명
    DELETEFLAG CHAR(1) DEFAULT 'N', -- 사용여부
    CONSTRAINT COM_CODE_V2_PK PRIMARY KEY (CODECD),
    CONSTRAINT COM_CODE_V2_UK UNIQUE      (CODECD, PCODECD),
    CONSTRAINT COM_CODE_V2_FK FOREIGN KEY (PCODECD) REFERENCES COM_CODE_V2(CODECD)

);
COMMENT ON TABLE COM_CODE_V2 IS '공통코드';
COMMENT ON COLUMN COM_CODE_V2.CODECD     IS '코드';
COMMENT ON COLUMN COM_CODE_V2.PCODECD    IS '상위코드';
COMMENT ON COLUMN COM_CODE_V2.CODENM     IS '코드명';
COMMENT ON COLUMN COM_CODE_V2.CODEDESC   IS '코드설명';
COMMENT ON COLUMN COM_CODE_V2.DELETEFLAG IS '사용여부';


-- DROP TABLE COM_DEPT CASCADE CONSTRAINTS ;
CREATE TABLE COM_DEPT
(
    DEPTNO     NUMBER(10, 0),             -- 부서 번호
    DEPTNM     VARCHAR2(20),              -- 부서명
    PARENTNO   NUMBER(10, 0) DEFAULT 0,   -- 부모 부서
    DELETEFLAG CHAR(1)       DEFAULT 'N', -- 삭제 여부
    CONSTRAINT PK_COM_DEPT PRIMARY KEY (DEPTNO)
);
COMMENT ON TABLE COM_DEPT IS '그룹 부서';
COMMENT ON COLUMN COM_DEPT.DEPTNO IS '부서 번호';
COMMENT ON COLUMN COM_DEPT.DEPTNM IS '부서명';
COMMENT ON COLUMN COM_DEPT.PARENTNO IS '부모 부서';
COMMENT ON COLUMN COM_DEPT.DELETEFLAG IS '삭제 여부';
-- DROP SEQUENCE DEPTNO_SEQ;
CREATE SEQUENCE DEPTNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;

-- DROP TABLE COM_USER CASCADE CONSTRAINTS ;
CREATE TABLE COM_USER
(
    USERNO     NUMBER(10, 0) NOT NULL, -- 사용자 번호 AUTO_INCREMENT USERNO_SEQ
    USERID     VARCHAR2(20),           -- ID
    USERNM     VARCHAR2(20),           -- 이름
    USERPW     VARCHAR2(100),          -- 비밀번호
    USERROLE   CHAR(1),                -- 권한
    PHOTO      VARCHAR2(50),           -- 사진
    DEPTNO     NUMBER(10, 0),
    DELETEFLAG CHAR(1) DEFAULT 'N',    -- 삭제 여부,
    USERPOS    VARCHAR2(2),
    CONSTRAINT PK_COM_USER PRIMARY KEY (USERNO)
);
--ALTER TABLE COM_USER ADD USERPOS VARCHAR2(2);
COMMENT ON TABLE COM_USER IS '사용자';
COMMENT ON COLUMN COM_USER.USERNO IS '사용자 번호';
COMMENT ON COLUMN COM_USER.USERID IS 'ID';
COMMENT ON COLUMN COM_USER.USERNM IS '이름';
COMMENT ON COLUMN COM_USER.USERPW IS '비밀번호';
COMMENT ON COLUMN COM_USER.USERROLE IS '권한';
COMMENT ON COLUMN COM_USER.PHOTO IS '사진';
COMMENT ON COLUMN COM_USER.DEPTNO IS '부서번호';
COMMENT ON COLUMN COM_USER.DELETEFLAG IS '삭제 여부';
COMMENT ON COLUMN COM_USER.USERPOS IS '직위';
-- DROP SEQUENCE USERNO_SEQ;
CREATE SEQUENCE USERNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;
/*------------------------------------------*/

-- DROP TABLE TBL_BOARD CASCADE CONSTRAINTS ;
CREATE TABLE TBL_BOARD
(
    BGNO          NUMBER(10, 0) DEFAULT 0,                 -- 게시판 그룹번호
    BRDNO         NUMBER(10, 0) NOT NULL,                  -- 글 번호 AUTO_INCREMENT BRDNO_SEQ
    BRDTITLE      VARCHAR2(255),                           -- 글 제목
    USERNO        NUMBER(10, 0),                           -- 작성자
    BRDMEMO       VARCHAR2(4000),                          -- 글 내용
    BRDDATE       TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP, -- 작성일자
    BRDNOTICE     CHAR(1),                                 -- 공지사항여부
    LASTDATE      TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP,
    LASTUSERNO    NUMBER(10, 0),
    BRDLIKE       NUMBER(10, 0) DEFAULT 0,                 -- 좋아요
    BRDDELETEFLAG CHAR(1)       DEFAULT 'N',               -- 삭제 여부
    ETC1          VARCHAR2(200),
    ETC2          VARCHAR2(200),
    ETC3          VARCHAR2(200),
    ETC4          VARCHAR2(200),
    ETC5          VARCHAR2(200),
    CONSTRAINT PK_TBL_BOARD PRIMARY KEY (BRDNO)
);
COMMENT ON TABLE TBL_BOARD IS '게시판';
COMMENT ON COLUMN TBL_BOARD.BGNO IS '게시판 그룹번호';
COMMENT ON COLUMN TBL_BOARD.BRDNO IS '글 번호';
COMMENT ON COLUMN TBL_BOARD.BRDTITLE IS '글 제목';
COMMENT ON COLUMN TBL_BOARD.USERNO IS '작성자';
COMMENT ON COLUMN TBL_BOARD.BRDMEMO IS '글 내용';
COMMENT ON COLUMN TBL_BOARD.BRDDATE IS '작성일자';
COMMENT ON COLUMN TBL_BOARD.BRDNOTICE IS '공지사항여부';
COMMENT ON COLUMN TBL_BOARD.LASTDATE IS '마지막 수정일';
COMMENT ON COLUMN TBL_BOARD.LASTUSERNO IS '마지막 수정자';
COMMENT ON COLUMN TBL_BOARD.BRDLIKE IS '좋아요';
COMMENT ON COLUMN TBL_BOARD.BRDDELETEFLAG IS '삭제 여부';
COMMENT ON COLUMN TBL_BOARD.ETC1 IS '기타1';
COMMENT ON COLUMN TBL_BOARD.ETC2 IS '기타2';
COMMENT ON COLUMN TBL_BOARD.ETC3 IS '기타3';
COMMENT ON COLUMN TBL_BOARD.ETC4 IS '기타4';
COMMENT ON COLUMN TBL_BOARD.ETC5 IS '기타5';

-- DROP SEQUENCE BRDNO_SEQ;
CREATE SEQUENCE BRDNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;

-- DROP TABLE TBL_BOARDFILE CASCADE CONSTRAINTS ;
CREATE TABLE TBL_BOARDFILE
(
    FILENO   NUMBER(10, 0) NOT NULL,  -- 파일 번호 AUTO_INCREMENT FILENO_SEQ
    BRDNO    NUMBER(10, 0) DEFAULT 0, -- 글번호
    FILENAME VARCHAR2(100),           -- 파일명
    REALNAME VARCHAR2(30),            -- 실제파일명
    FILESIZE NUMBER(10, 0),           -- 파일 크기
    CONSTRAINT PK_TBL_BOARDFILE PRIMARY KEY (FILENO)
);
COMMENT ON TABLE TBL_BOARDFILE IS '게시판 첨부파일';
COMMENT ON COLUMN TBL_BOARDFILE.FILENO IS '파일 번호';
COMMENT ON COLUMN TBL_BOARDFILE.BRDNO IS '글번호';
COMMENT ON COLUMN TBL_BOARDFILE.FILENAME IS '파일명';
COMMENT ON COLUMN TBL_BOARDFILE.REALNAME IS '실제파일명';
COMMENT ON COLUMN TBL_BOARDFILE.FILESIZE IS '파일 크기';

-- DROP SEQUENCE FILENO_SEQ;
CREATE SEQUENCE FILENO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;

-- DROP TABLE TBL_BOARDLIKE CASCADE CONSTRAINTS;
CREATE TABLE TBL_BOARDLIKE
(
    BLNO   NUMBER(10, 0) NOT NULL,                  -- 번호 AUTO_INCREMENT BLNO_SEQ
    BRDNO  NUMBER(10, 0) DEFAULT 0,                 -- 글번호
    USERNO NUMBER(10, 0),                           -- 작성자
    BLDATE TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP, -- 일자
    CONSTRAINT PK_TBL_BOARDLIKE PRIMARY KEY (BLNO)
);
COMMENT ON TABLE TBL_BOARDLIKE IS '게시판 좋아요';
COMMENT ON COLUMN TBL_BOARDLIKE.BLNO IS '번호';
COMMENT ON COLUMN TBL_BOARDLIKE.BRDNO IS '글번호';
COMMENT ON COLUMN TBL_BOARDLIKE.USERNO IS '작성자';
COMMENT ON COLUMN TBL_BOARDLIKE.BLDATE IS '등록일';
-- DROP SEQUENCE BLNO_SEQ;
CREATE SEQUENCE BLNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;


-- DROP TABLE TBL_BOARDREPLY CASCADE CONSTRAINTS ;
CREATE TABLE TBL_BOARDREPLY
(
    BRDNO         NUMBER(10, 0) NOT NULL,                  -- 게시물 번호
    RENO          NUMBER(10, 0) NOT NULL,                  -- 댓글 번호
    USERNO        NUMBER(10, 0),                           -- 작성자
    REMEMO        VARCHAR2(500) DEFAULT NULL,              -- 댓글내용
    REPARENT      NUMBER(10, 0) DEFAULT 0,                 -- 부모 댓글
    REDEPTH       NUMBER(10, 0),                           -- 깊이
    REORDER       NUMBER(10, 0),                           -- 순서
    REDATE        TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP, -- 작성일자
    REDELETEFLAG  CHAR(1) DEFAULT 'N',                  -- 삭제여부
    LASTDATE      TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP,
    LASTUSERNO    NUMBER(10, 0),
    REDELETEDATE  TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP,
    BRDDELETEDATE TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_TBL_BOARDREPLY PRIMARY KEY (RENO)
);
COMMENT ON TABLE TBL_BOARDREPLY IS '게시판 좋아요';
COMMENT ON COLUMN TBL_BOARDREPLY.BRDNO IS '게시물 번호';
COMMENT ON COLUMN TBL_BOARDREPLY.RENO IS '댓글 번호';
COMMENT ON COLUMN TBL_BOARDREPLY.USERNO IS '작성자';
COMMENT ON COLUMN TBL_BOARDREPLY.REMEMO IS '댓글내용';
COMMENT ON COLUMN TBL_BOARDREPLY.REPARENT IS '부모 댓글';
COMMENT ON COLUMN TBL_BOARDREPLY.REDEPTH IS '깊이';
COMMENT ON COLUMN TBL_BOARDREPLY.REORDER IS '순서';
COMMENT ON COLUMN TBL_BOARDREPLY.REDATE IS '작성일자';
COMMENT ON COLUMN TBL_BOARDREPLY.REDELETEFLAG IS '삭제여부';
COMMENT ON COLUMN TBL_BOARDREPLY.LASTDATE IS '마지막 수정일';
COMMENT ON COLUMN TBL_BOARDREPLY.LASTUSERNO IS '미자막 수정자';
--미사용 -- DROP SEQUENCE RENO_SEQ;
CREATE SEQUENCE RENO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;

-- DROP TABLE TBL_BOARDREAD CASCADE CONSTRAINTS ;
CREATE TABLE TBL_BOARDREAD
(
    BRDNO    NUMBER(10, 0) NOT NULL,                 -- 게시물 번호
    USERNO   NUMBER(10, 0) NOT NULL,                 -- 작성자
    READDATE TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP, -- 작성일자
    CONSTRAINT PK_TBL_BOARDREAD PRIMARY KEY (BRDNO, USERNO)
);
COMMENT ON TABLE TBL_BOARDREAD IS '게시판읽기';
COMMENT ON COLUMN TBL_BOARDREAD.BRDNO IS '게시물 번호';
COMMENT ON COLUMN TBL_BOARDREAD.USERNO IS '작성자';
COMMENT ON COLUMN TBL_BOARDREAD.READDATE IS '작성일자';

-- DROP TABLE TBL_BOARDGROUP CASCADE CONSTRAINTS ;
CREATE TABLE TBL_BOARDGROUP
(
    BGNO         NUMBER(10, 0) NOT NULL,  -- 게시판 그룹번호 AUTO_INCREMENT BGNO_SEQ
    BGNAME       VARCHAR2(50),            -- 게시판 그룹명
    BGPARENT     NUMBER(10, 0) DEFAULT 0, -- 게시판 그룹 부모
    BGDELETEFLAG CHAR(1) DEFAULT 'N',     -- 삭제 여부
    BGUSED       CHAR(1),                 -- 사용 여부
    BGREPLY      CHAR(1),                 -- 댓글 사용여부
    BGREADONLY   CHAR(1),                 -- 글쓰기 가능 여부
    BGNOTICE     CHAR(1),                 -- 공지 쓰기  가능 여부
    BGDATE       TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP, -- 생성일자
    CONSTRAINT PK_TBL_BOARDGROUP PRIMARY KEY (BGNO)
);
COMMENT ON TABLE TBL_BOARDGROUP IS '게시판 그룹';
COMMENT ON COLUMN TBL_BOARDGROUP.BGNO IS '게시판 그룹번호';
COMMENT ON COLUMN TBL_BOARDGROUP.BGNAME IS '게시판 그룹명';
COMMENT ON COLUMN TBL_BOARDGROUP.BGPARENT IS '게시판 그룹 부모';
COMMENT ON COLUMN TBL_BOARDGROUP.BGDELETEFLAG IS '삭제 여부';
COMMENT ON COLUMN TBL_BOARDGROUP.BGUSED IS '사용 여부';
COMMENT ON COLUMN TBL_BOARDGROUP.BGREPLY IS '댓글 사용여부';
COMMENT ON COLUMN TBL_BOARDGROUP.BGREADONLY IS '글쓰기 가능 여부';
COMMENT ON COLUMN TBL_BOARDGROUP.BGNOTICE IS '공지 쓰기  가능 여부';
COMMENT ON COLUMN TBL_BOARDGROUP.BGDATE IS '생성일자';
-- DROP SEQUENCE BGNO_SEQ;
CREATE SEQUENCE BGNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;

--DROP TABLE COM_LOGINOUT CASCADE CONSTRAINTS;
CREATE TABLE COM_LOGINOUT
(
    LNO    NUMBER(10, 0) NOT NULL, -- 순번 AUTO_INCREMENT LNO_SEQ
    USERNO NUMBER(10, 0),          -- 로그인 사용자
    LTYPE  CHAR(1),                -- in / out
    LDATE  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP, -- 발생일자
    CONSTRAINT PK_COM_LOGINOUT PRIMARY KEY (LNO)
);
COMMENT ON TABLE COM_LOGINOUT IS '로그인/로그아웃';
COMMENT ON COLUMN COM_LOGINOUT.LNO IS '순번';
COMMENT ON COLUMN COM_LOGINOUT.USERNO IS '로그인 사용자';
COMMENT ON COLUMN COM_LOGINOUT.LTYPE IS '로그인/로그아웃구분';
COMMENT ON COLUMN COM_LOGINOUT.LDATE IS '발생일자';

--DROP SEQUENCE LNO_SEQ;
CREATE SEQUENCE LNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;



-- DROP TABLE TBL_CRUD CASCADE CONSTRAINTS ;
CREATE TABLE TBL_CRUD
(
    CRNO         NUMBER(10, 0) NOT NULL,                 -- 번호 AUTO_INCREMENT CRNO_SEQ
    CRTITLE      VARCHAR2(255),                          -- 제목
    USERNO       NUMBER(10, 0),                          -- 작성자
    CRMEMO       VARCHAR2(4000),                         -- 내용
    CRDATE       TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP, -- 작성일자
    CRDELETEFLAG CHAR(1) DEFAULT 'N',               -- 삭제 여부
    CONSTRAINT PK_TBL_CRUD PRIMARY KEY (CRNO)
);
COMMENT ON TABLE TBL_CRUD IS '테이블 CRUD';
COMMENT ON COLUMN TBL_CRUD.CRNO IS '번호';
COMMENT ON COLUMN TBL_CRUD.CRTITLE IS '제목';
COMMENT ON COLUMN TBL_CRUD.USERNO IS '작성자';
COMMENT ON COLUMN TBL_CRUD.CRMEMO IS '내용';
COMMENT ON COLUMN TBL_CRUD.CRDATE IS '작성일자';
COMMENT ON COLUMN TBL_CRUD.CRDELETEFLAG IS '삭제 여부';

-- DROP SEQUENCE CRNO_SEQ;
CREATE SEQUENCE CRNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;


-- 날짜
-- DROP TABLE COM_DATE CASCADE CONSTRAINTS ;
CREATE TABLE COM_DATE
(
    CDNO          NUMBER(19, 0) NOT NULL, -- AUTO_INCREMENT CDNO_SEQ
    CDDATE        CHAR(10),
    CDYEAR        CHAR(4),
    CDMM          CHAR(2),
    CDDD          CHAR(2),
    CDWEEKOFYEAR  NUMBER(5, 0),
    CDWEEKOFMONTH NUMBER(5, 0),
    CDWEEK        NUMBER(5, 0),
    CDDAYOFWEEK   NUMBER(5, 0),
    CONSTRAINT PK_COM_DATE PRIMARY KEY (CDNO)
);
COMMENT ON TABLE COM_DATE IS '날짜';
COMMENT ON COLUMN COM_DATE.CDNO IS '번호';
COMMENT ON COLUMN COM_DATE.CDDATE IS '날짜';
COMMENT ON COLUMN COM_DATE.CDYEAR IS '년도';
COMMENT ON COLUMN COM_DATE.CDMM IS '월';
COMMENT ON COLUMN COM_DATE.CDDD IS '일';
COMMENT ON COLUMN COM_DATE.CDWEEKOFYEAR IS '연별주차';
COMMENT ON COLUMN COM_DATE.CDWEEKOFMONTH IS '월별주차';
COMMENT ON COLUMN COM_DATE.CDWEEK IS '주';
COMMENT ON COLUMN COM_DATE.CDDAYOFWEEK IS '주별일자';
-- DROP SEQUENCE CDNO_SEQ;
CREATE SEQUENCE CDNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;


-- 메일주소
-- DROP TABLE EML_ADDRESS CASCADE CONSTRAINTS;
CREATE TABLE EML_ADDRESS
(
    EMNO      NUMBER(10, 0) NOT NULL,
    EASEQ     NUMBER(10, 0) NOT NULL,
    EATYPE    CHAR(1)       NOT NULL,
    EAADDRESS VARCHAR2(150),
    CONSTRAINT PK_EML_ADDRESS PRIMARY KEY (EMNO, EASEQ, EATYPE)
);
COMMENT ON TABLE EML_ADDRESS IS '메일주소';
COMMENT ON COLUMN EML_ADDRESS.EMNO IS '메일번호';
COMMENT ON COLUMN EML_ADDRESS.EASEQ IS '순번';
COMMENT ON COLUMN EML_ADDRESS.EATYPE IS '주소종류';
COMMENT ON COLUMN EML_ADDRESS.EAADDRESS IS '메일주소';


-- 메일
-- DROP TABLE EML_MAIL CASCADE CONSTRAINTS ;
CREATE TABLE EML_MAIL
(
    EMNO       NUMBER(10, 0) NOT NULL, -- AUTO_INCREMENT EMNO_SEQ
    EMTYPE     CHAR(1),
    EMSUBJECT  VARCHAR2(150),
    EMFROM     VARCHAR2(150),
    EMCONTENTS VARCHAR2(4000),
    ENTRYDATE  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    USERNO     NUMBER(10, 0) NOT NULL,
    EMINO      NUMBER(10, 0) NOT NULL,
    DELETEFLAG CHAR(1) DEFAULT 'N',
    CONSTRAINT PK_EML_MAIL PRIMARY KEY (EMNO)
);
CREATE INDEX EML_MAIL_INX01 ON EML_MAIL (EMTYPE ASC, USERNO ASC, EMINO ASC);
COMMENT ON TABLE EML_MAIL IS '메일주소';
COMMENT ON COLUMN EML_MAIL.EMNO IS '메일번호';
COMMENT ON COLUMN EML_MAIL.EMTYPE IS '메일종류';
COMMENT ON COLUMN EML_MAIL.EMSUBJECT IS '제목';
COMMENT ON COLUMN EML_MAIL.EMFROM IS '보낸사람';
COMMENT ON COLUMN EML_MAIL.EMCONTENTS IS '내용';
COMMENT ON COLUMN EML_MAIL.ENTRYDATE IS '작성일';
COMMENT ON COLUMN EML_MAIL.USERNO IS '사용자번호';
COMMENT ON COLUMN EML_MAIL.EMINO IS '메일정보번호';
COMMENT ON COLUMN EML_MAIL.DELETEFLAG IS '삭제';
-- DROP SEQUENCE EMNO_SEQ;
CREATE SEQUENCE EMNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;



-- 첨부파일
-- DROP TABLE EML_MAILFILE CASCADE CONSTRAINTS ;
CREATE TABLE EML_MAILFILE
(
    FILENO   NUMBER(10, 0) NOT NULL, -- AUTO_INCREMENT FILENO_EML_SEQ
    FILENAME VARCHAR2(100),
    REALNAME VARCHAR2(30),
    FILESIZE NUMBER(10, 0),
    EMNO     NUMBER(10, 0) NOT NULL,
    CONSTRAINT PK_EML_MAILFILE PRIMARY KEY (FILENO)
);
COMMENT ON TABLE EML_MAILFILE IS '첨부파일';
COMMENT ON COLUMN EML_MAILFILE.FILENO IS '파일번호';
COMMENT ON COLUMN EML_MAILFILE.FILENAME IS '파일명';
COMMENT ON COLUMN EML_MAILFILE.REALNAME IS '실제파일명';
COMMENT ON COLUMN EML_MAILFILE.FILESIZE IS '파일크기';
COMMENT ON COLUMN EML_MAILFILE.EMNO IS '메일번호';
-- DROP SEQUENCE FILENO_EML_SEQ;
CREATE SEQUENCE FILENO_EML_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;


-- 메일정보
-- DROP TABLE EML_MAILINFO CASCADE CONSTRAINTS ;
CREATE TABLE EML_MAILINFO
(
    EMINO       NUMBER(10, 0) NOT NULL, -- AUTO_INCREMENT EMINO_SEQ
    EMIIMAP     VARCHAR2(30),
    EMIIMAPPORT VARCHAR2(5),
    EMISMTP     VARCHAR2(30),
    EMISMTPPORT VARCHAR2(5),
    EMIUSER     VARCHAR2(50),
    EMIPW       VARCHAR2(50),
    USERNO      NUMBER(10, 0) NOT NULL,
    ENTRYDATE   TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    DELETEFLAG  CHAR(1) DEFAULT 'N',
    CONSTRAINT PK_EML_MAILINFO PRIMARY KEY (EMINO)
);
COMMENT ON TABLE EML_MAILINFO IS '메일정보';
COMMENT ON COLUMN EML_MAILINFO.EMINO IS '메일정보번호';
COMMENT ON COLUMN EML_MAILINFO.EMIIMAP IS 'IMAP서버주소';
COMMENT ON COLUMN EML_MAILINFO.EMIIMAPPORT IS 'IMAP서버포트';
COMMENT ON COLUMN EML_MAILINFO.EMISMTP IS 'SMTP 서버주소';
COMMENT ON COLUMN EML_MAILINFO.EMISMTPPORT IS 'SMTP 서버포트';
COMMENT ON COLUMN EML_MAILINFO.EMIUSER IS '계정';
COMMENT ON COLUMN EML_MAILINFO.EMIPW IS '비밀번호';
COMMENT ON COLUMN EML_MAILINFO.USERNO IS '사용자번호';
COMMENT ON COLUMN EML_MAILINFO.ENTRYDATE IS '등록일자';
COMMENT ON COLUMN EML_MAILINFO.DELETEFLAG IS '삭제';
-- DROP SEQUENCE EMINO_SEQ;
CREATE SEQUENCE EMINO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;



-- 일정상세
-- DROP TABLE SCH_DETAIL CASCADE CONSTRAINTS ;
CREATE TABLE SCH_DETAIL
(
    SSNO     NUMBER(10, 0) NOT NULL,
    SDSEQ    NUMBER(5, 0)  NOT NULL,
    SDDATE   TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    SDHOUR   CHAR(2),
    SDMINUTE CHAR(2),
    CONSTRAINT PK_SCH_DETAIL PRIMARY KEY (SSNO, SDSEQ)
);
COMMENT ON TABLE SCH_DETAIL IS '일정상세';
COMMENT ON COLUMN SCH_DETAIL.SSNO IS '일정번호';
COMMENT ON COLUMN SCH_DETAIL.SDSEQ IS '순번';
COMMENT ON COLUMN SCH_DETAIL.SDDATE IS '날짜';
COMMENT ON COLUMN SCH_DETAIL.SDHOUR IS '시간';
COMMENT ON COLUMN SCH_DETAIL.SDMINUTE IS '분';


-- 공휴일
-- DROP TABLE SCH_HOLIDAY CASCADE CONSTRAINTS ;
CREATE TABLE SCH_HOLIDAY
(
    SHNO       NUMBER(5, 0) NOT NULL, -- AUTO_INCREMENT SHNO_SEQ
    SHTITLE    VARCHAR2(20),
    SHMONTH    NUMBER(5, 0),
    SHDATE     NUMBER(5, 0),
    SHCOLOR    VARCHAR2(10),
    DELETEFLAG CHAR(1) DEFAULT 'N',
    CONSTRAINT PK_SCH_HOLIDAY PRIMARY KEY (SHNO)
);
COMMENT ON TABLE SCH_HOLIDAY IS '공휴일';
COMMENT ON COLUMN SCH_HOLIDAY.SHNO IS '번호';
COMMENT ON COLUMN SCH_HOLIDAY.SHTITLE IS '공휴일명';
COMMENT ON COLUMN SCH_HOLIDAY.SHMONTH IS '월';
COMMENT ON COLUMN SCH_HOLIDAY.SHDATE IS '일';
COMMENT ON COLUMN SCH_HOLIDAY.SHCOLOR IS '색상';
COMMENT ON COLUMN SCH_HOLIDAY.DELETEFLAG IS '삭제';
-- DROP SEQUENCE SHNO_SEQ;
CREATE SEQUENCE SHNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;


-- 일정 - 컬럼명 SS->SCH
-- DROP TABLE SCH_SCHEDULE CASCADE CONSTRAINTS;
CREATE TABLE SCH_SCHEDULE
(
    SSNO           NUMBER(10, 0) NOT NULL, -- AUTO_INCREMENT SSNO_SEQ
    SSTITLE        VARCHAR2(50),
    SSTYPE         CHAR(1),
    SSSTARTDATE    CHAR(10),
    SSSTARTHOUR    CHAR(2),
    SSSTARTMINUTE  CHAR(2),
    SSENDDATE      VARCHAR2(10),
    SSENDHOUR      CHAR(2),
    SSENDMINUTE    CHAR(2),
    SSREPEATTYPE   CHAR(1),
    SSREPEATOPTION VARCHAR2(2),
    SSREPEATEND    VARCHAR2(10),
    SSCONTENTS     VARCHAR2(4000),
    SSISOPEN       CHAR(1),
    UPDATEDATE     TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    INSERTDATE     TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    USERNO         NUMBER(10, 0) NOT NULL,
    DELETEFLAG     CHAR(1) DEFAULT 'N',
    CONSTRAINT PK_SCH_SCHEDULE PRIMARY KEY (SSNO)
);
COMMENT ON TABLE SCH_SCHEDULE IS '일정';
COMMENT ON COLUMN SCH_SCHEDULE.SSNO IS '일정번호';
COMMENT ON COLUMN SCH_SCHEDULE.SSTITLE IS '일정명';
COMMENT ON COLUMN SCH_SCHEDULE.SSTYPE IS '구분';
COMMENT ON COLUMN SCH_SCHEDULE.SSSTARTDATE IS '시작일';
COMMENT ON COLUMN SCH_SCHEDULE.SSSTARTHOUR IS '시작일-시간';
COMMENT ON COLUMN SCH_SCHEDULE.SSSTARTMINUTE IS '시작일-분';
COMMENT ON COLUMN SCH_SCHEDULE.SSENDDATE IS '종료일';
COMMENT ON COLUMN SCH_SCHEDULE.SSENDHOUR IS '종료일-시간';
COMMENT ON COLUMN SCH_SCHEDULE.SSENDMINUTE IS '종료일-분';
COMMENT ON COLUMN SCH_SCHEDULE.SSREPEATTYPE IS '반복';
COMMENT ON COLUMN SCH_SCHEDULE.SSREPEATOPTION IS '반복옵션';
COMMENT ON COLUMN SCH_SCHEDULE.SSREPEATEND IS '반복종료';
COMMENT ON COLUMN SCH_SCHEDULE.SSCONTENTS IS '내용';
COMMENT ON COLUMN SCH_SCHEDULE.SSISOPEN IS '공개여부';
COMMENT ON COLUMN SCH_SCHEDULE.UPDATEDATE IS '수정일자';
COMMENT ON COLUMN SCH_SCHEDULE.INSERTDATE IS '작성일자';
COMMENT ON COLUMN SCH_SCHEDULE.USERNO IS '사용자번호';
COMMENT ON COLUMN SCH_SCHEDULE.DELETEFLAG IS '삭제';
-- DROP SEQUENCE SSNO_SEQ;
CREATE SEQUENCE SSNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;


-- 결재문서
-- DROP TABLE SGN_DOC CASCADE CONSTRAINTS ;
CREATE TABLE SGN_DOC
(
    DOCNO       NUMBER(20, 0) NOT NULL, -- AUTO_INCREMENT DOCNO_SEQ
    DOCTITLE    VARCHAR2(50),
    DOCCONTENTS VARCHAR2(4000),
    DELETEFLAG  CHAR(1) DEFAULT 'N',
    DOCSTATUS   CHAR(1),
    DOCSTEP     NUMBER(5, 0),
    DTNO        NUMBER(10, 0) NOT NULL,
    UPDATEDATE  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    INSERTDATE  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    USERNO      NUMBER(10, 0) NOT NULL,
    DEPTNM      VARCHAR2(20),
    DOCSIGNPATH VARCHAR2(200),
    CONSTRAINT PK_SGN_DOC PRIMARY KEY (DOCNO)
);
COMMENT ON TABLE SGN_DOC IS '결재문서';
COMMENT ON COLUMN SGN_DOC.DOCNO IS '문서번호';
COMMENT ON COLUMN SGN_DOC.DOCTITLE IS '제목';
COMMENT ON COLUMN SGN_DOC.DOCCONTENTS IS '문서내용';
COMMENT ON COLUMN SGN_DOC.DELETEFLAG IS '삭제여부';
COMMENT ON COLUMN SGN_DOC.DOCSTATUS IS '문서상태';
COMMENT ON COLUMN SGN_DOC.DOCSTEP IS '결재단계';
COMMENT ON COLUMN SGN_DOC.DTNO IS '문서양식번호';
COMMENT ON COLUMN SGN_DOC.UPDATEDATE IS '수정일자';
COMMENT ON COLUMN SGN_DOC.INSERTDATE IS '작성일자';
COMMENT ON COLUMN SGN_DOC.USERNO IS '사용자번호';
COMMENT ON COLUMN SGN_DOC.DEPTNM IS '부서명';
COMMENT ON COLUMN SGN_DOC.DOCSIGNPATH IS '결재경로문자열';
-- DROP SEQUENCE DOCNO_SEQ;
CREATE SEQUENCE DOCNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;


-- 첨부파일
-- DROP TABLE SGN_DOCFILE CASCADE CONSTRAINTS ;
CREATE TABLE SGN_DOCFILE
(
    FILENO     NUMBER(10, 0) NOT NULL, -- AUTO_INCREMENT FILENO_SGN_SEQ
    FILENAME   VARCHAR2(100),
    REALNAME   VARCHAR2(30),
    FILESIZE   NUMBER(10, 0),
    DOCNO      NUMBER(20, 0) NOT NULL,
    CONSTRAINT PK_SGN_DOCFILE PRIMARY KEY (FILENO)
);
COMMENT ON TABLE SGN_DOCFILE IS '첨부파일';
COMMENT ON COLUMN SGN_DOCFILE.FILENO IS '파일번호';
COMMENT ON COLUMN SGN_DOCFILE.FILENAME IS '파일명';
COMMENT ON COLUMN SGN_DOCFILE.REALNAME IS '실제파일명';
COMMENT ON COLUMN SGN_DOCFILE.FILESIZE IS '파일크기';
COMMENT ON COLUMN SGN_DOCFILE.DOCNO IS '문서번호';
-- DROP SEQUENCE FILENO_SGN_SEQ;
CREATE SEQUENCE FILENO_SGN_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;

-- 문서양식종류
-- DROP TABLE SGN_DOCTYPE CASCADE CONSTRAINTS;
CREATE TABLE SGN_DOCTYPE
(
    DTNO       NUMBER(10, 0) NOT NULL, -- AUTO_INCREMENT DTNO_SEQ
    DTTITLE    VARCHAR2(30),
    DTCONTENTS VARCHAR2(4000),
    DELETEFLAG CHAR(1) DEFAULT 'N',
    CONSTRAINT PK_SGN_DOCTYPE PRIMARY KEY (DTNO)
);
COMMENT ON TABLE SGN_DOCTYPE IS '문서양식종류';
COMMENT ON COLUMN SGN_DOCTYPE.DTNO IS '문서양식번호';
COMMENT ON COLUMN SGN_DOCTYPE.DTTITLE IS '문서양식명';
COMMENT ON COLUMN SGN_DOCTYPE.DTCONTENTS IS '문서양식내용';
COMMENT ON COLUMN SGN_DOCTYPE.DELETEFLAG IS '삭제';
-- DROP SEQUENCE DTNO_SEQ;
CREATE SEQUENCE DTNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;


-- 결재경로
-- DROP TABLE SGN_PATH CASCADE CONSTRAINTS;
CREATE TABLE SGN_PATH
(
    SPNO       NUMBER(10, 0) NOT NULL, -- AUTO_INCREMENT SPNO_SEQ
    SPTITLE    VARCHAR2(30),
    INSERTDATE TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    USERNO     NUMBER(10, 0) NOT NULL,
    SPSIGNPATH VARCHAR2(200),
    CONSTRAINT PK_SGN_PATH PRIMARY KEY (SPNO)
);
COMMENT ON TABLE SGN_PATH IS '결재경로';
COMMENT ON COLUMN SGN_PATH.SPNO IS '결재경로번호';
COMMENT ON COLUMN SGN_PATH.SPTITLE IS '경로명';
COMMENT ON COLUMN SGN_PATH.INSERTDATE IS '생성일자';
COMMENT ON COLUMN SGN_PATH.USERNO IS '사용자번호';
COMMENT ON COLUMN SGN_PATH.SPSIGNPATH IS '결재경로문자열';
-- DROP SEQUENCE SPNO_SEQ;
CREATE SEQUENCE SPNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;


-- 결재경로상세-결재자
-- DROP TABLE SGN_PATHUSER CASCADE CONSTRAINTS ;
CREATE TABLE SGN_PATHUSER
(
    SPNO   NUMBER(10, 0) NOT NULL,
    SPUSEQ NUMBER(10, 0) NOT NULL,
    USERNO NUMBER(10, 0) NOT NULL,
    SSTYPE CHAR(1),
    CONSTRAINT PK_SGN_PATHUSER PRIMARY KEY (SPNO, SPUSEQ)
);
COMMENT ON TABLE SGN_PATHUSER IS '결재경로상세-결재자';
COMMENT ON COLUMN SGN_PATHUSER.SPNO IS '결재경로번호';
COMMENT ON COLUMN SGN_PATHUSER.SPUSEQ IS '경로순번';
COMMENT ON COLUMN SGN_PATHUSER.USERNO IS '사용자번호';
COMMENT ON COLUMN SGN_PATHUSER.SSTYPE IS '결재종류';


-- 결재
-- DROP TABLE SGN_SIGN CASCADE CONSTRAINTS ;
CREATE TABLE SGN_SIGN
(
    SSNO        NUMBER(10, 0) NOT NULL, -- AUTO_INCREMENT SSNO_SIGN_SEQ
    DOCNO       NUMBER(20, 0) NOT NULL,
    SSSTEP      NUMBER(5, 0),
    SSTYPE      CHAR(1),
    SSRESULT    CHAR(1),
    SSCOMMENT   VARCHAR2(1000),
    RECEIVEDATE TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    SIGNDATE    TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    USERNO      NUMBER(10, 0) NOT NULL,
    USERPOS     VARCHAR2(10),
    CONSTRAINT SGN_SIGN PRIMARY KEY (SSNO, DOCNO)
);
COMMENT ON TABLE SGN_SIGN IS '결재';
COMMENT ON COLUMN SGN_SIGN.SSNO IS '결재번호';
COMMENT ON COLUMN SGN_SIGN.DOCNO IS '문서번호';
COMMENT ON COLUMN SGN_SIGN.SSSTEP IS '결재단계';
COMMENT ON COLUMN SGN_SIGN.SSTYPE IS '결재종류';
COMMENT ON COLUMN SGN_SIGN.SSRESULT IS '결재결과';
COMMENT ON COLUMN SGN_SIGN.SSCOMMENT IS '코멘트';
COMMENT ON COLUMN SGN_SIGN.RECEIVEDATE IS '받은일자';
COMMENT ON COLUMN SGN_SIGN.SIGNDATE IS '결재일자';
COMMENT ON COLUMN SGN_SIGN.USERNO IS '사용자번호';
COMMENT ON COLUMN SGN_SIGN.USERPOS IS '직위';
-- DROP SEQUENCE SSNO_SIGN_SEQ;
CREATE SEQUENCE SSNO_SIGN_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    CACHE 10;



-- DROP FUNCTION uf_datetime2string;

CREATE OR REPLACE FUNCTION uf_datetime2string(dt_ IN DATE)
    RETURN VARCHAR2
IS
    ti NUMBER;
    ret_ varchar2(10);
BEGIN

    ti :=  to_date(SYSDATE,'YYYY-MM-DD HH24:MI') - to_date(dt_,'YYYY-MM-DD HH24:MI');

    IF ti < 1 THEN ret_:='방금';
    ELSIF ti < 60 THEN ret_:= CONCAT(TRUNC(ti ,0), '분전');
    ELSIF ti < 60*24 THEN
        IF (abs(trunc(sysdate) - to_date(dt_,'YYYY-MM-DD')) =1) THEN
            ret_:= '어제';
        ELSE
            ret_:= CONCAT(TRUNC(ti/60 ,0), '시간전');
        END IF;
    ELSIF ti < 60*24*7 THEN ret_:= CONCAT(TRUNC(ti/60/24 ,0), '일전');
    ELSIF ti < 60*24*7*4 THEN ret_:= CONCAT(TRUNC(ti/60/24/7 ,0), '주전');
    ELSIF (MONTHS_BETWEEN(dt_, SYSDATE)=1) THEN ret_:= '지난달';
    ELSIF (MONTHS_BETWEEN(dt_, SYSDATE)<13) THEN
        IF (MONTHS_BETWEEN(dt_, SYSDATE)/12 =1) THEN
            ret_:= '작년';
        ELSE
            ret_:= TRUNC(MONTHS_BETWEEN(dt_, SYSDATE) ,0)||'달전';
        END IF;
    ELSE ret_:= TRUNC(MONTHS_BETWEEN(dt_, SYSDATE)/12) || '년전';
    END IF;

    RETURN ret_;

END uf_datetime2string;

---- start makeCalendar
declare
    sdate date := to_date('2018-01-01','YYYY-MM-DD');
    edate date := to_date('2022-05-06','YYYY-MM-DD');
begin
    WHILE(sdate  <= edate)
        LOOP
            INSERT INTO COM_DATE (CDNO, CDDATE, CDYEAR, CDMM, CDDD, CDWEEKOFYEAR, CDWEEKOFMONTH, CDWEEK, CDDAYOFWEEK)
            SELECT cdno_seq.nextval,
                   to_char(sdate,'YYYY-MM-DD'),
                   extract(year from TO_DATE(TO_DATE(sdate))),
                   extract(month from TO_DATE(TO_DATE(sdate))),
                   extract(day from TO_DATE(TO_DATE(sdate))),
                   TO_CHAR(TO_DATE(sdate), 'IW'),
                   to_char(sdate, 'WW'),
                   to_char(sdate+1, 'IW')-1,
                   to_char(sdate, 'D')
            FROM DUAL;
            SELECT sdate+1 INTO sdate FROM DUAL;

        END LOOP;

end;
---- end makeCalendar

-- DROP FUNCTION getColor4Alert;

CREATE OR REPLACE FUNCTION getColor4Alert(TSSTARTDATE_ IN VARCHAR2
, TSENDDATE_ IN VARCHAR2
, TSENDREAL_ IN VARCHAR2
, TSRATE_ IN NUMBER)
    RETURN VARCHAR2
    IS
    BGCOLOR_ varchar2(10)  := 'gray'; -- tsstartdate < today
    TODAY_   TIMESTAMP(6) := SYSDATE;
BEGIN
    /*
      오늘 날짜가 시작일자보다 작으면 회색
      오늘 날짜가 종료일자를 지났으면 빨강
      오늘 날짜가 주어진 기간의 50% 미만이면 녹색
      오늘 날짜가 주어진 기간의 50% 이상이면 노랑
      진행율이 기간내에 100이 되었으면 녹색, 지난뒤에 100이면 검정색
    */
    IF TSRATE_ < 100 THEN
        IF TSSTARTDATE_ > TODAY_ THEN
            BGCOLOR_ := 'gray';
        ELSIF TSENDDATE_ < TODAY_ THEN
            BGCOLOR_ := 'red';
        ELSIF TO_DATE(TSSTARTDATE_, 'YYYY-MM-DD') - TRUNC(SYSDATE) < TRUNC(SYSDATE) - TO_DATE(TSENDDATE_, 'YYYY-MM-DD') THEN
            BGCOLOR_ := 'green';
        ELSIF TO_DATE(TSSTARTDATE_, 'YYYY-MM-DD') - TRUNC(SYSDATE) >= TRUNC(SYSDATE) - TO_DATE(TSENDDATE_, 'YYYY-MM-DD') THEN
            BGCOLOR_ := 'yellow';
        END IF;
    ELSE
        IF TSENDREAL_ IS NOT NULL AND TSENDREAL_ <= TSENDDATE_ THEN
            BGCOLOR_ := 'green';
        ELSE
            BGCOLOR_ := 'orange';
        END IF;
    END IF;

    RETURN BGCOLOR_;

END;