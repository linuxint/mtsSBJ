package com.devkbil.mtssbj.search;

import com.devkbil.mtssbj.common.util.DateUtil;
import com.devkbil.mtssbj.common.config.EsConfig;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SearchController {
    
    static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
    static final Integer DISPLAY_COUNT = 5;
    @Value("${elasticsearch.clustername}")
    static final String INDEX_NAME = "mts";
    static final String[] HIGHLIGHT_FIELDS = {"brdwriter", "brdtitle", "brdmemo"};
    static final String[] INCLUDE_FIELDS = new String[]{"brdno", "userno", "brddate", "brdtime", "brdtitle", "brdwriter", "brdmemo", "brdhit"}; // 값을 가지고 올 필드

    @RequestMapping(value = "/search")
    public String search(HttpServletRequest request, ModelMap modelMap) {
        String today = DateUtil.date2Str(DateUtil.getToday());
        
        modelMap.addAttribute("today", today);
        
        return "search/search";
    }
    
    /*
     * 검색
     * @param searchVO: 검색 조건.
     * @return SearchResponse: ES가  전송한 검색 결과. Java에서는 받은 값을 그대로 client(ajax)에 전송. 모든 처리를 JS에서 진행
     */
    @RequestMapping(value = "/search4Ajax")
    public void search4Ajax(HttpServletRequest request, HttpServletResponse response, FullTextSearchVO searchVO) {
        if("".equals(searchVO.getSearchKeyword())) return;
        
        String[] searchRange = searchVO.getSearchRange().split(",");                // 검색 대상 필드 - 작성자, 제목, 내용 등
        
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.highlighter(makeHighlightField(searchRange));        // 검색 대상 필드에 대해서 하이라이트(댓글, 첨부파일은 제외)
        searchSourceBuilder.fetchSource(INCLUDE_FIELDS, null);
        searchSourceBuilder.from((searchVO.getPage() - 1) * DISPLAY_COUNT);                            // 페이징
        searchSourceBuilder.size(DISPLAY_COUNT);
        searchSourceBuilder.sort(new FieldSortBuilder("_score").order(SortOrder.DESC));                // 정렬
        searchSourceBuilder.sort(new FieldSortBuilder("brdno").order(SortOrder.DESC));
        
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("gujc").field("bgno");        // 그룹별(게시판) 개수
        searchSourceBuilder.aggregation(aggregation);
        
        searchSourceBuilder.query(makeQuery(searchRange, searchVO.getSearchKeyword().split(" "), searchVO));    // 검색식 작성
        
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        
        RestHighLevelClient client = null;
        SearchResponse searchResponse = null;
        try {
            //client = createConnection();
            EsConfig esConfig = new EsConfig();
            client = esConfig.client();
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().print(searchResponse.toString()); //ES가  전송한 검색 결과를 그대로 client(ajax)에 전송. 모든 처리를 JS에서 진행
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * 검색식 작성
     * @param fields: 검색할 필드.
     * @param words: 검색할 키워드.
     * @param searchVO: 검색 조건. 데이터 소스 종류(searchType), 날짜(getSearchTerm).
     */
    private BoolQueryBuilder makeQuery(String[] fields, String[] words, FullTextSearchVO searchVO) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        
        String searchType = searchVO.getSearchType();
        if(searchType != null & !"".equals(searchType)) {
            qb.must(QueryBuilders.termQuery("bgno", searchType));
        }
        
        if(!"a".equals(searchVO.getSearchTerm())) {                            // 기간 검색
            qb.must(QueryBuilders.rangeQuery("brddate").from(searchVO.getSearchTerm1()).to(searchVO.getSearchTerm2()));
        }
        
        for (String word : words) {            // 검색 키워드
            word = word.trim().toLowerCase();
            if("".equals(word)) continue;
            
            BoolQueryBuilder qb1 = QueryBuilders.boolQuery();
            for (String fld : fields) {                            // 입력한 키워드가 지정된 모든 필드에 있는지 조회
                if("brdreply".equals(fld)) {        // 댓글은 nested로 저장되어 있어 별도로 작성
                    qb1.should(QueryBuilders.nestedQuery("brdreply", QueryBuilders.boolQuery().must(QueryBuilders.termQuery("brdreply.rememo", word)), ScoreMode.None));
                } else if("brdfiles".equals(fld)) {        // 첨부 파일은  nested로 저장되어 있어 별도로 작성
                    qb1.should(QueryBuilders.nestedQuery("brdfiles", QueryBuilders.boolQuery().must(QueryBuilders.termQuery("brdfiles.filememo", word)), ScoreMode.None));
                } else {
                    qb1.should(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(fld, word)));
                }
            }
            
            qb.must(qb1);            // 검색 키워드가 여러개일 경우 and로 검색
        }
        
        return qb;
    }
    
    /*
     * Highlight 필드 지정
     * @param fields: 필드
     */
    private HighlightBuilder makeHighlightField(String[] fields) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        
        for (String fld : fields) {
            if("brdreply".equals(fld) || "brdfiles".equals(fld))
                continue;    // 댓글, 첨부파일은 하이라이트 안함. 댓글, 첨부파일이 검색되어도 부모글이 출력되기 때문
            HighlightBuilder.Field hField = new HighlightBuilder.Field(fld);
            highlightBuilder.field(hField);
        }
        
        return highlightBuilder;
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
