package com.huasisoft.search.core.serviceImpl;

import com.huasisoft.search.common.json.JsonResult;
import com.huasisoft.search.core.query.base.BaseQueryRule;
import com.huasisoft.search.core.query.search.HSHit;
import com.huasisoft.search.core.query.search.HSHitContext;
import com.huasisoft.search.core.query.search.HSSearchResponse;
import com.huasisoft.search.core.service.SearchService;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 14:29
 * @Description 统一查询接口实现
 * @Version 2.0.0
 */
@com.alibaba.dubbo.config.annotation.Service
@Service("searchService")
public class SearchServiceImpl implements SearchService {

    private final TransportClient transportClient;

    @Autowired
    public SearchServiceImpl(TransportClient transportClient) {
        this.transportClient = transportClient;
    }


    @Override
    public JsonResult<HSSearchResponse> search(BaseQueryRule queryRule) throws Exception {
        // 权限校验：以服务方式进行调用 （权限校验异常抛出 ）

        // 获取授权信息：以服务方式进行调用 （ 获取授权信息异常抛出）

        // 查询处理：有不同的查询选项，全部查询、公文基本信息查询、正文查询、附件查询，应该以不同的类来实现
        // 每一个查询方式基本上都是遵循固定的流程：权限校验+获取授权信息通过之后：
        // 1 设置查询方式:参数设置
        // 2 如果有过滤条件，则设置过滤条件
        // 3 如果有排序方式则设置排序字段
        // 4 设置高亮条件： TODO 这里对数字类型的字符会有坑，高亮内容变成了二进制流,猜猜可能是没有分词的原因
        // 5 执行查询
        // 6 封装结果返回前端
        // 如果这些步骤失败，则抛出查询失败异常
        //BooleanQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery().should().add(queryRule.getIndex(),);
        // 设置查询方式
        // 查询字段：
        String[] queryFileds = {"title","data","laiwendanwei"};
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(queryRule.getQuery(),queryFileds);
        // 设置高亮字段：默认用红色表示
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .preTags("<span style=\"color:red\">")
                .postTags("</span>")
                .fragmentSize(150)
                .numOfFragments(3)
                .noMatchSize(150);
        for (String field:queryRule.getHighLightFields()){
            highlightBuilder.field(field);
        }
        // 查询的索引类型
        String[] indexTypes = queryRule.getType();
        // 执行查询
        String[] indexs = queryRule.getIndexs();
        // 需要验证索引类型不合法，则不能查询
        SearchResponse searchResponse = transportClient.prepareSearch(indexs)
                //.setTypes("office")
                .setQuery(queryBuilder)
                .highlighter(highlightBuilder)
                .setFrom((queryRule.getPageNum()-1)*queryRule.getPageSize())
                .setSize(queryRule.getPageSize())
                .execute()
                .get();
        // 封装查询结果
        HSSearchResponse response = process(searchResponse,queryRule.getHighLightFields());
        // 返回数据
        return new JsonResult<HSSearchResponse>(response);
    }

    private HSSearchResponse process(SearchResponse searchResponse, String[] highLightFields) {
        HSSearchResponse response = new HSSearchResponse();
        HSHitContext hitContext = new HSHitContext();
        SearchHits hits = searchResponse.getHits();
        hitContext.setTotal(hits.totalHits);
        hitContext.setMax_score(hits.getMaxScore());
        List<HSHit> hsHits = new ArrayList<>();
        for (SearchHit hit:hits){
            HSHit hsHit = new HSHit();
            hsHit.set_id(hit.getId());
            hsHit.set_index(hit.getIndex());
            hsHit.set_score(hit.getScore());
            hsHit.set_source(hit.getSource());
            hsHit.setHighlight(wrapHighlightField(highLightFields, hit));
            hsHits.add(hsHit);
        }
        hitContext.setHits(hsHits);
        response.setHits(hitContext);
        return response;
    }

    /**
     * 处理高亮信息，将Elasticsearch内部的高亮信息封装成自定义的高亮信息
     * @param highLightFields
     * @param hit
     * @return
     */
    private Map<String,Object> wrapHighlightField(String[] highLightFields, SearchHit hit) {
        Map<String,Object> highlightFiledMap = new ConcurrentHashMap<String,Object>();
        for (String field:highLightFields){
            HighlightField hTitle = hit.getHighlightFields().get(field);
            if (hTitle != null) {
                Text[] fragments = hTitle.fragments();
                String hTitleStr = "";
                for (Text text : fragments) {
                    hTitleStr += text;
                }
                highlightFiledMap.put(field, hTitleStr);
            }
        }
        return highlightFiledMap;
    }
}
