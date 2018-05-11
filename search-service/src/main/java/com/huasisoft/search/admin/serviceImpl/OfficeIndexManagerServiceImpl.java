package com.huasisoft.search.admin.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huasisoft.search.admin.model.IndexManagerBeans;
import com.huasisoft.search.core.model.db.OfficeBase;
import com.huasisoft.search.core.model.es.ESBaseData;
import com.huasisoft.search.core.model.es.ESOfficeIndex;
import com.huasisoft.search.core.service.OfficeBaseService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 9:05
 * @Description 公文基本信息索引管理接口实现
 * @Version 2.0.0
 */
@Service("officeIndexManagerService")
public class OfficeIndexManagerServiceImpl extends AbstractIndexManagerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(OfficeIndexManagerServiceImpl.class);
    private static final String FORMAT_DATE =  "yyyy-MM-dd HH:mm:ss";
    @Autowired
    private OfficeBaseService officeBaseService;
    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    //索引名称
    private String indexName;
    // 索引类型
    private String typeName;

    private boolean setMapping() throws IOException {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("ID")
                .field("type","keyword")
                .endObject()
                .startObject("GUID")
                .field("type","keyword")
                .endObject()
                .startObject("title")
                .field("type","text")
                .field("store",true)
                .field("analyzer","ik_max_word")
                .field("search_analyzer","ik_max_word")
                .field( "copy_to","search")
                .endObject()
                .startObject("bwtype")
                .field("type","integer")
                .endObject()
                .startObject("banwenbianhao")
                .field("type","keyword")
                .field("store","true")
                .field( "copy_to","search")
                .endObject()
                .startObject("laiwendanwei")
                .field("type","text")
                .field("store",true)
                .field("analyzer","ik_max_word")
                .field("search_analyzer","ik_max_word")
                .field( "copy_to","search")
                .endObject()
                .startObject("createdate")
                .field("type","date")
                .field("format","yyyy-MM-dd")
                .field("store",true)
                .endObject()
                .startObject("createdatetime")
                .field("type","date")
                .field("format","yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                .field("store",true)
                .endObject()
                .startObject("creatorGUID")
                .field("type","keyword")
                .field("store",true)
                .endObject()
                .startObject("creatorname")
                .field("type","keyword")
                .field("store",true)
                .endObject()
                .startObject("dn")
                .field("type","keyword")
                .field("store",true)
                .endObject()
                .startObject("type")
                .field("type","integer")
                .field("store",true)
                .endObject()
                .startObject("bureauname")
                .field("type","keyword")
                .field("store",true)
                .endObject()
                .startObject("data")
                .field("type","text")
                .field("store",true)
                .field("analyzer","ik_max_word")
                .field("search_analyzer","ik_max_word")
                .field( "copy_to","search")
                .endObject()
                .startObject("departments")
                .field("type","nested")
                .startObject("properties")
                .startObject("workflowinstanceGUID")
                .field("type","keyword")
                .field("store",true)
                .endObject()
                .startObject("senduserGUID")
                .field("type","keyword")
                .field("store",true)
                .endObject()
                .startObject("sendusername")
                .field("type","keyword")
                .field("store",true)
                .endObject()
                .startObject("departmentname")
                .field("type","keyword")
                .field("store",true)
                .endObject()
                .startObject("departmentGUID")
                .field("type","keyword")
                .field("store",true)
                .endObject()
                .startObject("dn")
                .field("type","keyword")
                .field("store",true)
                .endObject()
                .endObject()
                .endObject()
                .endObject()
                .endObject();
        System.out.println(builder.toString());
        logger.info("Mapping{} is put to elasticsearch!",builder.toString());
        return setMapping(indexName, typeName, builder.toString());
    }

    /**
     * 初始化索引信息：设置索引名称，设置索引类型
     * @param beans
     */
    private void init(IndexManagerBeans beans) throws IOException {
        this.indexName = beans.getIndexName();
        this.typeName = beans.getTypeName();
        // 判断索引是否存在：
        boolean isIndexExists = existsIndexType(indexName);
        if (!isIndexExists){
            setMapping();
        }
    }
    @Override
    public boolean initIndexData(IndexManagerBeans beans) throws Exception {
        // 初始化
        init(beans);
        // 计算需要写入的公文数量：
        int total = officeBaseService.count();
        // 拆分任务
        if (total<=0){
            logger.info("没有查询到数据!");
            return true;
        }
        IndexManagerBeans.TaskBuilder builder = beans.builder(total);
        // 分隔计算任务
        builder.SplitTask();
        for (int pageNum=1;pageNum<=builder.getTaskCount();pageNum++){
            threadPoolTaskExecutor.execute(new OfficeTaskToESBuilder(pageNum,builder.getPerCount()));
        }
        return false;
    }

    protected class OfficeTaskToESBuilder implements Runnable{
        private int pageNum;
        private int pageSize;

        public OfficeTaskToESBuilder(int pageNum, int pageSize) {
            this.pageNum = pageNum;
            this.pageSize = pageSize;
        }

        @Override
        public void run() {
            try {
                logger.info("线程{}开始构建公文索引数据",Thread.currentThread().getName());
                List<OfficeBase> offices = officeBaseService.selectByPage(pageNum,pageSize);
                List<ESOfficeIndex> indices = wrap(offices);
                BulkRequestBuilder request = createBulkRequestBuilder(indices);
                BulkResponse bulkResponse = request.execute().get();
                logger.info("线程{}构建公文索引数据完成",Thread.currentThread().getName());
                if (bulkResponse.hasFailures()) {
                    // process failures by iterating through each bulk response item
                    //处理失败
                    logger.info("添加附件索引失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /**
         * 将数据库对象转换为ES索引数据对象
         * @param offices
         * @return
         */
        private List<ESOfficeIndex> wrap(List<OfficeBase> offices) {
            List<ESOfficeIndex> indices = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
            for (OfficeBase office:offices){
                String createtime = format.format(office.getCreatedatetime());
                ESOfficeIndex index = JSONObject.parseObject(JSONObject.toJSONString(office),ESOfficeIndex.class);
                index.setCreatedatetime(createtime);
                indices.add(index);
            }
            return indices;
        }

        private BulkRequestBuilder createBulkRequestBuilder( List<ESOfficeIndex> indices) throws IOException {
            BulkRequestBuilder request = client.prepareBulk();
            for (ESOfficeIndex index :indices){
                ObjectMapper mapper = new ObjectMapper();
                // 使用序列化方式
                byte[] json = mapper.writeValueAsBytes(index);
                request.add(client.prepareIndex(indexName,typeName,index.getID())
                        .setSource(json));
            /*request.add(client.prepareIndex(indexName,typeName,index.getID())
            .setSource(jsonBuilder()
                    .startObject()
                    .field("ID",index.getID())
                    .field("GUID",index.getGUID())
                    .field("title",index.getTitle())
                    .field("bwtype",index.getBwtype())
                    .field("banwenbianhao",index.getBanwenbianhao())
                    .field("laiwendanwei",index.getLaiwendanwei())
                    .field("createdate",index.getCreatedate())
                    .field("createdatetime",createtime)
                    .field("creatorGUID",index.getCreatorGUID())
                    .field("creatorname",index.getCreatorname())
                    .field("dn",index.getDn())
                    .field("type",index.getType())
                    .field("bureauname",index.getBureauname())
                    .startObject("departments")

                    .endObject()
                    .field("departments",index.getDepartments())
                    .field("data",index.getData())
                    .endObject()
            ));*/
            }
            return request;
        }
    }

}
