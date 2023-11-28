create table PRJ_PROJECT
(
    PRNO NUMBER(10) not null constraint PK_PRJ_PROJECT primary key,
    PRSTARTDATE VARCHAR2(10),
    PRENDDATE   VARCHAR2(10),
    PRTITLE     VARCHAR2(100),
    REGDATE     TIMESTAMP(6) default CURRENT_TIMESTAMP,
    USERNO      NUMBER(10),
    PRSTATUS    CHAR,
    DELETEFLAG  CHAR         default 'N'
);
comment on table PRJ_PROJECT is '프로젝트';
comment on column PRJ_PROJECT.PRNO is '프로젝트 번호';
comment on column PRJ_PROJECT.PRSTARTDATE is '시작일자';
comment on column PRJ_PROJECT.PRENDDATE is '종료일자';
comment on column PRJ_PROJECT.PRTITLE is '프로젝트 제목';
comment on column PRJ_PROJECT.REGDATE is '등록일자';
comment on column PRJ_PROJECT.USERNO is '사용자번호';
comment on column PRJ_PROJECT.PRSTATUS is '상태';
comment on column PRJ_PROJECT.DELETEFLAG is '삭제';

create table PRJ_TASK
(
    PRNO        NUMBER(10),
    TSNO        NUMBER(19) not null
        constraint PK_PRJ_TASK primary key,
    TSPARENT    NUMBER(19),
    TSSORT      NUMBER(7),
    TSTITLE     VARCHAR2(100),
    TSSTARTDATE VARCHAR2(10),
    TSENDDATE   VARCHAR2(10),
    TSENDREAL   VARCHAR2(10),
    TSRATE      NUMBER(5),
    OLDNO       NUMBER(19),
    DELETEFLAG  CHAR default 'N'
);
comment on table PRJ_TASK is '프로젝트 업무';
comment on column PRJ_TASK.PRNO is '프로젝트 번호';
comment on column PRJ_TASK.TSNO is '업무번호';
comment on column PRJ_TASK.TSPARENT is '부모업무번호';
comment on column PRJ_TASK.TSSORT is '정렬';
comment on column PRJ_TASK.TSTITLE is '업무 제목';
comment on column PRJ_TASK.TSSTARTDATE is '시작일자';
comment on column PRJ_TASK.TSENDDATE is '종료일자';
comment on column PRJ_TASK.TSENDREAL is '종료일자(실제)';
comment on column PRJ_TASK.TSRATE is '진행율';
comment on column PRJ_TASK.OLDNO is '이전업무번호';
comment on column PRJ_TASK.DELETEFLAG is '삭제';

create table PRJ_TASKUSER
(
    TSNO   NUMBER(19) not null,
    USERNO NUMBER(10) not null,
    constraint PK_PRJ_TASKUSER
        primary key (TSNO, USERNO)
);
comment on table PRJ_TASKUSER is '업무할당';
comment on column PRJ_TASKUSER.TSNO is '업무번호';
comment on column PRJ_TASKUSER.USERNO is '사용자번호';

create table PRJ_TASKFILE
(
    TSNO     NUMBER(19),
    FILENO   NUMBER(10) not null
        constraint PK_PRJ_TASKFILE primary key,
    FILENAME VARCHAR2(100),
    REALNAME VARCHAR2(30),
    FILESIZE NUMBER(10),
    DELETEFALG CHAR(1) DEFAULT 'N'
);
comment on table PRJ_TASKFILE is '첨부파일';
comment on column PRJ_TASKFILE.TSNO is '업무번호';
comment on column PRJ_TASKFILE.FILENO is '파일번호';
comment on column PRJ_TASKFILE.FILENAME is '파일명';
comment on column PRJ_TASKFILE.REALNAME is '실제파일명';
comment on column PRJ_TASKFILE.FILESIZE is '파일크기';
comment on column PRJ_TASKFILE.DELETEFALG is '삭제여부'; -- 2023-11-03

create table COM_CODE
(
    CLASSNO NUMBER(10)   not null,
    CODECD  VARCHAR2(10) not null,
    CODENM  VARCHAR2(30),
    constraint PK_COM_CODE
        primary key (CLASSNO, CODECD)
);
comment on table COM_CODE is '공통코드';
comment on column COM_CODE.CLASSNO is '그릅코드';
comment on column COM_CODE.CODECD is '코드번호';
comment on column COM_CODE.CODENM is '코드명';

