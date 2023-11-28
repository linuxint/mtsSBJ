package com.devkbil.mtssbj.search;

import com.devkbil.mtssbj.board.BoardReplyVO;
import com.devkbil.mtssbj.board.BoardService;
import com.devkbil.mtssbj.board.BoardVO;
import com.devkbil.mtssbj.common.ExtFieldVO;
import com.devkbil.mtssbj.common.LocaleMessage;
import com.devkbil.mtssbj.common.util.FileUtil;
import com.devkbil.mtssbj.common.util.FileVO;
import com.devkbil.mtssbj.common.util.HostUtil;
import com.devkbil.mtssbj.config.EsConfig;
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
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static org.elasticsearch.xcontent.XContentFactory.jsonBuilder;

@Controller
@EnableAsync
@EnableScheduling
@Configuration
public class IndexingController {

    @Value("${batch.indexing.host}")
    private final String indexingHost = "DESKTOP-288GLL6";
    @Value("${elasticsearch.clustername}")
    private final String indexName = "mts";
    private final String lastFile = System.getProperty("user.dir") + "/elasticsearch/" + indexName + ".last";
    //final String LAST_FILE = localeMessage.getMessage("info.workspace") + "/elasticsearch/mts.last";
    @Value("${batch.indexing.file_ext}")
    private final String fileExtention = "";
    private final Logger logBatch = LoggerFactory.getLogger("BATCH");
    @Autowired
    LocaleMessage localeMessage;
    private boolean isIndexing = false;
    @Autowired
    private BoardService boardService;
    private Properties lastFileProps = null;            // 마지막 색인값 보관

    private String filePath = null;                    // 첨부 파일 경로

