package banks;

public class Progress {
	int name;
	Resource MaxSource  =  new Resource();//һ����Ҫ�ܵ���Դ��Ŀ
	Resource NeedSource = new Resource();//����Ҫ����Դ��Ŀ
	Resource AllocSource  = new Resource();//�Ѿ��������Դ��Ŀ
	
	/*���ý��̵������Ϣ�����ơ����������Դ��Ŀ��������Դ��Ŀ���Ѿ�������Դ��Ŀ*/
	public void setName(int name){
		this.name = name;
	}
	
	public void setMaxSourceNumber(int A, int B, int C){
		System.out.println("Runnnig there!3");
		MaxSource.setResouceNumber(A, B, C);
	}
	public void setAllocSourceNumber(int A, int B, int C){
		AllocSource.setResouceNumber(A, B, C);
	}
	public void setNeedSourceNumber(){
		int NeedA = MaxSource.getResouceA() - AllocSource.getResouceA();
		int NeedB = MaxSource.getResouceB() - AllocSource.getResouceB();
		int NeedC = MaxSource.getResouceC() - AllocSource.getResouceC();
		NeedSource.setResouceNumber(NeedA, NeedB, NeedC);
	}
	
	

	
}
