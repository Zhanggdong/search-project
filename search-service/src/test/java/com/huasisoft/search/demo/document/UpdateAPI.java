package com.huasisoft.search.demo.document;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.update.UpdateRequest;
import org.junit.Test;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-27.
 * @Time 11:11
 * @Description 更新API
 * @Version 2.0.0
 */
public class UpdateAPI extends BaseTest{
    /**
     * 使用 UpdateRequest 操作
     *
     * @throws Exception
     */
    @Test
    public void testForUpdateRequest() throws Exception{
        UpdateRequest request = new UpdateRequest();
        request.index("twitter");
        request.type("tweet");
        request.id("1");
        request.doc(jsonBuilder()
                .startObject()
                .field("user", "http://quanke.name")
                .endObject()
        );
        transportClient.update(request).get();
    }

    /**
     * 使用prepareUpdate
     *
     * @throws Exception
     */
    @Test
    public void testForPrepareUpdate() throws Exception {
//        client.prepareUpdate("twitter", "tweet", "2")
//                .setScript(new Script(ScriptType.INLINE, "ctx._source.user = \"quanke.name\"", null, null))
//                .get();

//        client.prepareUpdate("twitter", "tweet", "2")
//                .setDoc(jsonBuilder()
//                        .startObject()
//                        .field("user", "quanke.name")
//                        .endObject())
//                .get();

    }
}
