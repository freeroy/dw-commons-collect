package org.developerworld.commons.collect;

import java.util.List;
import java.util.Map;

/**
 * 数据处理器
 * 注意，该类的实现应设计为线程安全
 * 
 * @author Roy Huang
 * @version 20121210
 * 
 */
public interface DataHandler {

	/**
	 * 处理数据
	 * 
	 * @param data
	 */
	public void handleData(Map<String, List<String>> data);

}
