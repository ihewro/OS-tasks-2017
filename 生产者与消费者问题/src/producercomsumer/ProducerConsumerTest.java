package producercomsumer;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerTest {

    private final static int BUFFER_SIZE = 10;
    private static LinkedList<Object> buffer = new LinkedList<Object>();
//����һ�������������n��

    public static void  main (String args[]){
        //����2��Producer,3��Consumer
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
                		//System.out.printf("�����߲������ˣ�");
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
            System.out.println(this.getName() + " ������һ����Ʒ. �ܵ����� = " + buffer.size());
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
            			//System.out.printf("�����߲��������ˣ�");
            		}
            		consumer();
				}
            	
            	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
            }
        }
        public void consumer(){
            buffer.removeFirst();
            System.out.println(this.getName() + " �����������Ʒ. �ܵ����� = " + buffer.size());
        }

    }

}