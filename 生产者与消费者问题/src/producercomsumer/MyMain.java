package producercomsumer;

import java.util.ArrayList;

public class MyMain {

	int n= 10;//����һ��10��������
	static int in;
	static int out;
	static ArrayList<Object> buffer = new ArrayList<>();
	static Semaphore mutex = new Semaphore(1);
	static Semaphore empty = new Semaphore(10);
	static Semaphore full = new Semaphore(0);
	
	/*public static void main(String[] args) {
		in = 0;
		out = 0;
		Thread producerA = new Thread(new Producer("producerA"));
        Thread producerB = new Thread(new Producer("producerB"));
        Thread consumerA = new Thread(new Consumer("consumerA"));
        Thread consumerB = new Thread(new Consumer("consumerB"));
        Thread consumerC = new Thread(new Consumer("consumerC"));

        producerA.start();
        producerB.start();
        consumerA.start();
        consumerB.start();
        consumerC.start();
	}*/
	
}

class Producer implements Runnable{
	String name;
	public Producer(String name) {
		// TODO �Զ����ɵĹ��캯�����
		this.name= name;
	}
	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		//MyMain MainInstance = new MyMain();
		while(MyMain.buffer.size() <= 10){			
			Semaphore.wait(MyMain.empty, "empty");
			Semaphore.wait(MyMain.mutex, "mutex");//ʹ�õ�ǰ����ػ��⣬������Java�е�lock
			System.out.println("��ʱ�����λ��" + MyMain.in);
			
			produce();
			
			Semaphore.signal(MyMain.mutex);
			Semaphore.signal(MyMain.full);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		
	}
	
	public void produce(){
		//MyMain MainInstance = new MyMain();
		MyMain.buffer.add(MyMain.in,new Object());
		System.out.println(this.name +"�ɹ�����һ����Ʒ" + " ��ǰ����Ϊ" + MyMain.buffer.size());
		MyMain.in = (MyMain.in + 1) % 10;
		System.out.println("��ʱ�����λ�� +1֮��   " + MyMain.in);
	}
	
}

class Consumer implements Runnable{
	String name;
	public Consumer(String name) {
		// TODO �Զ����ɵĹ��캯�����
		this.name = name;
	}
	
	@Override
	public void run() {
		System.out.println(this.name + "��������");
		// TODO �Զ����ɵķ������
		//MyMain MainInstance = new MyMain();
		//while(MainInstance.buffer.size()>0){	
			System.out.println(this.name + "�����ˣ���");
			Semaphore.wait(MyMain.mutex, "mutex");
			Semaphore.wait(MyMain.full, "full");
			System.out.print("out��λ��Ϊ " + MyMain.out);
			comsume();
			Semaphore.signal(MyMain.mutex);
			Semaphore.signal(MyMain.empty);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
//		}
		
	}
	public void comsume(){
		//MyMain MainInstance = new MyMain();
		MyMain.buffer.remove(MyMain.out);
		MyMain.out = (MyMain.out + 1) % 10;
		System.out.println(this.name + "������һ����Ʒ" + " ��ǰ��Ʒ����Ϊ" + MyMain.buffer.size());
	}
}

class Semaphore{
	int value;//����Դ����Ŀ���ߵȴ��������Ŀ
	ArrayList<Object> list = new ArrayList<>();//����Դ����������
	public Semaphore(int value) {
		// TODO �Զ����ɵĹ��캯�����
		this.value = value;
	}
	public void setValue(int value){
		this.value = value;
	}
	public int getValue(){
		return this.value;
	}
	public ArrayList<Object> getList(){
		return this.list;
	}
	public static void wait(Semaphore s, String name) {
		s.setValue(s.getValue() - 1);
		while (s.getValue() < 0) {//��ʾ֮ǰ��ֵһ����С�ڻ����0�ģ�������Դ�ڽ���ú�����ʱ����Ѿ���������
			//s.list.add(new Object());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				System.out.print(name + "��" + "valueֵΪ" + s.getValue());

		}
		/*wait���ִ��������⣬û�취���������ý��̣�û�취������ʶ���*/
	}
	
	public static void signal(Semaphore s){
		s.setValue(s.getValue() + 1);
		if (s.value <=0) {//��ʾ֮ǰ��ֵ�����-1��������Դ������һ�������ڵȴ������ˣ�Ҳ���Ǹ���Դ������������������һ��Ԫ��
			//s.list.remove(0);
		}
	}
}