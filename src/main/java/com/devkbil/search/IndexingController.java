package com.devkbil.search;

import com.devkbil.board.BoardReplyVO;
import com.devkbil.board.BoardService;
import com.devkbil.board.BoardVO;
import com.devkbil.common.Field3VO;
import com.devkbil.common.FileVO;
import com.devkbil.common.LocaleMessage;
import com.devkbil.common.config.EsConfig;
import com.devkbil.util.FileUtil;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder; // springboot 2.6.8
//import static org.elasticsearch.xcontent.XContentFactory.jsonBuilder; // springboot 2.7.0

@Controller
@EnableAsync
@EnableScheduling
public class IndexingController {
    
    static final Logger logger = LoggerFactory.getLogger(IndexingController.class);
    @Value("${elasticsearch.clustername}")
    static final String INDEX_NAME = "mts";
    //final String LAST_FILE = localeMessage.getMessage("info.workspace") + "/elasticsearch/mts.last";
    static final String FILE_EXTENTION = "doc,ppt,xls,docx,pptx,xlsx,pdf,txt,zip,hwp";
    static boolean is_indexing = false;
    final String LAST_FILE = System.getProperty("user.dir") + "/elasticsearch/" + INDEX_NAME + ".last";
    @Autowired
    LocaleMessage localeMessage;
    @Autowired
    private BoardService boardService;
    private Properties lastFileProps = null;            // 마지막 색인값 보관
    private String file_path = null;                    // 첨부 파일 경로
    
    /**
     * 색인
     * 1. 게시판
     * 2. 댓글
     * 3. 첨부파일
     */
    @Scheduled(cron = "${batch.indexingFile.crone}")
    public void indexingFile() throws IOException {
        if(is_indexing) return;
        is_indexing = true;
        loadLastValue();
        file_path = System.getProperty("user.dir") + "/fileupload/"; //localeMessage.getMessage("info.filePath") + "/";  //  첨부 파일 경로
        
        // ---------------------------- elasticsearch connection --------------------------------
        //RestHighLevelClient client = createConnection();
        EsConfig esConfig = new EsConfig();
        RestHighLevelClient client = esConfig.client();
        
        // ---------------------------- 게시판 변경글 --------------------------------
        String brdno_update = getLastValue("brd_update"); // 변경색인 인덱스
        String brdno = getLastValue("brd"); // 이전 색인시 마지막 일자
        
        List<BoardVO> boardlist = null;
        
        if(!brdno_update.equals(brdno)) {
            boardlist = (List<BoardVO>) boardService.selectBoards4Indexing(brdno_update);
            UpdateRequest updateRequest;
            for (BoardVO el : boardlist) {
                brdno_update = el.getBrdno();
                updateRequest = new UpdateRequest()
                        .index(INDEX_NAME)
                        .id(el.getBrdno())
                        .doc(jsonBuilder()
                                .startObject()
                                .field("bgno", el.getBgno())
                                .field("brdno", brdno_update)
                                .field("brdtitle", el.getBrdtitle())
                                .field("brdmemo", el.getBrdmemo())
                                .field("brdwriter", el.getUsernm())
                                .field("userno", el.getUserno())
                                .field("brddate", el.getBrddate())
                                .field("brdtime", el.getBrdtime())
                                .field("brdhit", el.getBrdhit())
                                .endObject());
                
                try {
                    client.update(updateRequest, RequestOptions.DEFAULT);
                } catch (IOException | ElasticsearchStatusException e) {
                    logger.error("indexRequest : " + e);
                }
            }
            
            if(boardlist.size() > 0) {
                writeLastValue("brd_update", brdno_update); // 마지막 색인 이후의 댓글/ 첨부파일 중에서 게시글이 색인 된 것만 색인 해야 함. SQL문에서 field1참조  => logtash를 쓰지 않고 개발한 이유
            }
            logger.info("board indexed update : " + boardlist.size());
            boardlist.clear();
            boardlist = null;
        }
        
        
        // ---------------------------- 게시판 신규글 --------------------------------
        boardlist = (List<BoardVO>) boardService.selectBoards4Indexing(brdno);
        for (BoardVO el : boardlist) {
            brdno = el.getBrdno();
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME)
                    .id(el.getBrdno())
                    .source("bgno", el.getBgno(),
                            "brdno", brdno,
                            "brdtitle", el.getBrdtitle(),
                            "brdmemo", el.getBrdmemo(),
                            "brdwriter", el.getUsernm(),
                            "userno", el.getUserno(),
                            "brddate", el.getBrddate(),
                            "brdtime", el.getBrdtime(),
                            "brdhit", el.getBrdhit()
                    );
            
            try {
                client.index(indexRequest, RequestOptions.DEFAULT);
            } catch (IOException | ElasticsearchStatusException e) {
                logger.error("indexRequest : " + e);
            }
        }
        
        if(boardlist.size() > 0) {
            writeLastValue("brd", brdno); // 마지막 색인 이후의 댓글/ 첨부파일 중에서 게시글이 색인 된 것만 색인 해야 함. SQL문에서 field1참조  => logtash를 쓰지 않고 개발한 이유
        }
        
        
        logger.info("board indexed : " + boardlist.size());
        boardlist.clear();
        boardlist = null;
        