create table COM_CODE_V2
(
    CODECD VARCHAR2(5) not null constraint COM_CODE_V2_PK primary key,
    PCODECD VARCHAR2(5) constraint COM_CODE_V2_FK references COM_CODE_V2,
    CODENM     VARCHAR2(40),
    CODEDESC   VARCHAR2(100),
    DELETEFLAG CHAR         default 'N',
    REGDATE    TIMESTAMP(6) default CURRENT_TIMESTAMP,
    REGUSERNO  NUMBER(10),
    CHGDATE    TIMESTAMP(6) default CURRENT_TIMESTAMP,
    CHGUSERNO  NUMBER(10),
    constraint COM_CODE_V2_UK
        unique (CODECD, PCODECD)
);
comment on table COM_CODE_V2 is '공통코드';
comment on column COM_CODE_V2.CODECD is '코드';
comment on column COM_CODE_V2.PCODECD is '상위코드';
comment on column COM_CODE_V2.CODENM is '코드명';
comment on column COM_CODE_V2.CODEDESC is '코드설명';
comment on column COM_CODE_V2.DELETEFLAG is '사용여부';
comment on column COM_CODE_V2.REGDATE is '등록일자';
comment on column COM_CODE_V2.REGUSERNO is '등록자';
comment on column COM_CODE_V2.CHGDATE is '수정일자';
comment on column COM_CODE_V2.CHGUSERNO is '수정자';

create table COM_DEPT
(
    DEPTNO     NUMBER(10) not null
        constraint PK_COM_DEPT primary key,
    DEPTNM     VARCHAR2(20),
    PARENTNO   NUMBER(10) default 0,
    DELETEFLAG CHAR       default 'N'
);
comment on table COM_DEPT is '그룹 부서';
comment on column COM_DEPT.DEPTNO is '부서 번호';
comment on column COM_DEPT.DEPTNM is '부서명';
comment on column COM_DEPT.PARENTNO is '부모 부서';
comment on column COM_DEPT.DELETEFLAG is '삭제 여부';

create table COM_USER
(
    USERNO NUMBER(10) not null constraint PK_COM_USER primary key,
    USERID     VARCHAR2(20),
    USERNM     VARCHAR2(20),
    USERPW     VARCHAR2(100),
    USERROLE   CHAR,
    PHOTO      VARCHAR2(50),
    DEPTNO     NUMBER(10),
    DELETEFLAG CHAR default 'N',
    USERPOS    VARCHAR2(2)
);
comment on table COM_USER is '사용자';
comment on column COM_USER.USERNO is '사용자 번호';
comment on column COM_USER.USERID is 'ID';
comment on column COM_USER.USERNM is '이름';
comment on column COM_USER.USERPW is '비밀번호';
comment on column COM_USER.USERROLE is '권한';
comment on column COM_USER.PHOTO is '사진';
comment on column COM_USER.DEPTNO is '부서번호';
comment on column COM_USER.DELETEFLAG is '삭제 여부';
comment on column COM_USER.USERPOS is '직위';

create table TBL_BOARD
(
    BGNO       NUMBER(10)   default 0,
    BRDNO      NUMBER(10) not null constraint PK_TBL_BOARD primary key,
    BRDTITLE   VARCHAR2(255),
    USERNO     NUMBER(10) constraint FK_TBL_BOARD_USERNO references COM_USER,
    BRDMEMO    VARCHAR2(4000),
    REGDATE    TIMESTAMP(6) default CURRENT_TIMESTAMP,
    BRDNOTICE  CHAR,
    CHGDATE    TIMESTAMP(6) default CURRENT_TIMESTAMP,
    CHGUSERNO  NUMBER(10),
    BRDLIKE    NUMBER(10)   default 0,
    DELETEFLAG CHAR         default 'N',
    ETC1       VARCHAR2(200),
    ETC2       VARCHAR2(200),
    ETC3       VARCHAR2(200),
    ETC4       VARCHAR2(200),
    ETC5       VARCHAR2(200)
);
comment on table TBL_BOARD is '게시판';
comment on column TBL_BOARD.BGNO is '게시판 그룹번호';
comment on column TBL_BOARD.BRDNO is '글 번호';
comment on column TBL_BOARD.BRDTITLE is '글 제목';
comment on column TBL_BOARD.USERNO is '작성자';
comment on column TBL_BOARD.BRDMEMO is '글 내용';
comment on column TBL_BOARD.REGDATE is '등록일자';
comment on column TBL_BOARD.BRDNOTICE is '공지사항여부';
comment on column TBL_BOARD.CHGDATE is '마지막 수정일';
comment on column TBL_BOARD.CHGUSERNO is '마지막 수정자';
comment on column TBL_BOARD.BRDLIKE is '좋아요';
comment on column TBL_BOARD.DELETEFLAG is '삭제 여부';
comment on column TBL_BOARD.ETC1 is '기타1';
comment on column TBL_BOARD.ETC2 is '기타2';
comment on column TBL_BOARD.ETC3 is '기타3';
comment on column TBL_BOARD.ETC4 is '기타4';
comment on column TBL_BOARD.ETC5 is '기타5';

