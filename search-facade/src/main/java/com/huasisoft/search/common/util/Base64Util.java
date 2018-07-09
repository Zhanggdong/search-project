package com.huasisoft.search.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-08.
 * @Time 10:29
 * @Description 使用NIO编写的Base64工具类，解决BIO消耗内存过多的问题
 * 该类使用静态内部类的单例模式，能达到Lazy效果和保证线程安全
 * @Version 2.0.0
 */
public class Base64Util {
    private static final Logger logger = LoggerFactory.getLogger(Base64Util.class);
    private final Base64.Decoder m_decoder = Base64.getDecoder();
    private final Base64.Encoder m_encoder = Base64.getEncoder();
    // 是否已经初始化过
    private boolean initialized = false;

    private final Base64.Decoder decoder = Base64.getDecoder();

    private final Base64.Encoder encoder = Base64.getEncoder();

    private Base64Util(){
        // 防止多线程环境下反射污染单例
        synchronized (Base64Util.class){
            if (initialized==false){
                initialized = !initialized;
            }else {
                throw new RuntimeException("单例已被侵犯");
            }
        }
    }

    private static class LazyHolder{
        private static final Base64Util INSTANCE = new Base64Util();
    }

    public static Base64Util getInstance(){
        //在返回结果以前，一定会先加载内部类
        return LazyHolder.INSTANCE;
    }

    public String endcode(byte[] data){
        return m_encoder.encodeToString(data);
    }

    public String encodeFile(String filePath){
        long start = System.currentTimeMillis();
        String result = new String();
        FileInputStream fis = null;
        FileChannel channel = null;
        try {
            File file = new File(filePath);
            if (!file.exists()){
                return null;
                //throw new FileNotFoundException("文件不存在！");
            }
            fis = new FileInputStream(file);
            channel = fis.getChannel();
            long size = channel.size();
            // 构建一个只读的MappedByteBuffer
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
            byte[] bytes = new byte[(int)size];
            mappedByteBuffer.get(bytes, 0, (int)size);
            result = endcode(bytes);
        }catch (FileNotFoundException e){
            logger.info("没有找到文件:{}",filePath);
        } catch (IOException e) {
            logger.info("{}->读取文件内容失败!",filePath);
            // 重试
            encodeFile(filePath);
        }finally {
            // 关闭通道和文件流
            try {
                if (channel!=null){
                    channel.close();
                }
                if (fis!=null) {
                    fis.close();
                }
            } catch (IOException e) {
                logger.info("资源关闭失败!");
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        logger.info("读取文件用时:{}毫秒",(end-start));
        return result;
    }

    public String encodeFile(String filePath,int allocate){
        long start = System.currentTimeMillis();
        String result = new String();
        FileInputStream fis = null;
        FileChannel channel = null;
        try {
            File file = new File(filePath);
            if (!file.exists()){
                throw new FileNotFoundException("文件不存在！");
            }
            fis = new FileInputStream(file);
            channel = fis.getChannel();
            long size = channel.size();
            // 构建一个只读的MappedByteBuffer
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
            // 如果文件内容很大,可以循环读取,计算应该读取多少次
            byte[] bytes = new byte[allocate];
            long cycle = size / allocate;
            int mode = (int)(size % allocate);
            for (int i = 0; i < cycle; i++) {
                // 每次读取allocate个字节
                mappedByteBuffer.get(bytes);
                // 打印文件内容,关闭打印速度会很快
                // System.out.print(new String(eachBytes));
            }
            if(mode > 0) {
                bytes = new byte[mode];
                mappedByteBuffer.get(bytes);
                // 打印文件内容,关闭打印速度会很快
                // System.out.print(new String(eachBytes));
            }
            result = endcode(bytes);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            logger.info("没有找到文件!");
        } catch (IOException e) {
            logger.info("读取文件内容失败!");
            encodeFile(filePath,allocate);
        }finally {
            // 关闭通道和文件流
            try {
                if (channel!=null){
                    channel.close();
                }
                if (fis!=null) {
                    fis.close();
                }
            } catch (IOException e) {
                logger.info("资源关闭失败!");
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        logger.info("读取文件用时:{} 毫秒",(end-start));
        return result;
    }

    /**
     * 异步读取文件进行编码
     * @param filePath
     * @return
     */
    public String endFileAsync(String filePath){
        final String[] encodeResult = {new String()};
        Path path = Paths.get(filePath);
        if(!Files.exists(path)){
            return null;
        }
        AsynchronousFileChannel fileChannel = null;
        try {
            fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
            long allocate = fileChannel.size();
            // 这里可能会范围越级
            ByteBuffer buffer = ByteBuffer.allocate(new Long(allocate).intValue());

            long position = 0;
            fileChannel.read(buffer,position,buffer,new CompletionHandler<Integer, ByteBuffer>(){
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    attachment.flip();
                    byte[] data = new byte[attachment.limit()];
                    attachment.get(data);
                    encodeResult[0] = endcode(data);
                    System.out.println(new String(data));
                    attachment.clear();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    logger.info("文件读取失败！");
                }
            });
        }catch (IOException e){

        }
        return encodeResult[0];
    }

    public byte[] decode(byte[] data){
        return m_decoder.decode(data);
    }

    public static void main(String[] args) {
        String path = "\\\\E:\\riseoa\\risefile\\repository\\fujian\\{0A000076-0000-0000-026A-423300000455}\\tongxiang\\20168\\深坪委办公纪〔2016〕266号  涞临同志挂点坑梓社区2016年第8次工作会议纪要OA.{BFA80606-FFFF-FFFF-9A8F-438E000002D9}.6.1.doc";
        String result = Base64Util.getInstance().encodeFile(path);
        System.out.println(result);

        String result2 = Base64Util.getInstance().endFileAsync(path);
        System.out.println(result2);
    }
}
