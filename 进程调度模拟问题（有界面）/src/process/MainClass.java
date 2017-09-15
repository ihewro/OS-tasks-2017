package process;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.omg.CORBA.PRIVATE_MEMBER;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class MainClass extends JFrame {

	private static final long serialVersionUID = -1860333819767861967L;
	static JPanel contentPane;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();
	static JTextField pcb_teminate;
	public static JTextField pcb_create;
	static JTextField pcb_running;
	static JTextField txtSystem;
	static JTextField runningtime;
	//static List<PCB> pcbs;
	static List<PCB> readypcbs;
	static List<PCB> blockpcbs;
	
	static int allTime=0;
	static boolean IOstatus = false;
	static int time_piece = 4;
	static PCB currentpcb;
	static JButton Startbutton;
	static JTable table_pcbready;
	static JTable table_pcbblock;
	static PCB currentcreatepcb = null;
	static JLabel lblIoState;
	static JLabel lblNewLabel_1;
	static JLabel lblNewLabel_2;
	static MyTableModel table_pcbready_dtm = new MyTableModel();
	static MyTableModel table_pcbblock_dtm = new MyTableModel();
	static ButtonGroup bg = new ButtonGroup();
	static int Algorithm;
	static JRadioButton TimeAlg;
	static JRadioButton PriorityAlg;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//符合 windows系统的风格控件
		//try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        //}catch(Exception e){}  
		
		System.out.println("hello");
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainClass frame = new MainClass();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//从json文件导入进程信息
		
		
		//pcbs=new ArrayList<>();
		//pcbs=ImportPCBData();
		
		
	}

	public static List<PCB> ImportPCBData(){
		String processMessage=PCB.loadProcess();
		List<PCB> PCBArgs = new ArrayList<>();
		JSONArray jsonArray=JSON.parseArray(processMessage);//使用阿里巴巴的json解析包，将字符串解析成json数组
		PCBArgs = PCB.JSONArray2ObjectList(jsonArray);
		return PCBArgs;	
	}
	/**
	 * Create the frame.
	 */
	public MainClass() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,100,900,620);
		//setBounds(int x, int y, int width, int height) 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel leftBox = new JPanel();
		contentPane.add(leftBox, BorderLayout.WEST);
		leftBox.setLayout(new BoxLayout(leftBox, BoxLayout.Y_AXIS));
		
		JPanel PCB_Create = new JPanel();
		PCB_Create.setBorder(new TitledBorder(null, "PCB_Create", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftBox.add(PCB_Create);
		PCB_Create.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pcb_create = new JTextField();
		pcb_create.setFont(new Font("宋体", Font.PLAIN, 16));
		pcb_create.setEditable(false);
		pcb_create.setColumns(25);
		PCB_Create.add(pcb_create);
		
		JScrollPane PCB_Ready = new JScrollPane();
		PCB_Ready.setBorder(new TitledBorder(null, "PCB_Ready", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftBox.add(PCB_Ready);
		
		
		//JTableOperation jTableInit = new JTableOperation();
		table_pcbready = new JTable(table_pcbready_dtm);

		PCB_Ready.setViewportView(table_pcbready);
		
		
		
		GlobalObject.setjScrollPane(PCB_Ready);
		GlobalObject.setjTable(table_pcbready);
		
		JPanel centerBox = new JPanel();
		contentPane.add(centerBox, BorderLayout.CENTER);
		centerBox.setLayout(new BoxLayout(centerBox, BoxLayout.Y_AXIS));
		
		JPanel PCB_Running = new JPanel();
		PCB_Running.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "PCB_Running", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		centerBox.add(PCB_Running);
		PCB_Running.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pcb_running = new JTextField();
		pcb_running.setFont(new Font("宋体", Font.PLAIN, 16));
		pcb_running.setEditable(false);
		pcb_running.setColumns(25);
		
		PCB_Running.add(pcb_running);
		
		JScrollPane PCB_Block = new JScrollPane();
		PCB_Block.setBorder(new TitledBorder(null, "PCB_Block", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centerBox.add(PCB_Block);
		
		//table_pcbblock = jTableInit.JtableDataInit();
		table_pcbblock = new JTable(table_pcbblock_dtm);
		PCB_Block.setViewportView(table_pcbblock);
		
		GlobalObject.setjScrollPane2(PCB_Block);
		GlobalObject.setjTable2(table_pcbblock);
		
		JPanel rightBox = new JPanel();
		contentPane.add(rightBox, BorderLayout.EAST);
		rightBox.setLayout(new BoxLayout(rightBox, BoxLayout.Y_AXIS));
		
		JPanel PCB_Terminate_panel = new JPanel();
		PCB_Terminate_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "PCB_Terminate", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		rightBox.add(PCB_Terminate_panel);
		PCB_Terminate_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pcb_teminate = new JTextField();
		pcb_teminate.setFont(new Font("宋体", Font.PLAIN, 16));
		pcb_teminate.setEditable(false);
		PCB_Terminate_panel.add(pcb_teminate);
		pcb_teminate.setColumns(25);
		
		JPanel CPU_info_panel = new JPanel();
		CPU_info_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "CPU infomation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		rightBox.add(CPU_info_panel);
		CPU_info_panel.setLayout(new BoxLayout(CPU_info_panel, BoxLayout.Y_AXIS));
		
		JPanel panel_6 = new JPanel();
		CPU_info_panel.add(panel_6);
		
		JLabel lblNewLabel = new JLabel("CPU time piece :4s");
		panel_6.add(lblNewLabel);
		
		JPanel Block_panel = new JPanel();
		Block_panel.setBorder(new TitledBorder(null, "Block", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Block_panel.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent e) {
			}
		});
		
		JPanel start_panel = new JPanel();
		start_panel.setBorder(new TitledBorder(null, "Start", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		rightBox.add(start_panel);
		start_panel.setLayout(new BoxLayout(start_panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		start_panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		TimeAlg = new JRadioButton("\u65F6\u95F4\u7247\u8F6E\u8F6C\u7B97\u6CD5");
		TimeAlg.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getItemSelectable() == TimeAlg) {
					Algorithm = 1;
				}else{
					Algorithm = 0;
				}
			}
		});
		panel_5.add(TimeAlg);
		
		PriorityAlg = new JRadioButton("\u4F18\u5148\u7EA7\u6570\u7B97\u6CD5");
		PriorityAlg.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getItemSelectable() == PriorityAlg) {
					Algorithm = 2;
				}else{
					Algorithm = 0;
				}
			}
		});
		panel_5.add(PriorityAlg);
		
		bg.add(TimeAlg);
		bg.add(PriorityAlg);
		Startbutton = new JButton("Start");
		panel_1.add(Startbutton);
		Startbutton.setFont(new Font("宋体", Font.PLAIN, 30));
		
		JPanel panel_2 = new JPanel();
		start_panel.add(panel_2);
		
		txtSystem = new JTextField();
		txtSystem.setEditable(false);
		txtSystem.setEnabled(false);
		txtSystem.setText("CPU Running Time :");
		txtSystem.setBorder(null);
		panel_2.add(txtSystem);
		txtSystem.setColumns(30);
		
		runningtime = new JTextField();
		runningtime.setEnabled(false);
		runningtime.setEditable(false);
		panel_2.add(runningtime);
		runningtime.setColumns(10);
		String s_alltime = String.valueOf(allTime);
		runningtime.setText(s_alltime);
		Startbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Startbutton.setEnabled(false);
				startbutton();
			}
		});
		rightBox.add(Block_panel);
		Block_panel.setLayout(new BoxLayout(Block_panel, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		Block_panel.add(panel);
		
		JButton btnNewButton = new JButton("Block");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blockbutton();
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("I/O Start");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IOstartbutton();
			}
		});
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("I/O Stop");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IOstopbutton();
			}
		});
		panel.add(btnNewButton_2);
		
		JPanel panel_3 = new JPanel();
		Block_panel.add(panel_3);
		
		lblIoState = new JLabel("I/O status : ");
		panel_3.add(lblIoState);
		
		lblNewLabel_2 = new JLabel("false");
		panel_3.add(lblNewLabel_2);
		
		JPanel panel_4 = new JPanel();
		Block_panel.add(panel_4);
		
		JLabel lblIoTime = new JLabel("I/O time for each blocked process : 3s");
		panel_4.add(lblIoTime);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane, rightBox}));
		pack();
	}
	
	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3560654524643623528L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_1 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2465718565543794188L;
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	}

	
	/**
	 * 主要函数
	 * @param jsonArray
	 * @return
	 */
	

	public void blockbutton(){
		if(currentpcb!=null && currentpcb.getRunTime()!=0){
			blockpcbs.add(currentpcb);
			currentpcb.setStatus(2);
			System.out.println("阻塞" + currentpcb.getName() + "status 是" + currentpcb.getStatus());	
		}
	}
	
	public void IOstartbutton(){
		IOstatus = true;
		lblNewLabel_2.setText("true");
	}
	public void IOstopbutton(){
		IOstatus = false;
		lblNewLabel_2.setText("false");
	}
	
	public void startbutton(){//开始按钮
		System.out.println("选择的按钮是：" + Algorithm);
		if(Algorithm == 0){
			//TODO:提醒选择单选按钮
			JOptionPane.showMessageDialog(null,"请先选择算法","结果",JOptionPane.INFORMATION_MESSAGE);
		}else{			
			System.out.println("startbutton start!");
			TimeAlg.setEnabled(false);
			PriorityAlg.setEnabled(false);
			Startbutton.setEnabled(false);
			changeByTime();
		}
	}
	
	public static void changeByTime() {
		
		blockpcbs = new ArrayList<>();
		readypcbs = new ArrayList<>();
		
		Thread RefreshData = new Thread(new RereshData());
		Thread CheckIODevice = new Thread(new CheckIODevice());
		Thread CreatPCB = new Thread(new CreatPCBQueue());
		Thread ScanningReady = new Thread(new ScanningReadyQueue());
		
		RefreshData.start();
		CheckIODevice.start();
		CreatPCB.start();
		ScanningReady.start();
		
		System.out.println("Start!");

	}
	

	static class CheckIODevice extends Thread{//检测I/O设备是否可用，如果可用，定时将阻塞队列插入到就绪队列中
		@Override
		public void run() {
			PCB pcb;
			while(true){
				System.out.println("检测IO设备");
				if(IOstatus == true && blockpcbs.size()!=0){
					pcb = blockpcbs.get(0);
					pcb.setStatus(0);
					readypcbs.add(pcb);
					blockpcbs.remove(pcb);
					try {
						Thread.sleep(3000);//每个进程所需的I/O时间为3s
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (readypcbs.isEmpty() && blockpcbs.isEmpty() &&currentpcb ==null){
					pcb_running.setText("");
					break;
				}

			}
			
		}
	}
	
	static class RereshData extends Thread{
		@Override
		public void run() {
			while(true){
				if(currentcreatepcb!= null){
					pcb_create.setText(currentcreatepcb.getName());
				}else{
					pcb_create.setText("");
				}
				//System.out.println("刷新就绪队列");
				//reloadJTable(GlobalObject.getjTable(),readypcbs,1);
				//reloadJTable(GlobalObject.getjTable2(),blockpcbs,2);
				if (Algorithm == 2) {					
					Collections.sort(readypcbs);
					Collections.sort(blockpcbs);
				}
				MyTableModel.reloadJTable(table_pcbready_dtm, readypcbs, GlobalObject.getjTable(), 1);
				MyTableModel.reloadJTable(table_pcbblock_dtm, blockpcbs, GlobalObject.getjTable2(), 2);
				//MyTableModel.reloadJTable(table_pcbready_dtm, readypcbs, GlobalObject.getjTable());
				//MyTableModel.reloadJTable(table_pcbblock_dtm, blockpcbs, GlobalObject.getjTable2());
				if(currentpcb!=null){
					pcb_running.setText(currentpcb.getName());//在pcb_running中显示运行的进程	
				}else{
					pcb_running.setText("");
				}
				

				runningtime.setText(String.valueOf(allTime));
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
				if (readypcbs.isEmpty() && blockpcbs.isEmpty() &&currentpcb ==null){
					pcb_running.setText("");
					JOptionPane.showMessageDialog(null,"所有进程运行完毕！","结果",JOptionPane.INFORMATION_MESSAGE);
					TimeAlg.setEnabled(true);
					PriorityAlg.setEnabled(true);
					Startbutton.setEnabled(true);
					break;
				}
			}
		}
	}
	static class CreatPCBQueue extends Thread{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			
			List<PCB> pcbs2 = ImportPCBData();
			
			System.out.println("pcb的数量为" + pcbs2.size());
			for(int i=0;i<pcbs2.size();i++){
				currentcreatepcb = pcbs2.get(i);
				readypcbs.add(currentcreatepcb);
				System.out.println("就绪队列的pcb数目" + readypcbs.size());
				try {
					if(i!=pcbs2.size()-1){
						System.out.println("i为" + i + "PCB创建进程" + pcbs2.get(i).getName());
						if(i>1){
							System.out.println("后一个进程的名称为" + pcbs2.get(i+1).getName());
						}
						//currentcreatepcb = null;
						//pcb_create.setText("");
						//TODO:这个地方有点小问题。
						Thread.sleep((pcbs2.get(i+1).getStartTime()-pcbs2.get(i).getStartTime())*1000 +200);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			currentcreatepcb = null;
			pcb_create.setText("");
		}
	}
	
	static class ScanningReadyQueue extends Thread{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while(true){
				System.out.println("****开始**** " + readypcbs.size());
				currentpcb=null;
				int k=0;
				try {
					Thread.sleep(500);//目的是让创建的新进程先显示在readytable中，再显示在running中
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				//System.out.println(pcbs.size());
				//Collections.sort(readypcbs);
				for(int j=0;j<readypcbs.size();j++){
					PCB pcb=readypcbs.get(j);

					if(pcb.getIsOver()==false){
						if(currentpcb==null){
							currentpcb=pcb;
							k=j;
							break;
						}	
					}
				}//从就绪队列中找出需要运行的进程，结束循环
				if(currentpcb!=null){
					readypcbs.remove(k);//从就绪队列中移除该进程
					//reloadJTable(GlobalObject.getjTable());
					currentpcb.setStatus(1);
					currentpcb.decrease();//运行该进程一个单位的时间
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//pcb_running.setText(currentpcb.getName());//在pcb_running中显示运行的进程
					//pcb_running.paintImmediately(pcb_running.getBounds());
					System.out.println("正在运行：" + currentpcb.getName());
					allTime++;
					int j =1;
					for(j=1;j<time_piece;j++){//给当前进程运行一个时间片的时间直到进程结束
						if (currentpcb.getIsOver() == false && currentpcb.getStatus() == 1) {
							currentpcb.decrease();//继续运行一个单位的时间
							allTime++;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}else if(currentpcb.getStatus() == 2){//在这过程中点击了阻塞的按钮
							currentpcb.setStatus(2);
							currentpcb =null;
							break;
						}
					}					
					if(j==time_piece&&currentpcb.getIsOver()==false && currentpcb.getStatus() !=2){//时间片用完了，但是进程还没有结束	
						currentpcb.setStatus(0);
						if(Algorithm == 2){							
							currentpcb.setPriority(currentpcb.getPriority() - 1);
							//成功运行一次后，优先数减1
						}
						readypcbs.add(currentpcb);//加到就绪队列的末尾
						
					}
					
					if(j==time_piece && currentpcb.getIsOver() == true){
						pcb_teminate.setText(currentpcb.getName());
					}
					
				}
				if (readypcbs.isEmpty() && blockpcbs.isEmpty() &&currentpcb ==null){
					pcb_running.setText("");
					break;
				}
				
			}
		}
	}
	
}