create table TBL_BOARDFILE
(
    FILENO   NUMBER(10) not null
        constraint PK_TBL_BOARDFILE primary key,
    BRDNO    NUMBER(10) default 0,
    FILENAME VARCHAR2(100),
    REALNAME VARCHAR2(30),
    FILESIZE NUMBER(10),
    DELETEFLAG CHAR(1) default 'N'
);
comment on table TBL_BOARDFILE is '게시판 첨부파일';
comment on column TBL_BOARDFILE.FILENO is '파일 번호';
comment on column TBL_BOARDFILE.BRDNO is '글번호';
comment on column TBL_BOARDFILE.FILENAME is '파일명';
comment on column TBL_BOARDFILE.REALNAME is '실제파일명';
comment on column TBL_BOARDFILE.FILESIZE is '파일 크기';
comment on column TBL_BOARDFILE.DELETEFLAG is '삭제 여부';

create table TBL_BOARDLIKE
(
    BLNO    NUMBER(10) not null
        constraint PK_TBL_BOARDLIKE primary key,
    BRDNO   NUMBER(10)   default 0,
    USERNO  NUMBER(10),
    REGDATE TIMESTAMP(6) default CURRENT_TIMESTAMP
);
comment on table TBL_BOARDLIKE is '게시판 좋아요';
comment on column TBL_BOARDLIKE.BLNO is '번호';
comment on column TBL_BOARDLIKE.BRDNO is '글번호';
comment on column TBL_BOARDLIKE.USERNO is '작성자';
comment on column TBL_BOARDLIKE.REGDATE is '등록일';

drop table TBL_BOARDREPLY;
create table TBL_BOARDREPLY
(
    BRDNO        NUMBER(10) not null  constraint FK_TBL_BOARDREPLY_BRDNO references TBL_BOARD,
    RENO         NUMBER(10) not null constraint PK_TBL_BOARDREPLY primary key,
    USERNO       NUMBER(10),
    REMEMO       VARCHAR2(500) default NULL,
    REPARENT     NUMBER(10)    default 0,
    REDEPTH      NUMBER(10),
    REORDER      NUMBER(10),
    REGDATE      TIMESTAMP(6)  default CURRENT_TIMESTAMP,
    REDELETEFLAG CHAR          default 'N',
    CHGDATE      TIMESTAMP(6)  default CURRENT_TIMESTAMP,
    CHGUSERNO    NUMBER(10),
    REDELDATE    TIMESTAMP(6)  default CURRENT_TIMESTAMP,
    DELDATE      TIMESTAMP(6)  default CURRENT_TIMESTAMP
);
comment on table TBL_BOARDREPLY is '게시판 좋아요';
comment on column TBL_BOARDREPLY.BRDNO is '게시물 번호';
comment on column TBL_BOARDREPLY.RENO is '댓글 번호';
comment on column TBL_BOARDREPLY.USERNO is '작성자';
comment on column TBL_BOARDREPLY.REMEMO is '댓글내용';
comment on column TBL_BOARDREPLY.REPARENT is '부모 댓글';
comment on column TBL_BOARDREPLY.REDEPTH is '깊이';
comment on column TBL_BOARDREPLY.REORDER is '순서';
comment on column TBL_BOARDREPLY.REGDATE is '등록일자';
comment on column TBL_BOARDREPLY.REDELETEFLAG is '삭제여부';
comment on column TBL_BOARDREPLY.CHGDATE is '마지막 수정일';
comment on column TBL_BOARDREPLY.CHGUSERNO is '미자막 수정자';

