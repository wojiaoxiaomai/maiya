package com.choncheng.maya.homepage;

import java.util.List;

import com.choncheng.maya.comm.entity.Advertisement;
import com.choncheng.maya.comm.entity.Goods;

/**
 * 
 * @项目名称：Maya
 * @类名称：DiscountAndHotGoods
 * @类描述： 首页下面的特惠以及热销的商品
 * @创建人：李波
 * @创建时间：2015-8-8 下午5:12:23
 * @版本号：v1.0
 * 
 */
public class DiscountAndHotGoods {
	private Advertisement advertisement;
	private List<Goods> goodList;

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public List<Goods> getGoodList() {
		return goodList;
	}

	public void setGoodList(List<Goods> goodList) {
		this.goodList = goodList;
	}

}
