package philosopherseating;

public class PhilosophersEating {

    public static void main(String []args){
        Fork fork = new Fork();
        new Philosopher("0",fork).start();
        new Philosopher("1",fork).start();
        new Philosopher("2",fork).start();
        new Philosopher("3",fork).start();
        new Philosopher("4",fork).start();
    }
}
/*ÿ����ѧ���൱��һ���߳�*/
class Philosopher extends Thread{
    private String name;
    private Fork fork;
    public Philosopher(String name,Fork fork){
        super(name);
        this.name=name;
        this.fork=fork;
    }
    
    public void run(){
        while(true){
            thinking();
            fork.takeFork();
            eating();
            fork.putFork();
        }
        
    }
    
    
    public void eating(){
        System.out.println("I am Eating:"+name);
        try {
            sleep(1000);//ģ��Է���ռ��һ��ʱ����Դ
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    public void thinking(){
        System.out.println("I am Thinking:"+name);
        try {
            sleep(1000);//ģ��˼��
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class Fork{
    /*5ֻ���ӣ���ʼΪ��δ����*/
    private boolean[] used={false,false,false,false,false,false};
    
    /*ֻ�е������ֵĿ��Ӷ�δ��ʹ��ʱ���������ȡ���ӣ��ұ���ͬʱ��ȡ�����ֿ���*/
    public synchronized void takeFork(){
        String name = Thread.currentThread().getName();
        int i = Integer.parseInt(name);
        while(used[i]||used[(i+1)%5]){
            try {
                wait();//�����������һֻ����ʹ�ã��ȴ�
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        used[i ]= true;
        used[(i+1)%5]=true;
    }
    
    /*����ͬʱ�ͷ������ֵĿ���*/
    public synchronized void putFork(){
        String name = Thread.currentThread().getName();
        int i = Integer.parseInt(name);
        
        used[i ]= false;
        used[(i+1)%5]=false;
        notifyAll();//���������߳�
    }
}

