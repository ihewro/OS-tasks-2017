package readerwriter;

import java.util.Random;  
import java.util.concurrent.Semaphore;  
import java.util.concurrent.atomic.AtomicInteger;  
import java.util.logging.Level;  
import java.util.logging.Logger;  

/*
 * java�ź���Semaphore��acquire() ��ȡһ����� release() �ͷ�һ����ɡ�
 * Semaphore���������췽����
 * 1. Semaphore(int) ��ʾ����ͬʱ���������
 * 2. Semaphore(int, booloean) booloean��ʾ�ͷŹ�ƽ�������̰߳����Ⱥ�˳��������
 * 
 * �����⻹����ʹ��java�Ķ�/д�����������readlock ��
 * private ReentrantReadWriterLock rwl = new ReentrantReadWriterLock();
 * private Lock readlock = rwl.readlock();
 * private Lock WiteLock = rwl.writelock();
 * �ڽ�����Ӧ����֮���������������֮ǰ����
 * 
 * ����ǻ�����Դ�������ַ�ʽ��
 * 1. synchronized �� notifyAll() ���� ��������Դ��wait�� �����߳�
 * 2. ReentrantLock �ࣺ lock()���룬 unlock()�ͷ���
 * */

public class ReadWrite {  
  
    private static ReadWrite thrdsync;  
    private static Thread t1, t2, t3, t4, t5;  
    private static final Random rand = new Random();  
    //TODO:����ط����ǲ����á�2��ָ�������ߣ�������ߵ�������࣬����ط���ֵҲҪͬ���仯
    private static Semaphore sm = new Semaphore(2);// �ź��� ����2���߳� true��ʾ�Ƚ��ȳ� 
    private static Semaphore wsm = new Semaphore(1);// �ź��� ����1���߳�  
    String text = "Beginning of the Book";// �����鱾  
    AtomicInteger readerCount = new AtomicInteger(0); // ��¼��ǰ��������  
    AtomicInteger writerCount = new AtomicInteger(0); // ��¼��ǰд������  
  
    public static void main(String[] args) {  
        thrdsync = new ReadWrite();  
        System.out.println("Lets begin...\n");  
        thrdsync.startThreads();  
    }  
    
    // �������һ��ʱ��  
    private void busy() {  
        try {  
            Thread.sleep(rand.nextInt(1000) + 1000);  
        } catch (InterruptedException e) {  
        }  
    }  
    // д��  
    private class Writer implements Runnable {  
  
        ReadWrite ts;  
  
        Writer(ReadWrite ts) {  
            super();  
            this.ts = ts;  
  
        }  
  
        public void run() {  
            while (true) {  
                if (readerCount.get() == 0) { // ��û�ж���ʱ�� ����д  
                    try {  
                        //System.out.println("write---readerCount= "+readerCount.get());  
                        //System.out.println("write---writerCount= "+writerCount.get());  
                        wsm.acquire(); // �ź�����ȡ����  
                        writerCount.getAndIncrement();  
                    } catch (InterruptedException ex) {  
                        Logger.getLogger(ReadWrite.class.getName()).log(  
                                Level.SEVERE, null, ex);  
                    }  
                    String new_sentence = new String("\tnew line in Book");  
                    busy();  
                    ts.write(new_sentence);  
                    wsm.release(); // �ź����ͷ�  
                    writerCount.getAndDecrement();  
  
                    busy();  
  
                }  
            } // of while  
        }  
    }  
  
    // ����  
    private class Reader implements Runnable {  
  
        ReadWrite ts;  
  
        Reader(ReadWrite ts) {  
            super();  
            this.ts = ts;  
  
        }  
  
        public void run() {  
            while (true) {  
                if (writerCount.get() == 0) { // û��д��ʱ �ſ��Զ�  
                    try {  
                        //System.out.println("Read---readerCount= "+readerCount.get());  
                        //System.out.println("Read---writerCount= "+writerCount.get());  
                        sm.acquire(); // ���߻�ȡ����  
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
                    sm.release(); // �ͷ�����  
                    readerCount.getAndDecrement();  
                    busy();  
                }  
            } // of while  
        }  
    }  
  
    // д����  
    void write(String sentence) {  
        System.out.println(Thread.currentThread().getName()  
                + " started to WRITE");  
        text += "\n" + sentence;  
        System.out.println(text);  
        System.out.println("End of Book\n");  
        System.out.println(Thread.currentThread().getName()  
                + " finished WRITING");  
    }  
  
    // ������  
    void read() {  
  
        System.out.println("\n" + Thread.currentThread().getName()  
                + " started to READ");  
        // System.out.println(text);  
        // System.out.println("End of Book\n");  
  
    }  
  
    // ������������ һ��д��  
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
