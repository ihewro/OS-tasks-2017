package banks;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
	Object[][] progressResource = new String[5][5];//���е�����
	String[] Names={"process", "Max", "Allocation", "Need", "Availble"};//������
	public MyTableModel() {
		// TODO �Զ����ɵĹ��캯�����
        for (int i = 0; i < 5; i++) {
        	progressResource[i][0] = "p" + i;
        }
	}
	@Override
	/*��ȡ������*/
	public String getColumnName(int column)
	{
		return Names[column];
	}
	@Override
	/*��ȡ����Ŀ*/
	public int getColumnCount() {
		// TODO �Զ����ɵķ������
		//�õ��������
		return Names.length;
	}

	@Override
	/*��ȡ����Ŀ*/
	public int getRowCount() {
		// TODO �Զ����ɵķ������
		//�õ��������
		return progressResource.length;
	}

	@Override
	/*��ȡĳһλ�õ�ֵ*/
	public Object getValueAt(int rowIndex, int columIndex) {
		// TODO �Զ����ɵķ������
		return progressResource[rowIndex][columIndex];
	}
	@Override
	/*����ĳһλ�õ�ֵ*/
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		progressResource[rowIndex][columnIndex] = (String) aValue;
		/*֪ͨ���������ݵ�Ԫ�����Ѿ��ı�*/
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public static void reloadJTable(MyTableModel dtm){
    	Main Maininstance = new Main();
    	Progress[] progresses = Maininstance.GetProgresses();
		for (int i = 0; i < 5;i++){
			dtm.setValueAt("p" + i, i, 0);
			dtm.setValueAt(progresses[i].MaxSource.gerResouceNumber(), i, 1);
			dtm.setValueAt(progresses[i].AllocSource.gerResouceNumber(), i, 2);
			dtm.setValueAt(progresses[i].NeedSource.gerResouceNumber(), i, 3);
		}
		dtm.setValueAt(Maininstance.GetAvailableResource().gerResouceNumber(), 0, 4);
		
		Maininstance.GetJTable().updateUI();
	}
	
	
}