create table TBL_BOARDREAD
(
    BRDNO   NUMBER(10) not null,
    USERNO  NUMBER(10) not null,
    REGDATE TIMESTAMP(6) default CURRENT_TIMESTAMP,
    constraint PK_TBL_BOARDREAD
        primary key (BRDNO, USERNO)
);
comment on table TBL_BOARDREAD is '게시판읽기';
comment on column TBL_BOARDREAD.BRDNO is '게시물 번호';
comment on column TBL_BOARDREAD.USERNO is '작성자';
comment on column TBL_BOARDREAD.REGDATE is '등록일자';

create table TBL_BOARDGROUP
(
    BGNO       NUMBER(10) not null
        constraint PK_TBL_BOARDGROUP primary key,
    BGNAME     VARCHAR2(50),
    BGPARENT   NUMBER(10)   default 0,
    DELETEFLAG CHAR         default 'N',
    BGUSED     CHAR,
    BGREPLY    CHAR,
    BGREADONLY CHAR,
    BGNOTICE   CHAR,
    REGDATE    TIMESTAMP(6) default CURRENT_TIMESTAMP,
    CHGDATE    TIMESTAMP(6) default CURRENT_TIMESTAMP
);

comment on table TBL_BOARDGROUP is '게시판 그룹';
comment on column TBL_BOARDGROUP.BGNO is '게시판 그룹번호';
comment on column TBL_BOARDGROUP.BGNAME is '게시판 그룹명';
comment on column TBL_BOARDGROUP.BGPARENT is '게시판 그룹 부모';
comment on column TBL_BOARDGROUP.DELETEFLAG is '삭제 여부';
comment on column TBL_BOARDGROUP.BGUSED is '사용 여부';
comment on column TBL_BOARDGROUP.BGREPLY is '댓글 사용여부';
comment on column TBL_BOARDGROUP.BGREADONLY is '글쓰기 가능 여부';
comment on column TBL_BOARDGROUP.BGNOTICE is '공지 쓰기  가능 여부';
comment on column TBL_BOARDGROUP.REGDATE is '등록일자';
comment on column TBL_BOARDGROUP.CHGDATE is '변경일자';

create table COM_LOGINOUT
(
    LNO     NUMBER(10) not null
        constraint PK_COM_LOGINOUT primary key,
    USERNO  NUMBER(10),
    LTYPE   CHAR,
    REGDATE TIMESTAMP(6) default CURRENT_TIMESTAMP
);
comment on table COM_LOGINOUT is '로그인;로그아웃';
comment on column COM_LOGINOUT.LNO is '순번';
comment on column COM_LOGINOUT.USERNO is '로그인 사용자';
comment on column COM_LOGINOUT.LTYPE is '로그인;로그아웃구분';
comment on column COM_LOGINOUT.REGDATE is '등록일자';

create table TBL_CRUD
(
    CRNO       NUMBER(10) not null
        constraint PK_TBL_CRUD primary key,
    CRTITLE    VARCHAR2(255),
    USERNO     NUMBER(10),
    CRMEMO     VARCHAR2(4000),
    REGDATE    TIMESTAMP(6) default CURRENT_TIMESTAMP,
    DELETEFLAG CHAR         default 'N'
);
comment on table TBL_CRUD is '테이블 CRUD';
comment on column TBL_CRUD.CRNO is '번호';
comment on column TBL_CRUD.CRTITLE is '제목';
comment on column TBL_CRUD.USERNO is '작성자';
comment on column TBL_CRUD.CRMEMO is '내용';
comment on column TBL_CRUD.REGDATE is '등록일자';
comment on column TBL_CRUD.DELETEFLAG is '삭제 여부';

create table COM_DATE
(
    CDNO          NUMBER(19) not null
        constraint PK_COM_DATE primary key,
    CDDATE        VARCHAR2(10),
    CDYEAR        VARCHAR2(4),
    CDMM          VARCHAR2(2),
    CDDD          VARCHAR2(2),
    CDWEEKOFYEAR  NUMBER(5),
    CDWEEKOFMONTH NUMBER(5),
    CDWEEK        NUMBER(5),
    CDDAYOFWEEK   NUMBER(5)
);
comment on table COM_DATE is '날짜';
comment on column COM_DATE.CDNO is '번호';
comment on column COM_DATE.CDDATE is '날짜';
comment on column COM_DATE.CDYEAR is '년도';
comment on column COM_DATE.CDMM is '월';
comment on column COM_DATE.CDDD is '일';
comment on column COM_DATE.CDWEEKOFYEAR is '연별주차';
comment on column COM_DATE.CDWEEKOFMONTH is '월별주차';
comment on column COM_DATE.CDWEEK is '주';
comment on column COM_DATE.CDDAYOFWEEK is '주별일자';

