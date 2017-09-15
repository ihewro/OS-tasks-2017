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
	static AtomicInteger PatientChair;//病椅
	static AtomicInteger WaitChair;//等椅
	static boolean[] Doctor = {false, false, false};//三个医生是否在看病
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
				state += "病椅数量为" + IntegerPatientChair + "\n";
				state += "等椅数量为" + IntegerWaitChair + "\n";
				System.out.println("病椅数量为" + IntegerPatientChair);
				System.out.println("等椅数量为" + IntegerWaitChair);
				current_patients_chairs.setText(patientchairs.getText());
				current_wait_chairs.setText(waitchairs.getText());
				PatientChair = new AtomicInteger(IntegerPatientChair);//3个病椅
				WaitChair = new AtomicInteger(IntegerWaitChair);//10把等待椅子
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
			DoctorsPool.execute(new Doctor("医生" + i));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}*/
		
		Thread refresh = new Thread(new Refresh());
		refresh.start();
		for (int i = 0; i < 20; i++) {
			PatientsPool.execute(new Patient("病人" + i));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		
		//System.out.println("所有病人均看病结束");
	}
	
	/*static class Doctor extends Thread{
		String name;
		public Doctor(String name) {
			// TODO 自动生成的构造函数存根
			this.name = name;
		}
		
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			super.run();
		}
	}*/
	
	static class Patient extends Thread{
		String name;
		public Patient(String name) {
			// TODO 自动生成的构造函数存根
			this.name = name;
		}
		
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			System.out.println(name + "来了！");
			state += name + "来了！" + "\n";
			if(PatientChair.get() >0){
				PatientChair.decrementAndGet();
				try {
					pchair.acquire();
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				//InterfaceInstance.current_patients_chairs;
				Treating();
			}else if (WaitChair.get() > 0) {
				WaitChair.decrementAndGet();
				try {
					wchair.acquire();
				} catch (InterruptedException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				//System.out.println(name + "在等待看病");
				while(PatientChair.get() <= 0){
					try {
						Thread.sleep(100);//等待看病,100ms检查一次是否有空闲的病椅
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				WaitChair.incrementAndGet();
				PatientChair.decrementAndGet();
				wchair.release();
				try {
					pchair.acquire();
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				Treating();
			}else {
				System.out.println("一个病椅都没有，一个等椅都没有，" + name +"走了！");
				state += "一个病椅都没有，一个等椅都没有，" + name +"走了！" + "\n";
			}
			
			System.out.println("当前的病椅" + PatientChair.get());
		}
		public void Treating(){
			System.out.println(name + "在看病……");
			state += name + "在看病……" + "\n";
			try {
				Thread.sleep(2000);//模拟看病过程
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			PatientChair.incrementAndGet();
			pchair.release();
			System.out.println(name + "看病结束！");
			state += name + "看病结束！" + "\n";
			//notifyAll();//唤醒其他线程
			
		}
	}

	static class Refresh extends Thread{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while (true) {				
				current_patients_chairs.setText("" + PatientChair.get());
				current_wait_chairs.setText("" + WaitChair.get());
				outputinfo.setText(state);
				
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				if (WaitChair.get() == IntegerPatientChair && PatientChair.get() == IntegerWaitChair) {
					System.out.print("问题结束！");
					break;	
				}
			}
			
		}
	}
}
