package process;
//package com.box.process.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PCB implements Comparable<PCB>{
	private int runTime;  //运行时间
    private  int priority;   //优先级
    private  String name; //进程名称
    private boolean isOver; //是否运行结束
    private int startTime;  //开始运行时间
    private int status;//进程状态

    /* status对应的数字码
     * 0：就绪
     * 1：运行
     * 2：阻塞
     * -1：结束
     * */
    public int getRunTime() {
		return runTime;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}


	public int getPriority() {
		return priority;
	}

	public int getStatus(){
		return status;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	public void setStatus(int status){
		this.status = status;
	}



	public boolean getIsOver() {
		return isOver;
	}


	public void setIsOver(boolean isOver) {
		this.isOver = isOver;
	}


	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public void decrease() {
		 runTime--;
		 if(runTime<=0){
			 isOver=true;
		 }
	}
	
	

	public void Json2Object(JSONObject jsonObject) {
		setName(jsonObject.getString("name"));
		setStartTime(jsonObject.getInteger("startTime"));
		setIsOver(jsonObject.getBooleanValue("isOver"));
		setPriority(jsonObject.getIntValue("priority"));
		setRunTime(jsonObject.getIntValue("runTime"));
	}

	public static List<PCB> JSONArray2ObjectList(JSONArray jsonArray) {
		List<PCB> pcbs=new ArrayList<>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			PCB pcb=new PCB();
			pcb.Json2Object(jsonObject);
			pcbs.add(pcb);
		}
		return pcbs;
	}
	
	public  static String loadProcess() {
		URL xmlpath=MainClass.class.getClassLoader().getResource("");

		String encoding="utf-8";

		try {
			File file=new File(xmlpath.toString().replace("file:/", "")+"process/JOB.txt");
			if(file.isFile() && file.exists()){ 
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file),encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				StringBuffer buffer=new StringBuffer();
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null){
					buffer.append(lineTxt);
				}
				String message=buffer.toString();
				read.close();
				return message;
			}else{
				System.out.println("打开文件失败");
			}
		} catch (Exception e) {
			System.out.println("加载文件失败");
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int compareTo(PCB arg0) {
		// TODO 自动生成的方法存根
		if (this.getPriority() > arg0.getPriority()){
			return -1;
		}else if (this.getPriority() < arg0.getPriority()) {
			return 1;
		}else {
			return 0;
		}
	}



}
