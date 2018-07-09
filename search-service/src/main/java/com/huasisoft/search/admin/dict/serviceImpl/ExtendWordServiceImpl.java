package com.huasisoft.search.admin.dict.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.admin.dict.constant.DictChannel;
import com.huasisoft.search.admin.dict.execption.ExtendWordException;
import com.huasisoft.search.admin.dict.model.ExtendWord;
import com.huasisoft.search.admin.dict.repository.ExtendWordRepository;
import com.huasisoft.search.admin.dict.service.ExtendWordService;
import com.huasisoft.search.admin.dict.service.PubSubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-29.
 * @Time 10:11
 * @Description 扩展词接口实现
 * @Version 2.0.0
 */
@com.alibaba.dubbo.config.annotation.Service
@Service("extendWordService")
public class ExtendWordServiceImpl implements ExtendWordService {
    private static final Logger logger = LoggerFactory.getLogger(ExtendWordServiceImpl.class);
    @Autowired
    private PubSubService pubSubService;
    @Autowired
    private ExtendWordRepository extendWordRepository;

    @Override
    public boolean save(ExtendWord extendWord) throws ExtendWordException {
        try {
            extendWord = extendWordRepository.save(extendWord);
            String data = JSONObject.toJSONString(extendWord);
            // 异步发送订阅通道
            pubSubService.publishAsync(DictChannel.EXTEND_WORD_CHANNEL,data);
            logger.info("添加扩展词成功:{}",extendWord.toString());
        }catch (Exception e){
            logger.info("添加扩展词失败:{}",extendWord.toString());
            throw new ExtendWordException("添加扩展词失败!");
        }
        return false;
    }

    @Override
    public boolean removeByContent(String content) throws ExtendWordException {
        boolean removed = false;
        try {
            removed = extendWordRepository.removeByContent(content);
            logger.info("删除扩展词成功:{}",content);
        } catch (Exception e) {
            logger.info("删除扩展词失败:{}",content);
            throw new ExtendWordException("删除扩展词失败!");
        }
        return removed;
    }

    @Override
    public boolean removeByContentList(List<String> contents) throws ExtendWordException {
        boolean removed = false;
        try {
            removed = extendWordRepository.removeByContent(contents);
            logger.info("批量删除扩展词成功:{}",contents);
        } catch (Exception e) {
            logger.info("批量删除扩展词失败:{}",contents);
            throw new ExtendWordException("批量删除扩展词失败!");
        }
        return removed;
    }

    @Override
    public List<ExtendWord> query(int pageNum, int pageSize) throws ExtendWordException {
        List<ExtendWord> extendWords = null;
        try {
            extendWords = extendWordRepository.query(pageNum,pageSize);
            logger.info("分页查询同义词未:{}",extendWords.toString());
        } catch (Exception e) {
            logger.info("查询除同义词失败");
            throw new ExtendWordException("分页查询同义词失败!");
        }
        return extendWords;
    }
}
