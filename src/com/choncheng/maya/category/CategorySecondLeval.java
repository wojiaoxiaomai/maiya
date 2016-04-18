package com.choncheng.maya.category;

import java.util.List;

/**
 * 
 * @项目名称：Maya
 * @类名称：CategoryFirstLeval
 * @类描述： 分类中二级的实体
 * @创建人：李波
 * @创建时间：2015-8-8 下午10:11:04
 * @版本号：v1.0
 * 
 */
public class CategorySecondLeval {
	private Category category;
	private List<Category> threeLevalList;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getThreeLevalList() {
		return threeLevalList;
	}

	public void setThreeLevalList(List<Category> threeLevalList) {
		this.threeLevalList = threeLevalList;
	}

}
