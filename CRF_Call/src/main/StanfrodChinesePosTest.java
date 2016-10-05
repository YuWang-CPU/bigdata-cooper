package main;


import java.io.FileWriter;
import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

class StanfrodChinesePosTest {
	// 主函数
	public static String tagger(String str) {
		// 步骤一、创建 MaxentTagger 对象
		MaxentTagger tagger = new MaxentTagger("taggers/chinese-distsim.tagger");

		// 步骤二、样例
//		Text_import tt = new Text_import();			
//		try {
//			str = tt.textread("lib//Answer.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	
		// 步骤三、标记字符串
		String tagged = tagger.tagString(str);

		// 步骤四、输出结果
		//System.out.println(tagged);
		
				
		return tagged;
	}
}
