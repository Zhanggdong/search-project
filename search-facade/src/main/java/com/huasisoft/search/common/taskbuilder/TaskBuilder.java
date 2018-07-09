package com.huasisoft.search.common.taskbuilder;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-23.
 * @Time 10:22
 * @Description 拆分任务处理类，
 * TODO 先不抽象
 * @Version 2.0.0
 */
public class TaskBuilder{
    /**
     * 数据总量
     */
    int total;
    /**
     * 总任务数量
     */
    int taskCount;
    /**
     *  当前执行到第几个任务
     */
    int currentTask;
    /**
     * 每次执行多条数据：需要从配置中获取
     */
    int perCount;
    // 默认每个线程执行 500次操作
    private static final Integer DEFAULT_PERCOUNT= 500;

    public TaskBuilder(int total) {
        this(total,DEFAULT_PERCOUNT);
    }

    public TaskBuilder(int total, int perCount) {
        this.total = total;
        this.perCount = perCount;
    }

    public int getTotal() {
        return total;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public int getCurrentTask() {
        return currentTask;
    }

    public int getPerCount() {
        return perCount;
    }

    public void setPerCount(int perCount) {
        this.perCount = perCount;
    }

    /**
     * 拆分任务
     */
    public void SplitTask(){
        taskCount = (total-1)/perCount+1;
    }
}
