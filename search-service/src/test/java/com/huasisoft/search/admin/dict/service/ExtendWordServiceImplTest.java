package com.huasisoft.search.admin.dict.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.admin.dict.model.ExtendWord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 16:54
 * @Description TODO
 * @Version 2.0.0
 */
public class ExtendWordServiceImplTest extends BaseTest {
    @Autowired
    private ExtendWordService extendWordService;

    @Test
    public void save() throws Exception {
        ExtendWord extendWord = new ExtendWord();
        extendWord.setWord("你个基佬");
        extendWordService.save(extendWord);
    }

    @Test
    public void removeByContent() throws Exception {

    }

    @Test
    public void removeByContentList() throws Exception {
    }

    @Test
    public void query() throws Exception {
    }

}