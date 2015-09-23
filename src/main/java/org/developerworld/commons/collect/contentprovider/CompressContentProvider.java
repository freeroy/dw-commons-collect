package org.developerworld.commons.collect.contentprovider;

import org.apache.commons.lang.StringUtils;
import org.developerworld.commons.collect.ContentProvider;

/**
 * 压缩内容提供器
 * 
 * @author Roy Huang
 * @version 20121210
 * 
 */
public class CompressContentProvider implements ContentProvider {

	private ContentProvider contentProvider;

	public CompressContentProvider(ContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	public String getContent() {
		String rst = contentProvider.getContent();
		if (StringUtils.isNotBlank(rst))
			rst = rst.replaceAll("(?<=\\>)(?:\\s*\r?\n?)(?=\\<)", "");
		return rst;
	}

}