create table EML_ADDRESS
(
    EMNO      NUMBER(10) not null, --EML_MAIL.EMNO
    EASEQ     NUMBER(10) not null,
    EATYPE    CHAR       not null,
    EAADDRESS VARCHAR2(150),
    constraint PK_EML_ADDRESS
        primary key (EMNO, EASEQ, EATYPE)
);
comment on table EML_ADDRESS is '메일주소';
comment on column EML_ADDRESS.EMNO is '메일번호';
comment on column EML_ADDRESS.EASEQ is '순번';
comment on column EML_ADDRESS.EATYPE is '주소종류';
comment on column EML_ADDRESS.EAADDRESS is '메일주소';

create table EML_MAIL
(
    EMNO       NUMBER(10) not null
        constraint PK_EML_MAIL primary key,
    EMTYPE     CHAR,
    EMSUBJECT  VARCHAR2(150),
    EMFROM     VARCHAR2(150),
    EMCONTENTS VARCHAR2(4000),
    REGDATE    TIMESTAMP(6) default CURRENT_TIMESTAMP,
    USERNO     NUMBER(10) not null,
    EMINO      NUMBER(10) not null,
    DELETEFLAG CHAR         default 'N'
);
comment on table EML_MAIL is '메일주소';
comment on column EML_MAIL.EMNO is '메일번호';
comment on column EML_MAIL.EMTYPE is '메일종류';
comment on column EML_MAIL.EMSUBJECT is '제목';
comment on column EML_MAIL.EMFROM is '보낸사람';
comment on column EML_MAIL.EMCONTENTS is '내용';
comment on column EML_MAIL.REGDATE is '작성일';
comment on column EML_MAIL.USERNO is '사용자번호';
comment on column EML_MAIL.EMINO is '메일정보번호';
comment on column EML_MAIL.DELETEFLAG is '삭제';

create index EML_MAIL_INX01
    on EML_MAIL (EMTYPE, USERNO, EMINO);

create table EML_MAILFILE
(
    FILENO   NUMBER(10) not null
        constraint PK_EML_MAILFILE primary key,
    FILENAME VARCHAR2(100),
    REALNAME VARCHAR2(30),
    FILESIZE NUMBER(10),
    EMNO     NUMBER(10) not null
);
comment on table EML_MAILFILE is '첨부파일';
comment on column EML_MAILFILE.FILENO is '파일번호';
comment on column EML_MAILFILE.FILENAME is '파일명';
comment on column EML_MAILFILE.REALNAME is '실제파일명';
comment on column EML_MAILFILE.FILESIZE is '파일크기';
comment on column EML_MAILFILE.EMNO is '메일번호';

create table EML_MAILINFO
(
    EMINO       NUMBER(10) not null
        constraint PK_EML_MAILINFO primary key,
    EMIIMAP     VARCHAR2(30),
    EMIIMAPPORT VARCHAR2(5),
    EMISMTP     VARCHAR2(30),
    EMISMTPPORT VARCHAR2(5),
    EMIUSER     VARCHAR2(50),
    EMIPW       VARCHAR2(50),
    USERNO      NUMBER(10) not null,
    REGDATE     TIMESTAMP(6) default CURRENT_TIMESTAMP,
    DELETEFLAG  CHAR         default 'N'
);
comment on table EML_MAILINFO is '메일정보';
comment on column EML_MAILINFO.EMINO is '메일정보번호';
comment on column EML_MAILINFO.EMIIMAP is 'IMAP서버주소';
comment on column EML_MAILINFO.EMIIMAPPORT is 'IMAP서버포트';
comment on column EML_MAILINFO.EMISMTP is 'SMTP 서버주소';
comment on column EML_MAILINFO.EMISMTPPORT is 'SMTP 서버포트';
comment on column EML_MAILINFO.EMIUSER is '계정';
comment on column EML_MAILINFO.EMIPW is '비밀번호';
comment on column EML_MAILINFO.USERNO is '사용자번호';
comment on column EML_MAILINFO.REGDATE is '등록일자';
comment on column EML_MAILINFO.DELETEFLAG is '삭제';

