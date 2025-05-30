package com.devkbil.mtssbj.board;

import com.devkbil.mtssbj.admin.board.BoardGroupVO;
import com.devkbil.mtssbj.common.ExtFieldVO;
import com.devkbil.mtssbj.common.util.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BoardService {

    @Autowired
    private SqlSessionTemplate sqlSession;
    @Autowired(required = false)
    private JpaTransactionManager txManager;

    /**
     * 게시판 정보 (그룹).
     */
    public BoardGroupVO selectBoardGroupOne4Used(String param) {
        return sqlSession.selectOne("selectBoardGroupOne4Used", param);
    }

    /**
     * ------------------------------------------
     * 게시판.
     */
    public Integer selectBoardCount(BoardSearchVO param) {
        return sqlSession.selectOne("selectBoardCount", param);
    }

    public List<?> selectBoardList(BoardSearchVO param) {
        return sqlSession.selectList("selectBoardList", param);
    }

    public List<?> selectNoticeList(BoardSearchVO param) {
        return sqlSession.selectList("selectNoticeList", param);
    }

    /**
     * 글 저장.
     */
    public void insertBoard(BoardVO param, List<FileVO> filelist, String[] fileno) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            if (param.getBrdno() == null || "".equals(param.getBrdno())) {
                sqlSession.insert("insertBoard", param);
            } else {
                sqlSession.update("updateBoard", param);
            }

            if (fileno != null) {
                HashMap<String, String[]> fparam = new HashMap<String, String[]>();
                fparam.put("fileno", fileno);
                sqlSession.insert("updateBoardFile", fparam);
            }

            for (FileVO f : filelist) {
                f.setParentPK(param.getBrdno());
                sqlSession.insert("insertBoardFile", f);
            }
            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            log.error("insertBoard");
        }
    }

    public BoardVO selectBoardOne(ExtFieldVO param) {
        return sqlSession.selectOne("selectBoardOne", param);
    }

    /**
     * 게시판 수정/삭제 권한 확인.
     */
    public String selectBoardAuthChk(BoardVO param) {
        return sqlSession.selectOne("selectBoardAuthChk", param);
    }

    public void updateBoardRead(ExtFieldVO param) {
        sqlSession.insert("updateBoardRead", param);
    }

    public void deleteBoardOne(String param) {
        sqlSession.delete("deleteBoardOne", param);
    }

    /**
     * 좋아요저장.
     */
    public void insertBoardLike(ExtFieldVO param) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            sqlSession.insert("insertBoardLike", param);
            sqlSession.update("updateBoard4Like", param);

            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            log.error("insertBoardLike");
        }
    }

    public List<?> selectBoardFileList(String param) {
        return sqlSession.selectList("selectBoardFileList", param);
    }

    /* =============================================================== */
    public List<?> selectBoardReplyList(String param) {
        return sqlSession.selectList("selectBoardReplyList", param);
    }

    /**
     * 댓글 저장.
     */
    public BoardReplyVO insertBoardReply(BoardReplyVO param) {
        if (param.getReno() == null || "".equals(param.getReno())) {
            if (param.getReparent() != null) {
                BoardReplyVO replyInfo = sqlSession.selectOne("selectBoardReplyParent", param.getReparent());
                param.setRedepth(replyInfo.getRedepth());
                param.setReorder(replyInfo.getReorder() + 1);
                sqlSession.selectOne("updateBoardReplyOrder", replyInfo);
            } else {
                Integer reorder = sqlSession.selectOne("selectBoardReplyMaxOrder", param.getBrdno());
                param.setReorder(reorder);
            }

            sqlSession.insert("insertBoardReply", param);
        } else {
            sqlSession.insert("updateBoardReply", param);
        }
        return sqlSession.selectOne("selectBoardReplyOne", param.getReno());
    }

    /**
     * 댓글 수정/삭제 권한 확인.
     */
    public String selectBoardReplyAuthChk(BoardReplyVO param) {
        return sqlSession.selectOne("selectBoardReplyAuthChk", param);
    }

    /**
     * 댓글 삭제.
     * 자식 댓글이 있으면 삭제 안됨.
     */
    public boolean deleteBoardReply(BoardReplyVO boardReplyInfo) {
        Integer cnt = sqlSession.selectOne("selectBoardReplyChild", boardReplyInfo);

        if (cnt > 0) {
            return false;
        }

        sqlSession.update("updateBoardReplyOrder4Delete", boardReplyInfo);

        sqlSession.delete("deleteBoardReply", boardReplyInfo);

        return true;
    }


    /**
     * 게시글 하위 댓글 모두 삭제.
     */
    public boolean deleteBoardReplyAll(Map map) {
        sqlSession.delete("deleteBoardReplyAll", map);
        return true;
    }

    /* =============================================================== */

    /**
     * 색인용 데이터 추출.
     * 마지막 색인 이후의 데이터.
     */
    public List<?> selectBoards4Indexing(String param) {
        return sqlSession.selectList("selectBoards4Indexing", param);
    }

    public List<?> selectBoardReply4Indexing(ExtFieldVO param) {
        return sqlSession.selectList("selectBoardReply4Indexing", param);
    }

    public List<?> selectBoardFiles4Indexing(ExtFieldVO param) {
        return sqlSession.selectList("selectBoardFiles4Indexing", param);
    }
}
