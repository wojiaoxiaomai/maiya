package com.choncheng.maya.homepage;

import java.util.List;

import com.choncheng.maya.comm.entity.Advertisement;
import com.choncheng.maya.comm.entity.Goods;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�DiscountAndHotGoods
 * @�������� ��ҳ������ػ��Լ���������Ʒ
 * @�����ˣ��
 * @����ʱ�䣺2015-8-8 ����5:12:23
 * @�汾�ţ�v1.0
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
