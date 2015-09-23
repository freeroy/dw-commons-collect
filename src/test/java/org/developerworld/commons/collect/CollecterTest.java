package org.developerworld.commons.collect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.developerworld.commons.collect.collecter.SimpleCollecter;
import org.developerworld.commons.collect.contentprovider.UrlContentProvider;
import org.developerworld.commons.collect.datahandler.ConsoleDataHandler;
import org.developerworld.commons.collect.datahandler.SimpleMultipleCollecterDataHandler;
import org.developerworld.commons.collect.datahandler.SimpleMultipleCollecterDataHandler.ContentProviderBuilder;
import org.developerworld.commons.collect.datahandler.SimpleMultipleCollecterDataHandler.FindersBuilder;
import org.developerworld.commons.collect.finder.MultipleMatchFinder;
import org.developerworld.commons.collect.finder.RegularMatchFinder;
import org.junit.Assert;
import org.junit.Test;

public class CollecterTest {

	String keyword = "iphone";

	/**
	 * 先通过列表页获取地址集合
	 * 再二次访问地址集合，获取详细页内容
	 */
	//@Test
	public void testCollect() {
		// 构建采集器对象
		Collecter collecter = new SimpleCollecter();
		// 执行采集
		collecter.collect(buildContentProvider1(), buildFinders(),
				buildDataHandler());
		Assert.assertTrue(true);
	}

	/**
	 * 构建内容提供器
	 * 
	 * @return
	 */
	private ContentProvider buildContentProvider1() {
		return new UrlContentProvider(
				"http://bbs.weiphone.com/forum.php?mod=rss&fid=526&auth=0",
				"utf-8");
	}

	/**
	 * 构建查找器
	 * 
	 * @return
	 */
	private Map<String, Finder> buildFinders() {
		Map<String, Finder> rst = new HashMap<String, Finder>();
		// 在标题查找关键字
		rst.put("detailUrl",
				new MultipleMatchFinder()
				// 筛选范围
						.addFinder(
								new RegularMatchFinder("<item>(.*?)</item>",
										"${1}"))
						// 匹配内容
						.addFinder(
								new RegularMatchFinder("<title>.*?" + keyword
										+ ".*?</title>.*?<link>(.*?)</link>",
										"${1}")));
		// 在内容查找关键字
		rst.put("detailUrl2",
				new MultipleMatchFinder()
						// 筛选范围
						.addFinder(
								new RegularMatchFinder("<item>(.*?)</item>",
										"${1}"))
						// 匹配内容
						.addFinder(
								new RegularMatchFinder(
										"<link>(.*?)</link>.*?<description>.*?"
												+ keyword + ".*?</description>",
										"${1}")));

		return rst;
	}

	/**
	 * 构建内容处理器
	 * @return
	 */
	private DataHandler buildDataHandler() {
		return 
				//构建深度查找内容处理器,根据第一次查找返回的地址集合，再次查找内容
				new SimpleMultipleCollecterDataHandler(
				new ContentProviderBuilder() {

					public List<ContentProvider> build(
							Map<String, List<String>> collectData) {
						//利用第一次查找的结果，获取二级查找地址
						List<ContentProvider> rst = new ArrayList<ContentProvider>();
						List<String> urls = new ArrayList<String>();
						urls.addAll(collectData.get("detailUrl"));
						urls.addAll(collectData.get("detailUrl2"));
						for (String url : urls)
							rst.add(new UrlContentProvider(url, "utf-8"));
						//返回二级查找的所有地址
						return rst;
					}
				}, new FindersBuilder() {
					public Map<String, Finder> build(
							Map<String, List<String>> collectData) {
						//构建二级查找的目标内容
						Map<String, Finder> rst = new HashMap<String, Finder>();
						rst.put("title", new RegularMatchFinder(
								"<span id=\"thread_subject\">(.*?)</span>",
								"${1}"));
						rst.put("content",
								new RegularMatchFinder(
										"<div class=\"t_fsz\">.*?<table.*?<tr.*?<td.*?>(.*?)</td>.*?<tr>.*?</table>",
										"${1}"));
						return rst;
					}
				}, 
				//控制台输出内容
				new ConsoleDataHandler());
	}

}
