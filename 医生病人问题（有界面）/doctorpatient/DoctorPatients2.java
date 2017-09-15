package doctorpatient;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.Color;
import java.awt.Dimension;

public class DoctorPatients2 extends JFrame {

	private JPanel contentPane;
	public JTextField patientchairs;
	public JTextField waitchairs;
	public static JTextField current_patients_chairs;
	public static JTextField current_wait_chairs;
	public static JTextPane outputinfo;
	static int IntegerPatientChair;
	static int IntegerWaitChair;
	static AtomicInteger PatientChair;//����
	static AtomicInteger WaitChair;//����
	static boolean[] Doctor = {false, false, false};//����ҽ���Ƿ��ڿ���
	static String state = "";
    private static Semaphore wchair;
    private static Semaphore pchair;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorPatients2 frame = new DoctorPatients2();
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the frame.
	 */
	public DoctorPatients2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u521D\u59CB\u5316\u53C2\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JLabel label = new JLabel("\u75C5\u6905\u6570\u91CF\uFF1A");
		panel_1.add(label);
		
		patientchairs = new JTextField();
		panel_1.add(patientchairs);
		patientchairs.setColumns(10);
		
		JLabel label_1 = new JLabel("\u7B49\u6905\u6570\u91CF\uFF1A");
		panel_1.add(label_1);
		
		waitchairs = new JTextField();
		panel_1.add(waitchairs);
		waitchairs.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JButton button = new JButton("\u5F00\u59CB");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IntegerPatientChair = Integer.parseInt(patientchairs.getText());
				IntegerWaitChair = Integer.parseInt(waitchairs.getText());
				state += "��������Ϊ" + IntegerPatientChair + "\n";
				state += "��������Ϊ" + IntegerWaitChair + "\n";
				System.out.println("��������Ϊ" + IntegerPatientChair);
				System.out.println("��������Ϊ" + IntegerWaitChair);
				current_patients_chairs.setText(patientchairs.getText());
				current_wait_chairs.setText(waitchairs.getText());
				PatientChair = new AtomicInteger(IntegerPatientChair);//3������
				WaitChair = new AtomicInteger(IntegerWaitChair);//10�ѵȴ�����
			    wchair = new Semaphore(IntegerPatientChair);
			    pchair = new Semaphore(IntegerWaitChair);
				Start();
			}
		});
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_4.add(button);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "\u5F53\u524D\u8D44\u6E90\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JPanel panel_6 = new JPanel();
		panel_2.add(panel_6);
		
		JLabel label_2 = new JLabel("\u5F53\u524D\u75C5\u6905\u6570\u91CF\uFF1A");
		panel_6.add(label_2);
		
		current_patients_chairs = new JTextField();
		current_patients_chairs.setDisabledTextColor(Color.DARK_GRAY);
		current_patients_chairs.setEditable(false);
		current_patients_chairs.setEnabled(false);
		panel_6.add(current_patients_chairs);
		current_patients_chairs.setColumns(10);
		
		JLabel label_3 = new JLabel("\u5F53\u524D\u7B49\u6905\u6570\u91CF\uFF1A");
		panel_6.add(label_3);
		
		current_wait_chairs = new JTextField();
		current_wait_chairs.setDisabledTextColor(Color.DARK_GRAY);
		current_wait_chairs.setEnabled(false);
		current_wait_chairs.setEditable(false);
		panel_6.add(current_wait_chairs);
		current_wait_chairs.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null, "\u63A7\u5236\u53F0\u8F93\u51FA\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(scrollPane);
		
		outputinfo = new JTextPane();
		outputinfo.setPreferredSize(new Dimension(300, 100));
		outputinfo.setEditable(false);
		outputinfo.setEnabled(false);
		outputinfo.setDisabledTextColor(Color.DARK_GRAY);
		scrollPane.setViewportView(outputinfo);
	}
	
	public static void Start () {
		ExecutorService PatientsPool = Executors.newCachedThreadPool();
		//ExecutorService DoctorsPool = Executors.newCachedThreadPool();
		/*for(int i =0; i < 10; i++){
			DoctorsPool.execute(new Doctor("ҽ��" + i));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}*/
		
		Thread refresh = new Thread(new Refresh());
		refresh.start();
		for (int i = 0; i < 20; i++) {
			PatientsPool.execute(new Patient("����" + i));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		
		
		//System.out.println("���в��˾���������");
	}
	
	/*static class Doctor extends Thread{
		String name;
		public Doctor(String name) {
			// TODO �Զ����ɵĹ��캯�����
			this.name = name;
		}
		
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			super.run();
		}
	}*/
	
	static class Patient extends Thread{
		String name;
		public Patient(String name) {
			// TODO �Զ����ɵĹ��캯�����
			this.name = name;
		}
		
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			System.out.println(name + "���ˣ�");
			state += name + "���ˣ�" + "\n";
			if(PatientChair.get() >0){
				PatientChair.decrementAndGet();
				try {
					pchair.acquire();
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				//InterfaceInstance.current_patients_chairs;
				Treating();
			}else if (WaitChair.get() > 0) {
				WaitChair.decrementAndGet();
				try {
					wchair.acquire();
				} catch (InterruptedException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				//System.out.println(name + "�ڵȴ�����");
				while(PatientChair.get() <= 0){
					try {
						Thread.sleep(100);//�ȴ�����,100ms���һ���Ƿ��п��еĲ���
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
				WaitChair.incrementAndGet();
				PatientChair.decrementAndGet();
				wchair.release();
				try {
					pchair.acquire();
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				Treating();
			}else {
				System.out.println("һ�����ζ�û�У�һ�����ζ�û�У�" + name +"���ˣ�");
				state += "һ�����ζ�û�У�һ�����ζ�û�У�" + name +"���ˣ�" + "\n";
			}
			
			System.out.println("��ǰ�Ĳ���" + PatientChair.get());
		}
		public void Treating(){
			System.out.println(name + "�ڿ�������");
			state += name + "�ڿ�������" + "\n";
			try {
				Thread.sleep(2000);//ģ�⿴������
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			PatientChair.incrementAndGet();
			pchair.release();
			System.out.println(name + "����������");
			state += name + "����������" + "\n";
			//notifyAll();//���������߳�
			
		}
	}

	static class Refresh extends Thread{
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			while (true) {				
				current_patients_chairs.setText("" + PatientChair.get());
				current_wait_chairs.setText("" + WaitChair.get());
				outputinfo.setText(state);
				
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				if (WaitChair.get() == IntegerPatientChair && PatientChair.get() == IntegerWaitChair) {
					System.out.print("���������");
					break;	
				}
			}
			
		}
	}
}