        // ---------------------------- 댓글 --------------------------------
        Field3VO lastVO = new Field3VO(); // 게시판, 댓글, 파일의 마지막 색인 값
        lastVO.setField1(brdno);
        lastVO.setField2(getLastValue("reply"));
        
        List<BoardReplyVO> replylist = (List<BoardReplyVO>) boardService.selectBoardReply4Indexing(lastVO);
        
        String reno = "";
        for (BoardReplyVO el : replylist) {
            reno = el.getReno();
            Map<String, Object> replyMap = new HashMap<String, Object>();
            replyMap.put("reno", reno);
            replyMap.put("redate", el.getRedate());
            replyMap.put("rememo", el.getRememo());
            replyMap.put("usernm", el.getUsernm());
            replyMap.put("userno", el.getUserno());
            
            Map<String, Object> singletonMap = Collections.singletonMap("reply", replyMap);
            
            UpdateRequest updateRequest = new UpdateRequest()
                    .index(INDEX_NAME)
                    .id(el.getBrdno())
                    .script(new Script(ScriptType.INLINE, "painless", "if (ctx._source.brdreply == null) {ctx._source.brdreply=[]} ctx._source.brdreply.add(params.reply)", singletonMap));
            
            try {
                client.update(updateRequest, RequestOptions.DEFAULT);
            } catch (IOException | ElasticsearchStatusException e) {
                logger.error("updateCommit : " + e);
            }
        }
        
        if(replylist.size() > 0) {
            writeLastValue("reply", reno); // 마지막 색인  정보 저장 - 댓글
        }
        
        logger.info("board reply indexed : " + replylist.size());
        replylist.clear();
        replylist = null;
        
        // ---------------------------- 첨부파일 --------------------------------
        lastVO.setField2(getLastValue("file"));
        List<FileVO> filelist = (List<FileVO>) boardService.selectBoardFiles4Indexing(lastVO);
        
        String fileno = "";
        for (FileVO el : filelist) {
            if(!FILE_EXTENTION.contains(FileUtil.getFileExtension(el.getFilename()))) continue; // 지정된 파일 형식만 색인
            
            fileno = el.getFileno().toString();
            Map<String, Object> fileMap = new HashMap<String, Object>();
            fileMap.put("fileno", fileno);
            fileMap.put("filememo", extractTextFromFile(el.getRealname()));
            
            Map<String, Object> singletonMap = Collections.singletonMap("file", fileMap);
            
            UpdateRequest updateRequest = new UpdateRequest()
                    .index(INDEX_NAME)
                    .id(el.getParentPK())
                    .script(new Script(ScriptType.INLINE, "painless", "if (ctx._source.brdfiles == null) {ctx._source.brdfiles=[]} ctx._source.brdfiles.add(params.file)", singletonMap));
            try {
                client.update(updateRequest, RequestOptions.DEFAULT);
            } catch (IOException | ElasticsearchStatusException e) {
                logger.error("updateCommit : " + e);
            }
        }
        if(filelist.size() > 0) {
            writeLastValue("file", fileno); // 마지막 색인  정보 저장 - 댓글
        }
        
        logger.info("board files indexed : " + filelist.size());
        filelist.clear();
        filelist = null;
        
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        is_indexing = false;
    }
    
    /**
     * tika로 오피스 등에서 텍스트 추출
     *
     * @param filename
     * @return
     */
    private String extractTextFromFile(String filename) {
        
        File file = new File(file_path + filename.substring(0, 4) + "/" + filename);
        if(!file.exists()) {
            logger.error("file not exists : " + filename);
            return "";
        }
        String text = "";
        Tika tika = new Tika();
        try {
            text = tika.parseToString(file);            // binary contents => text contents
        } catch (IOException | TikaException e) {
            e.printStackTrace();
        }
        return text;
    }
    
    // ---------------------------------------------------------------------------
    
    /**
     * 처리한 마지막 값(날짜)을 저장한 파일 열고 읽기
     */
    private void loadLastValue() {
        lastFileProps = new Properties();
        try {
            lastFileProps.load(new FileInputStream(LAST_FILE));
        } catch (IOException e) {
            logger.error("" + e);
        }
    }
    
    /**
     * 처리한 마지막 값(날짜) 쓰기
     *
     * @param key
     * @param value
     */
    private void writeLastValue(String key, String value) {
        lastFileProps.setProperty(key, value);    // 마지막 색인 일자 저장
        
        try {
            FileOutputStream lastFileOut = new FileOutputStream(LAST_FILE);
            lastFileProps.store(lastFileOut, "Last Indexing");
            lastFileOut.close();
        } catch (IOException e) {
            logger.error("writeLastValue : " + e);
        }
    }
    
    /**
     * 데이터 종류별 마지막 값 반환
     *
     * @param key
     * @return
     */
    private String getLastValue(String key) {
        String value = lastFileProps.getProperty(key);
        if(value == null) value = "0";
        return value;
    }
    
    // ---------------------------------------------------------------------------
    /*
    // 공통 환경으로 변경
    public RestHighLevelClient createConnection() {
        final CredentialsProvider credentialProvider = new BasicCredentialsProvider();
        credentialProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "manager"));

        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                ).setHttpClientConfigCallback(
                        httpAsyncClientBuilder -> {
                            HttpAsyncClientBuilder httpAsyncClientBuilder1 = httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialProvider);
                            return httpAsyncClientBuilder1;
                        }
                )
        );
    }
     */
    // ---------------------------------------------------------------------------
}