create table SCH_DETAIL
(
    SSNO     NUMBER(10) not null,
    SDSEQ    NUMBER(5)  not null,
    SDDATE   TIMESTAMP(6) default CURRENT_TIMESTAMP,
    SDHOUR   CHAR(2),
    SDMINUTE CHAR(2),
    constraint PK_SCH_DETAIL
        primary key (SSNO, SDSEQ)
);
comment on table SCH_DETAIL is '일정상세';
comment on column SCH_DETAIL.SSNO is '일정번호';
comment on column SCH_DETAIL.SDSEQ is '순번';
comment on column SCH_DETAIL.SDDATE is '날짜';
comment on column SCH_DETAIL.SDHOUR is '시간';
comment on column SCH_DETAIL.SDMINUTE is '분';

create table SCH_HOLIDAY
(
    SHNO       NUMBER(5) not null
        constraint PK_SCH_HOLIDAY primary key,
    SHTITLE    VARCHAR2(20),
    SHMONTH    NUMBER(5),
    SHDATE     NUMBER(5),
    SHCOLOR    VARCHAR2(10),
    DELETEFLAG CHAR default 'N'
);
comment on table SCH_HOLIDAY is '공휴일';
comment on column SCH_HOLIDAY.SHNO is '번호';
comment on column SCH_HOLIDAY.SHTITLE is '공휴일명';
comment on column SCH_HOLIDAY.SHMONTH is '월';
comment on column SCH_HOLIDAY.SHDATE is '일';
comment on column SCH_HOLIDAY.SHCOLOR is '색상';
comment on column SCH_HOLIDAY.DELETEFLAG is '삭제';

create table SCH_SCHEDULE
(
    SSNO           NUMBER(10) not null
        constraint PK_SCH_SCHEDULE primary key,
    SSTITLE        VARCHAR2(50),
    SSTYPE         CHAR,
    SSSTARTDATE    CHAR(10),
    SSSTARTHOUR    CHAR(2),
    SSSTARTMINUTE  CHAR(2),
    SSENDDATE      VARCHAR2(10),
    SSENDHOUR      CHAR(2),
    SSENDMINUTE    CHAR(2),
    SSREPEATTYPE   CHAR,
    SSREPEATOPTION VARCHAR2(2),
    SSREPEATEND    VARCHAR2(10),
    SSCONTENTS     VARCHAR2(4000),
    SSISOPEN       CHAR,
    CHGDATE        TIMESTAMP(6) default CURRENT_TIMESTAMP,
    REGDATE        TIMESTAMP(6) default CURRENT_TIMESTAMP,
    USERNO         NUMBER(10) not null,
    DELETEFLAG     CHAR         default 'N'
);
comment on table SCH_SCHEDULE is '일정';
comment on column SCH_SCHEDULE.SSNO is '일정번호';
comment on column SCH_SCHEDULE.SSTITLE is '일정명';
comment on column SCH_SCHEDULE.SSTYPE is '구분';
comment on column SCH_SCHEDULE.SSSTARTDATE is '시작일';
comment on column SCH_SCHEDULE.SSSTARTHOUR is '시작일-시간';
comment on column SCH_SCHEDULE.SSSTARTMINUTE is '시작일-분';
comment on column SCH_SCHEDULE.SSENDDATE is '종료일';
comment on column SCH_SCHEDULE.SSENDHOUR is '종료일-시간';
comment on column SCH_SCHEDULE.SSENDMINUTE is '종료일-분';
comment on column SCH_SCHEDULE.SSREPEATTYPE is '반복';
comment on column SCH_SCHEDULE.SSREPEATOPTION is '반복옵션';
comment on column SCH_SCHEDULE.SSREPEATEND is '반복종료';
comment on column SCH_SCHEDULE.SSCONTENTS is '내용';
comment on column SCH_SCHEDULE.SSISOPEN is '공개여부';
comment on column SCH_SCHEDULE.CHGDATE is '수정일자';
comment on column SCH_SCHEDULE.REGDATE is '등록일자';
comment on column SCH_SCHEDULE.USERNO is '사용자번호';
comment on column SCH_SCHEDULE.DELETEFLAG is '삭제';

