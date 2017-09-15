package banks;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JFrame {
	private JPanel contentPane;
	static JTextField textField;
	public static JTable table;
	public static Resource AvailableResource = new Resource();
	static int SafeArgs[] = new int [5];//��¼��ȫ����
	final static MyTableModel dtm = new MyTableModel();
	static int RequestSource [] = new int [4];
	//��̬��������һ��������ж����ã�
	public static Progress[] progresses = new Progress[5];

	JTextField info;
	JTextField safeArgsInfo;
	//info �� safeArgsInfo �����static ���Σ�ֻ��settext() һ�Σ�ԭ�������
	public JTable GetJTable(){
		return table;
	}
	public Progress[] GetProgresses(){
		return progresses;
	}
	public Resource GetAvailableResource(){
		return AvailableResource;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Main mainInstance = new Main();
		System.out.println("Runnnig there!1");
		mainInstance.InitProgressResouce();
		MyTableModel.reloadJTable(dtm);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		if(mainInstance.SafeCheck() == false){
			textField.setForeground(Color.WHITE);
			textField.setBackground(Color.RED);
			textField.setText("����ȫ");
		}
		
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		JTextField RequestProgressName;
		JTextField RequestProgressA;
		JTextField RequestProgressB;
		JTextField RequestProgressC;
		/*һ��ʼ�Ұ�����������ڳ�Ա����λ�ã�����static���Σ������޷���ȡ���û������ֵ*/
		setAlwaysOnTop(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 750, 420);
		//setBounds(int x, int y, int width, int height) 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("\u5F53\u524D\u7CFB\u7EDF\u7684\u72B6\u6001\u662F\uFF1A ");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.GREEN);
		textField.setText("\u5B89\u5168");
		panel.add(textField);
		textField.setColumns(10);
		
		table  = new JTable(dtm);
		table.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null, "\u8D44\u6E90\u5206\u914D\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	
		JPanel panel_6 = new JPanel();
		contentPane.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_6.add(scrollPane_1);
		scrollPane_1.setBorder(new TitledBorder(null, "\u8F93\u5165\u533A\u57DF", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_1 = new JPanel();
		scrollPane_1.setColumnHeaderView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JPanel panel_8 = new JPanel();
		panel_2.add(panel_8);
		
		JLabel lblNewLabel_1 = new JLabel("\u8BF7\u6C42\u7684\u8FDB\u7A0B\u540D\u79F0\uFF1A  ");
		panel_8.add(lblNewLabel_1);
		
		JPanel panel_9 = new JPanel();
		panel_2.add(panel_9);
		
		RequestProgressName = new JTextField();
		RequestProgressName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				//RequestSource[0] = Integer.parseInt(RequestProgressName.getText());
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		panel_9.add(RequestProgressName);
		RequestProgressName.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		JPanel panel_10 = new JPanel();
		panel_3.add(panel_10);
		
		JLabel lblNewLabel_2 = new JLabel("A\u7C7B\u8D44\u6E90\u8BF7\u6C42\u6570\u76EE\uFF1A");
		panel_10.add(lblNewLabel_2);
		
		JPanel panel_11 = new JPanel();
		panel_3.add(panel_11);
		
		RequestProgressA = new JTextField();
		RequestProgressA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				//RequestSource[1] = Integer.parseInt(RequestProgressA.getText());
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		panel_11.add(RequestProgressA);
		RequestProgressA.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		JPanel panel_12 = new JPanel();
		panel_4.add(panel_12);
		
		JLabel lblB = new JLabel("B\u7C7B\u8D44\u6E90\u8BF7\u6C42\u6570\u76EE\uFF1A");
		panel_12.add(lblB);
		
		JPanel panel_13 = new JPanel();
		panel_4.add(panel_13);
		
		RequestProgressB = new JTextField();
		RequestProgressB.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				//RequestSource[2] = Integer.parseInt(RequestProgressB.getText());
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		panel_13.add(RequestProgressB);
		RequestProgressB.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		JPanel panel_14 = new JPanel();
		panel_5.add(panel_14);
		
		JLabel lblC = new JLabel("C\u7C7B\u8D44\u6E90\u8BF7\u6C42\u6570\u76EE\uFF1A");
		panel_14.add(lblC);
		
		JPanel panel_15 = new JPanel();
		panel_5.add(panel_15);
		
		RequestProgressC = new JTextField();
		RequestProgressC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				//RequestSource[3] = Integer.parseInt(RequestProgressC.getText());
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		panel_15.add(RequestProgressC);
		RequestProgressC.setColumns(10);
		
		JPanel panel_7 = new JPanel();
		scrollPane_1.setViewportView(panel_7);
		
		JButton btnNewButton_1 = new JButton("\u6E05\u7A7A");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RequestProgressName.setText("");
				RequestProgressA.setText("");
				RequestProgressB.setText("");
				RequestProgressC.setText("");
			}
		});
		panel_7.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("\u63D0\u4EA4");
		panel_7.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(RequestProgressName.getText().isEmpty() || RequestProgressA.getText().isEmpty() ||
						RequestProgressB.getText().isEmpty() || RequestProgressC.getText().isEmpty()){
					safeArgsInfo.setText("��������ȷ�����֣�");
				}else if (!isNumeric(RequestProgressName.getText()) || !isNumeric(RequestProgressA.getText()) ||
						!isNumeric(RequestProgressB.getText()) || !isNumeric(RequestProgressC.getText())) {
					safeArgsInfo.setText("ֻ����д���֣�");
				}else if (Integer.parseInt(RequestProgressName.getText())>4 || 
						Integer.parseInt(RequestProgressName.getText())<0) {
					safeArgsInfo.setText("��������ֻ����0-4�����֣���");
				}
				else {
					System.out.println("�������ƣ�" + RequestProgressName.getText());
					System.out.println("A�� " + RequestProgressA.getText());
					System.out.println("B�� " + RequestProgressB.getText());
					System.out.println("C�� " + RequestProgressC.getText());
					RequestSource[0] = Integer.parseInt(RequestProgressName.getText());
					RequestSource[1] = Integer.parseInt(RequestProgressA.getText());
					RequestSource[2] = Integer.parseInt(RequestProgressB.getText());
					RequestSource[3] = Integer.parseInt(RequestProgressC.getText());
					Main mainInstance = new Main();
					if(Request(RequestSource)){
						MyTableModel.reloadJTable(dtm);
					}
					if(mainInstance.SafeCheck() == false){
						textField.setForeground(Color.WHITE);
						textField.setBackground(Color.RED);
						textField.setText("����ȫ");
					}
				}
			}
		});
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(new TitledBorder(null, "\u53CD\u9988\u4FE1\u606F\u533A\u57DF", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.add(scrollPane_2);
		
		JPanel panel_16 = new JPanel();
		scrollPane_2.setViewportView(panel_16);
		panel_16.setLayout(new BoxLayout(panel_16, BoxLayout.Y_AXIS));
		
		JPanel panel_17 = new JPanel();
		panel_16.add(panel_17);
		panel_17.setLayout(new BoxLayout(panel_17, BoxLayout.Y_AXIS));
		
		JPanel panel_19 = new JPanel();
		panel_17.add(panel_19);
		panel_19.setLayout(new BoxLayout(panel_19, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("\u5206\u914D\u4FE1\u606F\uFF1A");
		panel_19.add(label);
		
		info = new JTextField();
		panel_19.add(info);
		info.setColumns(10);
		
		JPanel panel_18 = new JPanel();
		panel_16.add(panel_18);
		
		JLabel label_1 = new JLabel("\u5B89\u5168\u5E8F\u5217\uFF1A");
		panel_18.add(label_1);
		
		safeArgsInfo = new JTextField();
		panel_18.add(safeArgsInfo);
		safeArgsInfo.setColumns(20);
		
		
	}
	
	public void InitProgressResouce(){
		for(int i = 0; i < 5; i++){
			progresses[i] = new Progress();
		}
		/*�趨5���������������Դ����Ŀ*/
		System.out.println("Runnnig there!2");
		progresses[0].setMaxSourceNumber(7,5,3);
		progresses[1].setMaxSourceNumber(3,2,2);
		progresses[2].setMaxSourceNumber(9,0,2);
		progresses[3].setMaxSourceNumber(2,2,2);
		progresses[4].setMaxSourceNumber(4,3,3);
		/*�趨5�������Ѿ��������Դ��Ŀ*/
		progresses[0].setAllocSourceNumber(0,1,0);
		progresses[1].setAllocSourceNumber(2,0,0);
		progresses[2].setAllocSourceNumber(3,0,2);
		progresses[3].setAllocSourceNumber(2,1,1);
		progresses[4].setAllocSourceNumber(0,0,2);	
		
		for(int i = 0; i < 5; i++){
			progresses[i].setName(i);//�趨5�����̵�����
			progresses[i].setNeedSourceNumber();//�趨5������ʣ���������Դ��Ŀ
		}
		
		AvailableResource.setResouceNumber(3,3,2);
		
	}

	public boolean SafeCheck(){
		Resource Work = new Resource();
		Work.setResouceNumber(AvailableResource.getResouceA(), AvailableResource.getResouceB(), AvailableResource.getResouceC());
		//Work = AvailableResource;����ش���
		boolean Finish[] = {false,false,false,false,false};
		int j =0;
		for (int i = 0; i < Finish.length; i++) {
			if (Finish[i] == false) {
				if (progresses[i].NeedSource.getResouceA() <= Work.getResouceA() 
						&& progresses[i].NeedSource.getResouceB() <= Work.getResouceB()
						&& progresses[i].NeedSource.getResouceC() <= Work.getResouceC()) {
					
					Work.setResouceNumber(progresses[i].AllocSource.getResouceA() + Work.getResouceA(),
							progresses[i].AllocSource.getResouceB() + Work.getResouceB(),
							progresses[i].AllocSource.getResouceC() + Work.getResouceC());
					//���м���������һ�Ƿ�����Դ�����̣����ǽ����ͷŷ���Ľ��̡����յĽ���ǿ�ʹ�õ���Դ = ԭ����ʹ�õ���Դ 
					//+ �����ȥ����Դ�������Ѿ��ͷŻ��ջ����ˣ�
					Finish[i] = true;
					SafeArgs[j++] = i;
					i = -1;//���±����������̣���
				}
				
			}
			
		}
		
		for(int i = 0;i < 5; i++){
			if (Finish[i] == false) {
				return false;
			}
		}

		return true;
	}

	public boolean Request(int RequesSource []){
		System.out.println("Running there4!");
		System.out.println("Running there5!");
		if (RequesSource[1] <= progresses[RequesSource[0]].NeedSource.getResouceA() &&
				RequesSource[2] <= progresses[RequesSource[0]].NeedSource.getResouceB() &&
				RequesSource[3] <= progresses[RequesSource[0]].NeedSource.getResouceC()) {
			if (RequesSource[1] <= AvailableResource.getResouceA() &&
				RequesSource[2] <= AvailableResource.getResouceB() &&
				RequesSource[3] <= AvailableResource.getResouceC()) {
				ProbeAlloc(RequesSource);
				if(SafeCheck()){
					System.out.println("����ɹ�");
					info.setText("����ɹ�");
					safeArgsInfo.setText("" + "p" +SafeArgs[0] + "p"+ SafeArgs[1]+ "p" + SafeArgs[2] + "p" + SafeArgs[3] + "p" + SafeArgs[4]);
					return true;
				}else {
					System.out.println("����ʧ�ܡ�ԭ��ϵͳ�����벻��ȫ״̬���п�������������");
					info.setText("��ȫ�Լ��ʧ�ܡ�ԭ��ϵͳ�����벻��ȫ״̬���п�������������\n ��̽�Է��佫��ع���");
					safeArgsInfo.setText("�ް�ȫ����");
					RollBack(RequesSource);
				}
			}else{
				System.out.println("����ʧ�ܡ�ԭ�������������ڿ�������Դ������");
				info.setText("��ȫ�Լ��ʧ�ܡ�ԭ�������������ڿ�������Դ������\n");
				safeArgsInfo.setText("�ް�ȫ����");
			}
		}else {
			System.out.println("����ʧ�ܡ�ԭ������������������������");
			info.setText("��ȫ�Լ��ʧ�ܡ�ԭ������������������������\n");
			safeArgsInfo.setText("�ް�ȫ����");
		}
		return false;
	}
	
	public void ProbeAlloc(int RequesSource []){
		System.out.println("Running there!6");
		System.out.println("��ǰ������Դ�Ľ���" + RequesSource[0]);
		System.out.println("����A��Ŀ��" + RequesSource[1]);
		System.out.println("����B��Ŀ��" + RequesSource[2]);
		System.out.println("����C��Ŀ��" + RequesSource[3]);
		int tempA = AvailableResource.getResouceA() ;
		int tempB = AvailableResource.getResouceB() ;
		int tempC = AvailableResource.getResouceC() ;
		System.out.printf("A : %d, B: %d, C:%d ",tempA, tempB, tempC);
		AvailableResource.setResouceNumber(AvailableResource.getResouceA() - RequesSource[1], 
				AvailableResource.getResouceB() - RequesSource[2],
				AvailableResource.getResouceC() - RequesSource[3]);
		progresses[RequesSource[0]].AllocSource.setResouceNumber(progresses[RequesSource[0]].AllocSource.getResouceA() + RequesSource[1],
				progresses[RequesSource[0]].AllocSource.getResouceB() + RequesSource[2],
				progresses[RequesSource[0]].AllocSource.getResouceC() + RequesSource[3]);
		
		progresses[RequesSource[0]].NeedSource.setResouceNumber(progresses[RequesSource[0]].NeedSource.getResouceA() - RequesSource[1],
				progresses[RequesSource[0]].NeedSource.getResouceB() - RequesSource[2],
				progresses[RequesSource[0]].NeedSource.getResouceC() - RequesSource[3]);
		System.out.println("Running there!7");
		System.out.println("Avalible: " + AvailableResource.gerResouceNumber());
	}
	
	public void RollBack(int RequesSource []){
		AvailableResource.setResouceNumber(AvailableResource.getResouceA() + RequesSource[1], 
				AvailableResource.getResouceB() + RequesSource[2],
				AvailableResource.getResouceC() + RequesSource[3]);
		progresses[RequesSource[0]].AllocSource.setResouceNumber(progresses[RequesSource[0]].AllocSource.getResouceA() - RequesSource[1],
				progresses[RequesSource[0]].AllocSource.getResouceB() - RequesSource[2],
				progresses[RequesSource[0]].AllocSource.getResouceC() - RequesSource[3]);
		
		progresses[RequesSource[0]].NeedSource.setResouceNumber(progresses[RequesSource[0]].NeedSource.getResouceA() - RequesSource[1],
				progresses[RequesSource[0]].NeedSource.getResouceB() - RequesSource[2],
				progresses[RequesSource[0]].NeedSource.getResouceC() - RequesSource[3]);
	}
	
	public static boolean isNumeric(String str){  
		  for (int i = str.length();--i>=0;){    
		   if (!Character.isDigit(str.charAt(i))){  
		    return false;  
		   }  
		  }  
		  return true;  
		}  
}
