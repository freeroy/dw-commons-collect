package org.developerworld.commons.collect;

import java.util.List;

/**
 * 查找器
 * 
 * @author Roy Huang
 * @version 20121210
 * 
 */
public interface Finder {
	
	/**
	 * 查找数据
	 * @param content
	 * @return
	 */
	public List<String> find(String content);
	
}