create table SGN_DOC
(
    DOCNO       NUMBER(20) not null
        constraint PK_SGN_DOC primary key,
    DOCTITLE    VARCHAR2(50),
    DOCCONTENTS VARCHAR2(4000),
    DELETEFLAG  CHAR         default 'N',
    DOCSTATUS   CHAR,
    DOCSTEP     NUMBER(5),
    DTNO        NUMBER(10) not null,
    CHGDATE     TIMESTAMP(6) default CURRENT_TIMESTAMP,
    REGDATE     TIMESTAMP(6) default CURRENT_TIMESTAMP,
    USERNO      NUMBER(10) not null,
    DEPTNM      VARCHAR2(20),
    DOCSIGNPATH VARCHAR2(200)
);
comment on table SGN_DOC is '결재문서';
comment on column SGN_DOC.DOCNO is '문서번호';
comment on column SGN_DOC.DOCTITLE is '제목';
comment on column SGN_DOC.DOCCONTENTS is '문서내용';
comment on column SGN_DOC.DELETEFLAG is '삭제여부';
comment on column SGN_DOC.DOCSTATUS is '문서상태';
comment on column SGN_DOC.DOCSTEP is '결재단계';
comment on column SGN_DOC.DTNO is '문서양식번호';
comment on column SGN_DOC.CHGDATE is '수정일자';
comment on column SGN_DOC.REGDATE is '등록일자';
comment on column SGN_DOC.USERNO is '사용자번호';
comment on column SGN_DOC.DEPTNM is '부서명';
comment on column SGN_DOC.DOCSIGNPATH is '결재경로문자열';

create table SGN_DOCFILE
(
    FILENO   NUMBER(10) not null
        constraint PK_SGN_DOCFILE primary key,
    FILENAME VARCHAR2(100),
    REALNAME VARCHAR2(30),
    FILESIZE NUMBER(10),
    DOCNO    NUMBER(20) not null
);
comment on table SGN_DOCFILE is '첨부파일';
comment on column SGN_DOCFILE.FILENO is '파일번호';
comment on column SGN_DOCFILE.FILENAME is '파일명';
comment on column SGN_DOCFILE.REALNAME is '실제파일명';
comment on column SGN_DOCFILE.FILESIZE is '파일크기';
comment on column SGN_DOCFILE.DOCNO is '문서번호';

create table SGN_DOCTYPE
(
    DTNO       NUMBER(10) not null
        constraint PK_SGN_DOCTYPE primary key,
    DTTITLE    VARCHAR2(30),
    DTCONTENTS VARCHAR2(4000),
    DELETEFLAG CHAR default 'N'
);
comment on table SGN_DOCTYPE is '문서양식종류';
comment on column SGN_DOCTYPE.DTNO is '문서양식번호';
comment on column SGN_DOCTYPE.DTTITLE is '문서양식명';
comment on column SGN_DOCTYPE.DTCONTENTS is '문서양식내용';
comment on column SGN_DOCTYPE.DELETEFLAG is '삭제';

create table SGN_PATH
(
    SPNO       NUMBER(10) not null
        constraint PK_SGN_PATH primary key,
    SPTITLE    VARCHAR2(30),
    REGDATE    TIMESTAMP(6) default CURRENT_TIMESTAMP,
    USERNO     NUMBER(10) not null,
    SPSIGNPATH VARCHAR2(200)
);
comment on table SGN_PATH is '결재경로';
comment on column SGN_PATH.SPNO is '결재경로번호';
comment on column SGN_PATH.SPTITLE is '경로명';
comment on column SGN_PATH.REGDATE is '등록일자';
comment on column SGN_PATH.USERNO is '사용자번호';
comment on column SGN_PATH.SPSIGNPATH is '결재경로문자열';

create table SGN_PATHUSER
(
    SPNO   NUMBER(10) not null,
    SPUSEQ NUMBER(10) not null,
    USERNO NUMBER(10) not null,
    SSTYPE CHAR,
    constraint PK_SGN_PATHUSER
        primary key (SPNO, SPUSEQ)
);
comment on table SGN_PATHUSER is '결재경로상세-결재자';
comment on column SGN_PATHUSER.SPNO is '결재경로번호';
comment on column SGN_PATHUSER.SPUSEQ is '경로순번';
comment on column SGN_PATHUSER.USERNO is '사용자번호';
comment on column SGN_PATHUSER.SSTYPE is '결재종류';

