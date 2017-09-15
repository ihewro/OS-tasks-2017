package readerwriter;

import java.util.Random;  
import java.util.concurrent.Semaphore;  
import java.util.concurrent.atomic.AtomicInteger;  
import java.util.logging.Level;  
import java.util.logging.Logger;  

/*
 * java信号量Semaphore：acquire() 获取一个许可 release() 释放一个许可。
 * Semaphore有两个构造方法：
 * 1. Semaphore(int) 表示可以同时进入的数量
 * 2. Semaphore(int, booloean) booloean表示释放公平，即多线程按照先后顺序分配许可
 * 
 * 该问题还可以使用java的读/写锁来解决。即readlock 和
 * private ReentrantReadWriterLock rwl = new ReentrantReadWriterLock();
 * private Lock readlock = rwl.readlock();
 * private Lock WiteLock = rwl.writelock();
 * 在进入相应方法之后加锁，结束方法之前解锁
 * 
 * 如果是互斥资源，有两种方式：
 * 1. synchronized ： notifyAll() 唤醒 调用了资源的wait的 其他线程
 * 2. ReentrantLock 类： lock()锁入， unlock()释放锁
 * */

public class ReadWrite {  
  
    private static ReadWrite thrdsync;  
    private static Thread t1, t2, t3, t4, t5;  
    private static final Random rand = new Random();  
    //TODO:这个地方还是不够好。2是指两个读者，如果读者的数量变多，这个地方的值也要同步变化
    private static Semaphore sm = new Semaphore(2);// 信号量 允许2个线程 true表示先进先出 
    private static Semaphore wsm = new Semaphore(1);// 信号量 允许1个线程  
    String text = "Beginning of the Book";// 代表书本  
    AtomicInteger readerCount = new AtomicInteger(0); // 记录当前读者数量  
    AtomicInteger writerCount = new AtomicInteger(0); // 记录当前写者数量  
  
    public static void main(String[] args) {  
        thrdsync = new ReadWrite();  
        System.out.println("Lets begin...\n");  
        thrdsync.startThreads();  
    }  
    
    // 随机休眠一定时间  
    private void busy() {  
        try {  
            Thread.sleep(rand.nextInt(1000) + 1000);  
        } catch (InterruptedException e) {  
        }  
    }  
    // 写者  
    private class Writer implements Runnable {  
  
        ReadWrite ts;  
  
        Writer(ReadWrite ts) {  
            super();  
            this.ts = ts;  
  
        }  
  
        public void run() {  
            while (true) {  
                if (readerCount.get() == 0) { // 当没有读者时才 可以写  
                    try {  
                        //System.out.println("write---readerCount= "+readerCount.get());  
                        //System.out.println("write---writerCount= "+writerCount.get());  
                        wsm.acquire(); // 信号量获取允许  
                        writerCount.getAndIncrement();  
                    } catch (InterruptedException ex) {  
                        Logger.getLogger(ReadWrite.class.getName()).log(  
                                Level.SEVERE, null, ex);  
                    }  
                    String new_sentence = new String("\tnew line in Book");  
                    busy();  
                    ts.write(new_sentence);  
                    wsm.release(); // 信号量释放  
                    writerCount.getAndDecrement();  
  
                    busy();  
  
                }  
            } // of while  
        }  
    }  
  
    // 读者  
    private class Reader implements Runnable {  
  
        ReadWrite ts;  
  
        Reader(ReadWrite ts) {  
            super();  
            this.ts = ts;  
  
        }  
  
        public void run() {  
            while (true) {  
                if (writerCount.get() == 0) { // 没有写者时 才可以读  
                    try {  
                        //System.out.println("Read---readerCount= "+readerCount.get());  
                        //System.out.println("Read---writerCount= "+writerCount.get());  
                        sm.acquire(); // 读者获取允许  
                        readerCount.getAndIncrement();  
                    } catch (InterruptedException ex) {  
                        Logger.getLogger(ReadWrite.class.getName()).log(  
                                Level.SEVERE, null, ex);  
                    }  
                    // System.out.print(t);  
  
                    ts.read();  
                    busy();  
                    System.out.println(Thread.currentThread().getName()  
                            + " end of read");  
                    sm.release(); // 释放允许  
                    readerCount.getAndDecrement();  
                    busy();  
                }  
            } // of while  
        }  
    }  
  
    // 写函数  
    void write(String sentence) {  
        System.out.println(Thread.currentThread().getName()  
                + " started to WRITE");  
        text += "\n" + sentence;  
        System.out.println(text);  
        System.out.println("End of Book\n");  
        System.out.println(Thread.currentThread().getName()  
                + " finished WRITING");  
    }  
  
    // 读函数  
    void read() {  
  
        System.out.println("\n" + Thread.currentThread().getName()  
                + " started to READ");  
        // System.out.println(text);  
        // System.out.println("End of Book\n");  
  
    }  
  
    // 创建两个读者 一个写者  
    public void startThreads() {  
        ReadWrite ts = new ReadWrite();  
        t1 = new Thread(new Writer(ts), "Writer # 1");  
        t2 = new Thread(new Writer(ts), "Writer # 2");  
        t3 = new Thread(new Reader(ts), "Reader # 1");  
        t4 = new Thread(new Reader(ts), "Reader # 2");  
        // t5 = new Thread(new Reader(ts), "Reader # 3");  
        t1.start();  
        t2.start();  
        t3.start();  
        t4.start();  
        // t5.start();  
    }  

}  
