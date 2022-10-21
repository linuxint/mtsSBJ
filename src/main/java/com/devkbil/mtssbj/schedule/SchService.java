package com.devkbil.mtssbj.schedule;

import com.devkbil.mtssbj.common.ExtFieldVO;
import com.devkbil.mtssbj.search.SearchVO;
import com.devkbil.mtssbj.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SchService {
    
    @Autowired
    private SqlSessionTemplate sqlSession;
    @Autowired(required = false)
    private DataSourceTransactionManager txManager;
    
    public List<?> selectCalendar(MonthVO param, String userno) {
        List<?> list = sqlSession.selectList("selectCalendar", param);
        
        ExtFieldVO fld = new ExtFieldVO();
        fld.setField1(userno);
        for (int i = 0; i < list.size(); i++) {
            CalendarVO cvo = (CalendarVO) list.get(i);
            fld.setField2(cvo.getCddate());
            cvo.setList(sqlSession.selectList("selectSchList4Calen", fld));
        }
        return list;
    }
    
    /**
     * 리스트.
     */
    public Integer selectSchCount(SearchVO param) {
        return sqlSession.selectOne("selectSchCount", param);
    }
    
    public List<?> selectSchList(SearchVO param) {
        return sqlSession.selectList("selectSchList", param);
    }
    
    /**
     * 저장.
     */
    public void insertSch(SchVO param) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        
        try {
            if(param.getSsno() == null || "".equals(param.getSsno())) {
                sqlSession.insert("insertSch", param);
            } else {
                sqlSession.update("updateSch", param);
            }
            
            sqlSession.insert("deleteSchDetail", param.getSsno());
            
            SchDetailVO param2 = new SchDetailVO();
            param2.setSsno(param.getSsno());
            param2.setSdhour(param.getSsstarthour());
            param2.setSdminute(param.getSsstartminute());
            
            Integer inx = 1;
            Date sdate = DateUtil.str2Date(param.getSsstartdate());
            if("1".equals(param.getSsrepeattype())) {            //반복없음
                Date edate = DateUtil.str2Date(param.getSsenddate());
                while (!sdate.after(edate)) {
                    param2.setSdseq(inx++);
                    param2.setSddate(DateUtil.date2Str(sdate));
                    sqlSession.insert("insertSchDetail", param2);
                    sdate = DateUtil.dateAdd(sdate, 1);
                }
            } else if("2".equals(param.getSsrepeattype())) {            //주간반복
                Date edate = DateUtil.str2Date(param.getSsrepeatend());
                
                Integer dayofweek = Integer.parseInt(param.getSsrepeatoption());
                while (!sdate.after(edate)) {
                    if(DateUtil.getDayOfWeek(sdate) == dayofweek) break;
                    sdate = DateUtil.dateAdd(sdate, 1);
                }
                while (!sdate.after(edate)) {
                    param2.setSdseq(inx++);
                    param2.setSddate(DateUtil.date2Str(sdate));
                    sqlSession.insert("insertSchDetail", param2);
                    sdate = DateUtil.dateAdd(sdate, 7);
                }
            } else if("3".equals(param.getSsrepeattype())) {            //월간반복
                Date edate = DateUtil.str2Date(param.getSsrepeatend());
                
                Integer iYear = DateUtil.getYear(sdate);
                Integer iMonth = DateUtil.getMonth(sdate);
                String sday = param.getSsrepeatoption();
                
                Date ndate = DateUtil.str2Date(iYear + "-" + iMonth + "-" + sday);
                if(sdate.after(ndate))
                    sdate = DateUtil.str2Date(String.format("%s-%s-%s", iYear, ++iMonth, sday));
                else sdate = ndate;
                
                while (!sdate.after(edate)) {
                    param2.setSdseq(inx++);
                    param2.setSddate(DateUtil.date2Str(sdate));
                    sqlSession.insert("insertSchDetail", param2);
                    sdate = DateUtil.str2Date(String.format("%s-%s-%s", iYear, ++iMonth, sday));
                }
            }
            
            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            log.error("insertSch");
        }
    }
    
    /**
     * 읽기.
     */
    public SchVO selectSchOne(SchVO param) {
        return sqlSession.selectOne("selectSchOne", param);
    }
    
    public SchVO selectSchOne4Read(SchVO param) {
        return sqlSession.selectOne("selectSchOne4Read", param);
    }
    
    /**
     * 삭제.
     */
    public void deleteSch(SchVO param) {
        sqlSession.update("deleteSch", param);
    }
}
