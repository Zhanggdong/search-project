package com.huasisoft.search;


import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    int count =10;
    CountDownLatch latch = new CountDownLatch(count);
    @Test
    public void shouldAnswerWithTrue() throws InterruptedException {
        System.out.println("开始执行");
        for (int i=0;i<count;i++){
            testRun();
        }
        latch.await();
        System.out.println("主线程执行完毕");
    }

    public void testRun(){
        new Thread(()->{
            try {
                Thread.sleep(10000);
                System.out.println("子线程等待结束");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