    /**
     * 색인
     * 1. 게시판
     * 2. 댓글
     * 3. 첨부파일
     */
    @Scheduled(cron = "${batch.indexingFile.crone}")
    public void indexingFile() throws IOException {

        // indexing host check
        if (!HostUtil.hostCheck(indexingHost)) {
            return;
        }

        if (isIndexing) {
            return;
        }
        isIndexing = true;
        loadLastValue();
        filePath = System.getProperty("user.dir") + "/fileupload/"; //localeMessage.getMessage("info.filePath") + "/";  //  첨부 파일 경로

        // ---------------------------- elasticsearch connection --------------------------------
        //RestHighLevelClient client = createConnection();
        EsConfig esConfig = new EsConfig();
        RestHighLevelClient client = esConfig.client();

        // ---------------------------- 게시판 변경글 --------------------------------
        String brdnoUpdate = getLastValue("brd_update"); // 변경색인 인덱스
        String brdno = getLastValue("brd"); // 이전 색인시 마지막 일자

        List<BoardVO> boardlist;

        if (!brdnoUpdate.equals(brdno)) {
            boardlist = (List<BoardVO>)boardService.selectBoards4Indexing(brdnoUpdate);
            UpdateRequest updateRequest;
            for (BoardVO el : boardlist) {
                brdnoUpdate = el.getBrdno();
                updateRequest = new UpdateRequest()
                        .index(indexName)
                        .id(el.getBrdno())
                        .doc(jsonBuilder()
                                .startObject()
                                .field("bgno", el.getBgno())
                                .field("brdno", brdnoUpdate)
                                .field("brdtitle", el.getBrdtitle())
                                .field("brdmemo", el.getBrdmemo())
                                .field("brdwriter", el.getUsernm())
                                .field("userno", el.getUserno())
                                .field("regdate", el.getRegdate())
                                .field("regtime", el.getRegtime())
                                .field("brdhit", el.getBrdhit())
                                .endObject());

                try {
                    client.update(updateRequest, RequestOptions.DEFAULT);
                } catch (IOException | ElasticsearchStatusException e) {
                    logBatch.error("indexRequest : " + e.getMessage());
                }
            }

            if (!boardlist.isEmpty()) {
                // 마지막 색인 이후의 댓글/ 첨부파일 중에서 게시글이 색인 된 것만 색인 해야 함. SQL문에서 field1참조  => logtash를 쓰지 않고 개발한 이유
                writeLastValue("brd_update", brdnoUpdate);
            }
            logBatch.info("board indexed update : " + boardlist.size());
            boardlist.clear();
        }

        // ---------------------------- 게시판 신규글 --------------------------------
        boardlist = (List<BoardVO>)boardService.selectBoards4Indexing(brdno);
        for (BoardVO el : boardlist) {
            brdno = el.getBrdno();
            IndexRequest indexRequest = new IndexRequest(indexName)
                    .id(el.getBrdno())
                    .source("bgno", el.getBgno(),
                            "brdno", brdno,
                            "brdtitle", el.getBrdtitle(),
                            "brdmemo", el.getBrdmemo(),
                            "brdwriter", el.getUsernm(),
                            "userno", el.getUserno(),
                            "regdate", el.getRegdate(),
                            "regtime", el.getRegtime(),
                            "brdhit", el.getBrdhit()
                    );

            try {
                client.index(indexRequest, RequestOptions.DEFAULT);
            } catch (IOException | ElasticsearchStatusException e) {
                logBatch.error("indexRequest : " + e.getMessage());
            }
        }

        if (!boardlist.isEmpty()) {
            writeLastValue("brd",
                    brdno); // 마지막 색인 이후의 댓글/ 첨부파일 중에서 게시글이 색인 된 것만 색인 해야 함. SQL문에서 field1참조  => logtash를 쓰지 않고 개발한 이유
        }

        logBatch.info("board indexed : " + boardlist.size());
        boardlist.clear();

        // ---------------------------- 댓글 --------------------------------
        ExtFieldVO lastVO = new ExtFieldVO(); // 게시판, 댓글, 파일의 마지막 색인 값
        lastVO.setField1(brdno);
        lastVO.setField2(getLastValue("reply"));

        List<BoardReplyVO> replylist = (List<BoardReplyVO>)boardService.selectBoardReply4Indexing(lastVO);

        String reno = "";
        Map<String, Object> replyMap = new ConcurrentHashMap<String, Object>();
        for (BoardReplyVO el : replylist) {
            reno = el.getReno();
            replyMap.put("reno", reno);
            replyMap.put("regdate", el.getRegdate());
            replyMap.put("rememo", el.getRememo());
            replyMap.put("usernm", el.getUsernm());
            replyMap.put("userno", el.getUserno());

            Map<String, Object> singletonMap = Collections.singletonMap("reply", replyMap);
            replyMap.clear();

            UpdateRequest updateRequest = new UpdateRequest()
                    .index(indexName)
                    .id(el.getBrdno())
                    .script(new Script(ScriptType.INLINE, "painless",
                            "if (ctx._source.brdreply == null) {ctx._source.brdreply=[]} ctx._source.brdreply.add(params.reply)",
                            singletonMap));

            try {
                client.update(updateRequest, RequestOptions.DEFAULT);
            } catch (IOException | ElasticsearchStatusException e) {
                logBatch.error("updateCommit : " + e.getMessage());
            }
        }

        if (!replylist.isEmpty()) {
            writeLastValue("reply", reno); // 마지막 색인  정보 저장 - 댓글
        }

        logBatch.info("board reply indexed : " + replylist.size());
        replylist.clear();

        // ---------------------------- 첨부파일 --------------------------------
        lastVO.setField2(getLastValue("file"));
        List<FileVO> filelist = (List<FileVO>)boardService.selectBoardFiles4Indexing(lastVO);

        String fileno = "";
        Map<String, Object> fileMap = new ConcurrentHashMap<String, Object>();
        for (FileVO el : filelist) {
            if (!fileExtention.contains(FileUtil.getFileExtension(el.getFilename()))) {
                continue; // 지정된 파일 형식만 색인
            }
            fileno = el.getFileno().toString();
            fileMap.put("fileno", fileno);
            fileMap.put("filememo", extractTextFromFile(el.getRealname()));

            Map<String, Object> singletonMap = Collections.singletonMap("file", fileMap);
            fileMap.clear();

            UpdateRequest updateRequest = new UpdateRequest()
                    .index(indexName)
                    .id(el.getParentPK())
                    .script(new Script(ScriptType.INLINE, "painless",
                            "if (ctx._source.brdfiles == null) {ctx._source.brdfiles=[]} ctx._source.brdfiles.add(params.file)",
                            singletonMap));
            try {
                client.update(updateRequest, RequestOptions.DEFAULT);
            } catch (IOException | ElasticsearchStatusException e) {
                logBatch.error("updateCommit : " + e.getMessage());
            }
        }
        if (!filelist.isEmpty()) {
            writeLastValue("file", fileno); // 마지막 색인  정보 저장 - 댓글
        }

        logBatch.info("board files indexed : " + filelist.size());
        filelist.clear();

        try {
            client.close();
        } catch (IOException e) {
            logBatch.error(e.getMessage());
        }
        isIndexing = false;
    }

    /**
     * tika로 오피스 등에서 텍스트 추출
     *
     * @param filename
     * @return
     */
    private String extractTextFromFile(String filename) {
        String realPath = FileUtil.getRealPath(filePath, filename);
        File file = new File(realPath + filename);
        if (!file.exists()) {
            logBatch.error("file not exists : " + filename);
            return "";
        }
        String text = "";
        Tika tika = new Tika();
        try {
            text = tika.parseToString(file);            // binary contents => text contents
        } catch (IOException e) {
            logBatch.error(String.valueOf(e));
        } catch (TikaException e) {
            throw new RuntimeException(e);
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
            FileInputStream lastFileIn = new FileInputStream(lastFile);
            lastFileProps.load(lastFileIn);
        } catch (IOException e) {
            logBatch.error(e.getMessage());
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
            FileOutputStream lastFileOut = new FileOutputStream(lastFile);
            lastFileProps.store(lastFileOut, "Last Indexing");
        } catch (IOException e) {
            logBatch.error("writeLastValue : " + e.getMessage());
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
        if (value == null) {
            value = "0";
        }
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
