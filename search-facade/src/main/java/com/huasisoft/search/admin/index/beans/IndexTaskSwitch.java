package com.huasisoft.search.admin.index.beans;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-04.
 * @Time 17:03
 * @Description 索引构建状态开关
 * @Version 2.0.0
 */
public class IndexTaskSwitch {
    /**
     * 公文索引构建任务状态开关
     */
    private AtomicBoolean officeTaskSwitch = new AtomicBoolean(true);
    /**
     * 正文索引构建任务状态开关
     */
    private AtomicBoolean documentTaskSwitch = new AtomicBoolean(true);
    /**
     * 意见索引任务状态开关
     */
    private AtomicBoolean commnetTaskSwitch = new AtomicBoolean(true);
    /**
     * 附件索引任务状态开关
     */
    private AtomicBoolean attachmentTaskSwitch = new AtomicBoolean(true);

    private boolean initialized = false;

    private IndexTaskSwitch(){
        synchronized (IndexTaskSwitch.class){
            if(initialized == false){
                initialized = !initialized;
            }else{
                throw new RuntimeException("单例已被侵犯");
            }
        }
    }

    //默认不加载
    private static class LazyHolder{
        private static final IndexTaskSwitch LAZY = new IndexTaskSwitch();
    }

    public static final IndexTaskSwitch getInstance(){
        //在返回结果以前，一定会先加载内部类
        return LazyHolder.LAZY;
    }

    public AtomicBoolean getOfficeTaskSwitch() {
        return officeTaskSwitch;
    }

    public void setOfficeTaskSwitch(boolean taskSwitch) {
        this.officeTaskSwitch.set(taskSwitch);
    }

    public AtomicBoolean getDocumentTaskSwitch() {
        return documentTaskSwitch;
    }

    public void setDocumentTaskSwitch(boolean taskSwitch) {
        this.documentTaskSwitch.set(taskSwitch);
    }

    public AtomicBoolean getCommnetTaskSwitch() {
        return commnetTaskSwitch;
    }

    public void setCommnetTaskSwitch(boolean taskSwitch) {
        this.commnetTaskSwitch.set(taskSwitch);
    }

    public AtomicBoolean getAttachmentTaskSwitch() {
        return attachmentTaskSwitch;
    }

    public void setAttachmentTaskSwitch(boolean taskSwitch) {
        this.attachmentTaskSwitch.set(taskSwitch);
    }
}
