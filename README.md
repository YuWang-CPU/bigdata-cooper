# 命名实体识别CRF代码模块README
## 1.模块实现功能
	1.读入.txt、.doc、.xml等文件。
	2.将文件中内容与实体库进行精确匹配得到实体。
	3.根据规则对文本内容进行规则实体识别。
	4.根据CRF对文本内容进行实体识别。

## 2.代码结构说明
	CRF_Call
	————src
	——————main
	——————————IKsegment.java	分词工具
	——————————ReadTxt.java		主函数、程序入口。完成精确匹配实体和CRF实体识别功能。调用其它三个.java文件
	——————————Rule.java			完成规则实体识别功能
	——————————StanfrodChinesePosTest.java  词性标注工具
	——————tagger				stanford词性标注必要文件
	——————test.dic				分词工具必要文件（词典、停用词）
	——————IKAnalyzer.cfg.xml	分词工具必要文件
	——————IKAnalyzer.cfg.xml.bak	分词工具必要文件
	————lib
	——————repository
	————————————————AllEntity.txt	所有实体名
	————————————————end.txt			实体结尾规则
	————————————————root.txt		实体开始规则
	——————crf_learn.exe				CRF必要文件
	——————crf_test.exe				CRF必要文件
	——————libcrfpp.dll				CRF必要文件
	——————model						CRF必要模型文件
	——————output.txt				CRF生成文件
	——————template					CRF必要文件
	——————test.txt					程序输入测试文件
	——————test.data					CRF功能必要文件
	——————test-entity.txt			程序输出文件
	——————train.data				CRF功能训练集（用于后期调优）
	————test.bat					CRF测试必要文件
	————train.bat					CRF训练必要文件
	
	代码环境jdk1.8 
	IKAnalyzer2012_FF.jar	分词架包
	stanford-postagger-3.5.2.jar	词性标注架包

## 3.程序输入：test.txt

## 4.程序输出：test-entity.txt
