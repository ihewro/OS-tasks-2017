package baber;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Baber {
	
	//private static Semaphore chair = new Semaphore(3);//������3��
	static int chair = 3;
	static boolean isDressing = false;
	static int Customers = 0;//����˿���Ŀ
	public static void main(String[] args) {
		Thread CustomerA = new Thread(new Customer("�˿�A"));
		Thread CustomerB = new Thread(new Customer("�˿�B"));
		Thread CustomerC = new Thread(new Customer("�˿�C"));
		Thread CustomerD = new Thread(new Customer("�˿�D"));
		Thread CustomerE = new Thread(new Customer("�˿�E"));

		Thread hairDresser = new Thread(new hairDresser());
		hairDresser.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
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
			// TODO �Զ����ɵķ������
			while(true){
				if(isDressing == false && chair == 3){					
					System.out.println("û�й˿���������ʦ˯���С���");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
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
			// TODO �Զ����ɵĹ��캯�����
			this.name = name;
		}
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			Customers++;
			System.out.println(name + "���ˣ�");
			if(isDressing == false){
				Dressing(name);
			}else if (chair > 0 && isDressing == true) {
				chair--;
				System.out.println("���ӵ�����Ϊ��" + chair);
				while(isDressing == true){		
					//System.out.printf(name + "���ڼ����ʦ�Ƿ�������ѭ������    ");
					//System.out.println("isDressing = " + isDressing);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
				chair++;
				Dressing(name);
			}else{
				//isGo = true;
				System.out.println("���ӵ�����Ϊ��" + chair);
				System.out.println("��ʦ����������Ҳ�����ˡ�" + name + "���ˣ�");
				Customers--;
			}
		}
		public synchronized void Dressing(String name){
			isDressing = true;
			System.out.println(name + "����������");
			try {
				Thread.sleep(2000);//ģ��������
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
			Customers--;
			isDressing = false;
			System.out.println(name + "����ϣ�");
			notifyAll();//���������߳�
		}
	}
}
