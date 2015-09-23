package org.developerworld.commons.collect.contentprovider;

import org.developerworld.commons.collect.ContentProvider;

/**
 * 简单字符串内容提供器
 * @author Roy Huang
 * @version 20121210
 *
 */
public class StringContentProvider implements ContentProvider{
	
	private String content;
	
	public StringContentProvider(){
		
	}
	
	public StringContentProvider(String content){
		this.content=content;
	}
	
	public void setContent(String content){
		this.content=content;
	}

	public String getContent() {
		return content;
	}

}
