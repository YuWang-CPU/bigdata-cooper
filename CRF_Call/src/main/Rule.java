package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

//设计想法：通过设计规则发现QA、文本中的实体
/*	从规则出发，将实体分为两类：一类是独立实体（如：翼起玩）、另一类是组合型实体（如：爱动漫 -》爱动漫xxxx）
 * 组合类实体： b+o+e型
 * 	1、将之前处理实体分类的结果作为第一类关键词，由于很多实体都是基于根实体，如：爱动漫 -》爱动漫xxxx
 *  2、将一些电信实体常用词作为第二类关键词，如xxx套餐、xxx流量包等
 *  3、将第一类关键词作为实体开头参数-b、第二类关键词作为实体结尾参数-e
 *	4、当发现规则1却未发现规则2的情况下，利用分词工具、词性标注进行计算，对实体结尾词进行判断
 *	
 */
public class Rule {
	public static HashSet<String> rule(String fileTo) {
		
		Rule rule = new Rule();
		HashSet<String> rEn = new HashSet<String>();
		rEn=rule.getRule(fileTo);
		return rEn;
		
	}
	//获取role
	public HashSet<String>  getRule(String fileTo){
		ArrayList<String> roleB = new ArrayList<String>();
		String filePath = "lib//repository//root.txt";			//获取语料路径
		roleB=readTxtFile(filePath);				//读取语料文本，并保存为arraylist格式
		HashMap<String,Double> roleBMap = new HashMap<String, Double>();
		for (String string : roleB) {
			String [] kv = string.split(" ");
			roleBMap.put(kv[0], Double.parseDouble(kv[1]));
		}
		ArrayList<String> roleE = new ArrayList<String>();
		String filePath1 = "lib//repository//end.txt";			//获取语料路径
		roleE=readTxtFile(filePath1);				//读取语料文本，并保存为arraylist格式	
		HashMap<String,Double> roleEMap = new HashMap<String, Double>();
		for (String string : roleE) {
			String [] kv = string.split(" ");
			roleEMap.put(kv[0], Double.parseDouble(kv[1]));
		}
		String context = getXml(fileTo);
		HashSet<String> ruleEn = new HashSet<String>();
		return ruleEn=calculate(roleBMap, roleEMap, context);
		
	}
	public String getXml(String fileTo){
//		String filePath = "lib//test.txt";			//获取语料路径
		String context="";
		try {
			//String encoding="ANSI";
			File file=new File(fileTo);
			if(file.isFile() && file.exists()){
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file),"gb2312");
						BufferedReader bufferedReader = new BufferedReader(read);
						String lineTxt = null;
						while((lineTxt = bufferedReader.readLine()) != null){
							context+=lineTxt;  
						}
						read.close();
			}else{
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	return context;
	}
//	读取文本保存为arraylist
	public  ArrayList<String> readTxtFile(String filePath){
		ArrayList<String> arr = new ArrayList<String>();
		try {
				//String encoding="ANSI";
				File file=new File(filePath);
				if(file.isFile() && file.exists()){
					InputStreamReader read = new InputStreamReader(
							new FileInputStream(file));
							BufferedReader bufferedReader = new BufferedReader(read);
							String lineTxt = null;
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
//	计算
	public HashSet<String> calculate(HashMap<String,Double> rb,HashMap<String,Double> re ,String context){
		ArrayList<String> b = new ArrayList<String>();
		HashSet<String> ent = new HashSet<String>();
		Iterator it = rb.keySet().iterator();
		while(it.hasNext()){
		String key = (String) it.next();
		if(context.contains(key)){
			String en = context.substring(context.indexOf(key), context.indexOf(key)+15);
//			System.out.println(en);
				b.add(en);
			}
		}
		
		for (String string : b) {
			Iterator it1 = re.keySet().iterator();
			while(it1.hasNext()){
				String key = (String) it1.next();
				if(string.contains(key)){
					String en = string.substring(0, string.indexOf(key)+key.length());
//					System.out.println(en);
					ent.add(en);
					break;
					}
				}
		
		}
		return ent;
	
		
	}
}
