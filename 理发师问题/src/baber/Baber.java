package baber;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Baber {
	
	//private static Semaphore chair = new Semaphore(3);//椅子有3把
	static int chair = 3;
	static boolean isDressing = false;
	static int Customers = 0;//定义顾客数目
	public static void main(String[] args) {
		Thread CustomerA = new Thread(new Customer("顾客A"));
		Thread CustomerB = new Thread(new Customer("顾客B"));
		Thread CustomerC = new Thread(new Customer("顾客C"));
		Thread CustomerD = new Thread(new Customer("顾客D"));
		Thread CustomerE = new Thread(new Customer("顾客E"));

		Thread hairDresser = new Thread(new hairDresser());
		hairDresser.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		CustomerA.start();

		CustomerB.start();

		CustomerC.start();

		CustomerD.start();

		CustomerE.start();
		
	}
	
	static class hairDresser extends Thread{
		
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while(true){
				if(isDressing == false && chair == 3){					
					System.out.println("没有顾客来理发，理发师睡觉中……");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
			//Dressing();
		}
	}
	
	static class Customer extends Thread{
		String name;
		boolean isGo = false;
		public Customer(String name) {
			// TODO 自动生成的构造函数存根
			this.name = name;
		}
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			Customers++;
			System.out.println(name + "来了！");
			if(isDressing == false){
				Dressing(name);
			}else if (chair > 0 && isDressing == true) {
				chair--;
				System.out.println("椅子的数量为：" + chair);
				while(isDressing == true){		
					//System.out.printf(name + "卡在检测理发师是否在理发的循环里了    ");
					//System.out.println("isDressing = " + isDressing);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				chair++;
				Dressing(name);
			}else{
				//isGo = true;
				System.out.println("椅子的数量为：" + chair);
				System.out.println("理发师在理发，椅子也坐满了。" + name + "走了！");
				Customers--;
			}
		}
		public synchronized void Dressing(String name){
			isDressing = true;
			System.out.println(name + "正在理发……");
			try {
				Thread.sleep(2000);//模拟理发过程
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			Customers--;
			isDressing = false;
			System.out.println(name + "理发完毕！");
			notifyAll();//唤醒其他线程
		}
	}
}
