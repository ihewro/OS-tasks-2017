package producercomsumer;

import java.util.ArrayList;

public class MyMain {

	int n= 10;//定义一共10个缓冲区
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
		// TODO 自动生成的构造函数存根
		this.name= name;
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		//MyMain MainInstance = new MyMain();
		while(MyMain.buffer.size() <= 10){			
			Semaphore.wait(MyMain.empty, "empty");
			Semaphore.wait(MyMain.mutex, "mutex");//使得当前缓冲池互斥，类似于Java中的lock
			System.out.println("此时插入的位置" + MyMain.in);
			
			produce();
			
			Semaphore.signal(MyMain.mutex);
			Semaphore.signal(MyMain.full);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	}
	
	public void produce(){
		//MyMain MainInstance = new MyMain();
		MyMain.buffer.add(MyMain.in,new Object());
		System.out.println(this.name +"成功生产一个物品" + " 当前总数为" + MyMain.buffer.size());
		MyMain.in = (MyMain.in + 1) % 10;
		System.out.println("此时插入的位置 +1之后   " + MyMain.in);
	}
	
}

class Consumer implements Runnable{
	String name;
	public Consumer(String name) {
		// TODO 自动生成的构造函数存根
		this.name = name;
	}
	
	@Override
	public void run() {
		System.out.println(this.name + "进程启动");
		// TODO 自动生成的方法存根
		//MyMain MainInstance = new MyMain();
		//while(MainInstance.buffer.size()>0){	
			System.out.println(this.name + "运行了！！");
			Semaphore.wait(MyMain.mutex, "mutex");
			Semaphore.wait(MyMain.full, "full");
			System.out.print("out的位置为 " + MyMain.out);
			comsume();
			Semaphore.signal(MyMain.mutex);
			Semaphore.signal(MyMain.empty);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
//		}
		
	}
	public void comsume(){
		//MyMain MainInstance = new MyMain();
		MyMain.buffer.remove(MyMain.out);
		MyMain.out = (MyMain.out + 1) % 10;
		System.out.println(this.name + "消费了一个物品" + " 当前物品总数为" + MyMain.buffer.size());
	}
}

class Semaphore{
	int value;//该资源的数目或者等待分配的数目
	ArrayList<Object> list = new ArrayList<>();//该资源的阻塞队列
	public Semaphore(int value) {
		// TODO 自动生成的构造函数存根
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
		while (s.getValue() < 0) {//表示之前该值一定是小于或等于0的，即该资源在进入该函数的时候就已经分配完了
			//s.list.add(new Object());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				System.out.print(name + "的" + "value值为" + s.getValue());

		}
		/*wait部分代码有问题，没办法做到阻塞该进程，没办法互斥访问对象*/
	}
	
	public static void signal(Semaphore s){
		s.setValue(s.getValue() + 1);
		if (s.value <=0) {//表示之前该值最大是-1，即该资源最少有一个进程在等待分配了，也就是该资源的阻塞队列中至少有一个元素
			//s.list.remove(0);
		}
	}
}