create table SGN_SIGN
(
    SSNO        NUMBER(10) not null,
    DOCNO       NUMBER(20) not null,
    SSSTEP      NUMBER(5),
    SSTYPE      CHAR,
    SSRESULT    CHAR,
    SSCOMMENT   VARCHAR2(1000),
    RECEIVEDATE TIMESTAMP(6) default CURRENT_TIMESTAMP,
    SIGNDATE    TIMESTAMP(6) default CURRENT_TIMESTAMP,
    USERNO      NUMBER(10) not null,
    USERPOS     VARCHAR2(10),
    constraint SGN_SIGN
        primary key (SSNO, DOCNO)
);
comment on table SGN_SIGN is '결재';
comment on column SGN_SIGN.SSNO is '결재번호';
comment on column SGN_SIGN.DOCNO is '문서번호';
comment on column SGN_SIGN.SSSTEP is '결재단계';
comment on column SGN_SIGN.SSTYPE is '결재종류';
comment on column SGN_SIGN.SSRESULT is '결재결과';
comment on column SGN_SIGN.SSCOMMENT is '코멘트';
comment on column SGN_SIGN.RECEIVEDATE is '받은일자';
comment on column SGN_SIGN.SIGNDATE is '결재일자';
comment on column SGN_SIGN.USERNO is '사용자번호';
comment on column SGN_SIGN.USERPOS is '직위';

drop table com_menu;
create table COM_MENU
(
    MNU_NO           NUMBER(10) not null constraint PK_COM_MENU primary key,
    MNU_PARENT       NUMBER(10),
    MNU_TYPE         VARCHAR2(10),
    MNU_NM           VARCHAR2(100),
    MNU_DESC         VARCHAR2(400),
    MNU_TARGET       VARCHAR2(100),
    MNU_FILENM       VARCHAR2(100),
    MNU_IMGPATH      VARCHAR2(100),
    MNU_CUSTOM       VARCHAR2(400),
    MNU_DESKTOP      CHAR(1) default 'N',
    MNU_MOBILE       CHAR(1) default 'N',
    MNU_ORDER        NUMBER(10),
    MNU_CERT_TYPE    VARCHAR2(10),
    MNU_EXTN_CONN_YN CHAR(1) default 'N',
    MNU_START_HOUR   VARCHAR2(5),
    MNU_END_HOUR     VARCHAR2(5),
    REGDATE          TIMESTAMP(6) default CURRENT_TIMESTAMP,
    REGUSERNO        NUMBER(10),
    CHGDATE          TIMESTAMP(6)default CURRENT_TIMESTAMP,
    CHGUSERNO        NUMBER(10),
    DELETEFLAG       CHAR(1)
);
comment on table COM_MENU is '메뉴정보';
comment on column COM_MENU.MNU_NO           is '메뉴ID';
comment on column COM_MENU.MNU_PARENT       is '상위메뉴ID';
comment on column COM_MENU.MNU_TYPE         is '메뉴유형코드-팝업,컨텐트';
comment on column COM_MENU.MNU_NM           is '메뉴명';
comment on column COM_MENU.MNU_DESC         is '설명';
comment on column COM_MENU.MNU_TARGET       is '메뉴링크';
comment on column COM_MENU.MNU_FILENM       is '파일명';
comment on column COM_MENU.MNU_IMGPATH      is '이미지경로';
comment on column COM_MENU.MNU_CUSTOM       is '커스텀태그';
comment on column COM_MENU.MNU_DESKTOP      is '데스크탑버전 사용여부';
comment on column COM_MENU.MNU_MOBILE       is '모바일버전 사용여부';
comment on column COM_MENU.MNU_ORDER        is '정렬순서';
comment on column COM_MENU.MNU_CERT_TYPE    is '인증구분코드';
comment on column COM_MENU.MNU_EXTN_CONN_YN is '외부연결여부';
comment on column COM_MENU.MNU_START_HOUR   is '사용시작시';
comment on column COM_MENU.MNU_END_HOUR     is '사용종료시';
comment on column COM_MENU.REGDATE          is '등록일자';
comment on column COM_MENU.REGUSERNO        is '등록자';
comment on column COM_MENU.CHGDATE          is '수정일자';
comment on column COM_MENU.CHGUSERNO        is '수정자';
comment on column COM_MENU.DELETEFLAG       is '삭제여부';

