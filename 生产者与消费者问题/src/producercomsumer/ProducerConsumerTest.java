package producercomsumer;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerTest {

    private final static int BUFFER_SIZE = 10;
    private static LinkedList<Object> buffer = new LinkedList<Object>();
//定义一个链表，代表具有n个

    public static void  main (String args[]){
        //创建2个Producer,3个Consumer
        Thread producerA = new Thread(new Producer("producerA"));
        Thread producerB = new Thread(new Producer("producerB"));
        Thread consumerA = new Thread(new Consumer("consumerA"));
        Thread consumerB = new Thread(new Consumer("consumerB"));
        Thread consumerC = new Thread(new Consumer("consumerC"));

        producerA.start();
        producerB.start();
        consumerA.start();
        consumerB.start();
        //consumerC.start();
    }

    static class Producer extends Thread{
        public Producer(String name){
            this.setName(name);
        }
        
        private static AtomicInteger i = new AtomicInteger(0);
        @Override
        public void run() {
            while(true){
                synchronized (buffer){
                	while(buffer.size() >= BUFFER_SIZE){
                		try {
							buffer.wait(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
                		//System.out.printf("生产者不生产了！");
                	}
                    produce();
                    buffer.notifyAll();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public void produce(){
            buffer.add(new Object());
            System.out.println(this.getName() + " 生产了一件物品. 总的数量 = " + buffer.size());
        }
    }

    static class Consumer extends Thread{
        public Consumer(String name){
            this.setName(name);
        }
        private static AtomicInteger i = new AtomicInteger(0);
        @Override
        public void run() {
            while (true) {
            	synchronized (buffer) {
            		while (buffer.size() == 0) {
            			try {
            				buffer.wait(50);
            			} catch (InterruptedException e) {
            				e.printStackTrace();
            			}
            			//System.out.printf("消费者不能消费了！");
            		}
            		consumer();
				}
            	
            	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
            }
        }
        public void consumer(){
            buffer.removeFirst();
            System.out.println(this.getName() + " 消费了意见物品. 总的数量 = " + buffer.size());
        }

    }

}