SELECT A1.TABLE_NAME                        TABLE_NAME
     , A1.TABLE_COMMENTS                    TABLE_COMMENTS
     , A1.COLUMN_COMMENTS                   COLUMN_COMMENTS
     , A1.COLUMN_NAME                       COLUMN_NAME
     , DECODE(B1.CONSTRAINT_TYPE, 'P', 'Y') PK_FLAG
     , DECODE(B1.CONSTRAINT_TYPE, 'R', 'Y') FK_FLAG
     , A1.NULL_FLAG
     , A1.DATA_TYPE
     , A1.DATA_LENGTH
FROM (SELECT B.COMMENTS                   TABLE_COMMENTS
           , A.TABLE_NAME                 TABLE_NAME
           , C.COMMENTS                   COLUMN_COMMENTS
           , A.COLUMN_NAME                COLUMN_NAME
           , DECODE(A.NULLABLE, 'Y', 'Y') NULL_FLAG
           , A.DATA_TYPE                  DATA_TYPE
           , (CASE
                  WHEN A.DATA_TYPE IN ('CHAR', 'VARCHAR2')
                      THEN '(' || A.DATA_LENGTH || ')'
                  WHEN A.DATA_TYPE = 'NUMBER'
                      AND A.DATA_SCALE = 0
                      AND A.DATA_PRECISION IS NOT NULL
                      THEN '(' || A.DATA_PRECISION || ')'
                  WHEN A.DATA_TYPE = 'NUMBER'
                      AND A.DATA_SCALE <> 0
                      THEN '(' || A.DATA_PRECISION || ',' || A.DATA_SCALE || ')'
        END
        )                                 DATA_LENGTH
           , A.COLUMN_ID
      FROM USER_TAB_COLUMNS A
               JOIN USER_TAB_COMMENTS B ON (A.TABLE_NAME = B.TABLE_NAME)
               JOIN USER_COL_COMMENTS C ON (A.TABLE_NAME = C.TABLE_NAME
          AND A.COLUMN_NAME = C.COLUMN_NAME)) A1
         LEFT JOIN
     (SELECT A.TABLE_NAME, A.COLUMN_NAME, B.CONSTRAINT_TYPE
      FROM USER_CONS_COLUMNS A,
           USER_CONSTRAINTS B
      WHERE (A.CONSTRAINT_NAME = B.CONSTRAINT_NAME)
        AND B.CONSTRAINT_TYPE IN ('P', 'R')) B1
     ON (A1.TABLE_NAME = B1.TABLE_NAME
         AND A1.COLUMN_NAME = B1.COLUMN_NAME)
-- WHERE A1.COLUMN_COMMENTS LIKE '%등록자%'
--WHERE A1.COLUMN_NAME LIKE '%START%'
WHERE A1.TABLE_NAME = 'COM_MENU'
ORDER BY A1.COLUMN_ID;
;

SELECT * FROM USER_TAB_COLUMNS