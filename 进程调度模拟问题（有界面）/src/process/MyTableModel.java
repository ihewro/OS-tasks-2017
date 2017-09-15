package process;

import java.awt.Font;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel{

	String Name[] = {"name", "runtime", "status", "priority"};
	Object Data[][] = new Object[20][4];
	public MyTableModel() {
		// TODO 自动生成的构造函数存根
        for (int i = 0; i < 20; i++) {
        	Data[i][0] = "";
        	Data[i][1] = "";
        	Data[i][2] = "";
        	Data[i][3] = "";
        }
	}
	@Override
	public int getColumnCount() {
		// TODO 自动生成的方法存根
		return Name.length;
	}

	@Override
	public int getRowCount() {
		// TODO 自动生成的方法存根
		return Data.length;
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO 自动生成的方法存根
		return Name[column];
	}
	

	@Override
	public Object getValueAt(int column, int row) {
		// TODO 自动生成的方法存根
		return Data[column][row];
	}
	
	/*public static void reloadJTable(MyTableModel dtm, List<PCB> pcbs, JTable table){
        for (int i = 0; i < pcbs.size(); i++) {
        	dtm.setValueAt(pcbs.get(i).getName(), i, 0);
        	dtm.setValueAt(pcbs.get(i).getRunTime(), i, 1);
        	dtm.setValueAt(pcbs.get(i).getStatus(), i, 1);
        }
        table = new JTable(dtm);
	}*/
	
    public static void reloadJTable(MyTableModel dtm, List<PCB> pcbs, JTable table, int num){
    	String[] Names={"Name", "Runtime", "Status", "priority"};
        String[][] tablepcbs = new String[20][4];
        //System.out.println("就绪队列的pcb数目" + readypcbs.size());
        //遍历List
        for (int i = 0; i < pcbs.size(); i++) {
        	tablepcbs[i][0] = pcbs.get(i).getName();
        	tablepcbs[i][1] = "" + pcbs.get(i).getRunTime();
        	tablepcbs[i][2] = "" + pcbs.get(i).getStatus();
        	tablepcbs[i][3] = "" + pcbs.get(i).getPriority();
        }

        table=new JTable(tablepcbs,Names);
        table.setFont(new Font("Dialog",1,12));
        if(num ==1){
        	GlobalObject.setjTable(table);
            GlobalObject.getjScrollPane().setViewportView(table);	
        }else{
        	GlobalObject.setjTable2(table);
            GlobalObject.getjScrollPane2().setViewportView(table);	
        }
        
   
    }

}
