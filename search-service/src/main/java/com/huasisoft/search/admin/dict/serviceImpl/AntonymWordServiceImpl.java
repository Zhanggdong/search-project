package com.huasisoft.search.admin.dict.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.admin.dict.constant.DictChannel;
import com.huasisoft.search.admin.dict.execption.SynonymWordException;
import com.huasisoft.search.admin.dict.model.SynonymWord;
import com.huasisoft.search.admin.dict.repository.SynonymWordRepository;
import com.huasisoft.search.admin.dict.service.AntonymWordService;
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
 * @Time 10:07
 * @Description 同义词扩展接口实现
 * @Version 2.0.0
 */
@com.alibaba.dubbo.config.annotation.Service
@Service("antonymWordService")
public class AntonymWordServiceImpl implements AntonymWordService {
    private static final Logger logger = LoggerFactory.getLogger(AntonymWordServiceImpl.class);

    @Autowired
    private PubSubService pubSubService;
    @Autowired
    private SynonymWordRepository synonymWordRepository;

    @Override
    public boolean save(SynonymWord synonymWord) throws SynonymWordException {
        try {
            synonymWord = synonymWordRepository.save(synonymWord);
            String data = JSONObject.toJSONString(synonymWord);
            // 异步发送订阅通道
            pubSubService.publishAsync(DictChannel.SYNONYM_WORD_CHANNEL,data);
            logger.info("添加同义词成功:{}",synonymWord.toString());
        }catch (Exception e){
            logger.info("添加同义词失败:{}",synonymWord.toString());
            throw new SynonymWordException("添加同义词失败!");
        }
        return false;
    }

    @Override
    public boolean removeByContent(String content) throws SynonymWordException{
        boolean removed = false;
        try {
            removed = synonymWordRepository.removeByContent(content);
            logger.info("删除同义词成功:{}",content);
        } catch (Exception e) {
            logger.info("删除同义词失败:{}",content);
            throw new SynonymWordException("删除同义词失败!");
        }
        return removed;
    }

    @Override
    public boolean remove(List<String> contents) throws SynonymWordException {
        boolean removed = false;
        try {
            removed = synonymWordRepository.removeByContent(contents);
            logger.info("批量删除同义词成功:{}",contents);
        } catch (Exception e) {
            logger.info("批量删除同义词失败:{}",contents);
            throw new SynonymWordException("删除同义词失败!");
        }
        return removed;
    }

    @Override
    public List<SynonymWord> query(int pageNum, int pageSize) throws SynonymWordException{

        List<SynonymWord> synonymWords = null;
        try {
            synonymWords = synonymWordRepository.query(pageNum,pageSize);
            logger.info("分页查询同义词未:{}",synonymWords.toString());
        } catch (Exception e) {
            logger.info("查询除同义词失败");
            throw new SynonymWordException("分页查询同义词失败!");
        }
        return synonymWords;
    }
}
