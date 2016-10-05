package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;


public class ReadTxt {
	static ArrayList<String>  entity = new ArrayList<String>();		//存储发现的所有的实体
	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {
        	//精确匹配实体,对文本内容进行预处理
			ArrayList<String> ent = new ArrayList<String>();
			ent=readAllEntity("lib//repository//AllEntity.txt");
			String filePath = "lib/test.txt";
			ArrayList<String> arrs = new ArrayList<String>();
	        arrs = readTxtFile(filePath,ent);
	        //arr中存储文本内容预处理数据
	        String line = "";
	        for (String string : arrs) {
				line+=string;
			}
	        Text_export.expString(line);//将预处理文件输出到test.data文件
			String command = "cmd.exe /c start test.bat";
			 //String command1 = "cmd.exe /c start train.bat";	//训练数据
			 try {
				Process process = Runtime.getRuntime().exec(command);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//完成CRF功能代码调用
			ArrayList<String> entityCRF = new ArrayList<String>();
			Thread.sleep(2000);
			HashSet<String> entityRule = new HashSet<String>();
			entityRule=Rule.rule(filePath);//调用规则识别实体程序，得到规则实体
			entityCRF=readop("lib//output.txt");//调用CRF识别结果文件，并对结果文件进行格式处理，得到CRF实体
			entity.addAll(entityRule);
			entity.addAll(entityCRF);
			HashSet<String> set = new HashSet<String>();
			for (String string : entity) {
				set.add(string);
			}
			Text_export.expSet(set,filePath); // 将所有实体去重输出
	}
		
	
	//函数对输入文件进行实体精确匹配并将格式调整成CRF可识别的
		public static ArrayList<String> readTxtFile(String filePath,ArrayList<String> Allentity){
			ArrayList<String> arr = new ArrayList<String>();
	        try {
	                //String encoding="ANSI";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),"gb2312");//
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                    	if(lineTxt!=""){
	                    	for (String string : Allentity) {
								if(lineTxt.contains(string)){
									entity.add(string);//获取精确匹配结果
								}
							}
	                    	String line =StanfrodChinesePosTest.tagger(IKsegment.IKAnalysis(lineTxt));
	                    	String a = line.replaceAll("#", "	").replaceAll(" ", "	O\r\n");
	                    	arr.add(a);	//调整为CRF可识别格式
	                    	}
	                    }
	                    read.close();
	        }else{
	            System.out.println("找不到指定的文件");
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	        }
			return arr;
	}
		
	//函数对CRF处理结果文件进行格式调整
		public static ArrayList<String> readop(String filePath){
			ArrayList<String> arr = new ArrayList<String>();
	        try {
	                //String encoding="ANSI";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),"gb2312");//
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    String en = "";
	                    int temp = 0;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                    	if(lineTxt.contains("B-BUS")){
	                    		en+=lineTxt.substring(0, lineTxt.indexOf("	"));
	                    		temp=1;
	                    	}else if(lineTxt.contains("I-BUS")){
	                    		en+=lineTxt.substring(0, lineTxt.indexOf("	"));
	                    	}
	                    	else if(lineTxt.contains("O	O")&&temp==1){
	                    		
	                    		temp=0;
	                    		arr.add(en);
	                    		en="";
	                    	}
	                    }
	                    read.close();
	        }else{
	            System.out.println("找不到指定的文件");
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	        }
			return arr;
	}
		
		//获取Allentity中所有的实体
		public static ArrayList<String> readAllEntity(String filePath){
			ArrayList<String> arr = new ArrayList<String>();
	        try {
	                //String encoding="ANSI";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file));//
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    String en = "";
	                    int temp = 0;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                    		arr.add(lineTxt);
	                    }
	                    read.close();
	        }else{
	            System.out.println("找不到指定的文件");
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	        }
			return arr;
	     
	    
	}
}

//输出类
class Text_export{
	public static void expString(String word){
		try {
	    	OutputStreamWriter pw = null;//定义一个流
	    	pw = new OutputStreamWriter(new FileOutputStream("lib//test.data"),"gb2312");
	    	pw.write(word);
	    	pw.flush();
	    	pw.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
	}
	public static void expSet(HashSet<String> set , String filePath){
		try {
			String entityfile=filePath.replace(".txt", "-entity.txt").replace(".xml", "-entity.txt");
	    	OutputStreamWriter pw = null;//定义一个流
	    	pw = new OutputStreamWriter(new FileOutputStream(entityfile),"gb2312");
	    	pw.write(filePath);
	    	pw.write(13);
	    	pw.write(10);
	    	for (String string : set) {
	    		pw.write(string+"	");
			}
	    	pw.flush();
	    	pw.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
	}
	
}
