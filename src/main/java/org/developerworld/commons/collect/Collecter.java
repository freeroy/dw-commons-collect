package org.developerworld.commons.collect;

import java.util.Map;

/**
 * 采集器
 * 
 * @author Roy Huang
 * @version 20121210
 * 
 */
public interface Collecter {

	/**
	 * 执行采集
	 * @param contentProvider
	 * @param finders
	 * @param dataHandler
	 */
	public void collect(ContentProvider contentProvider,
			Map<String, Finder> finders, DataHandler dataHandler);

}
