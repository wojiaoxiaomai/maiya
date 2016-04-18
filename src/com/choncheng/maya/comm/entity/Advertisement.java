package com.choncheng.maya.comm.entity;

import java.io.Serializable;

/**
 * 
 * @项目名称：Maya
 * @类名称：Advertisement
 * @类描述： 广告实体
 * @创建人：李波
 * @创建时间：2015-8-14 下午2:33:09
 * @版本号：v1.0
 * 
 */
public class Advertisement implements Serializable {
	private String ad_id;
	private String ad_name;
	private String ad_image;
	private String photos;// 广告位下的图片
	private String expend1;
	private String expend2;

	public String getAd_id() {
		return ad_id;
	}

	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}

	public String getAd_name() {
		return ad_name;
	}

	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}

	public String getAd_image() {
		return ad_image;
	}

	public void setAd_image(String ad_image) {
		this.ad_image = ad_image;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getExpend1() {
		return expend1;
	}

	public void setExpend1(String expend1) {
		this.expend1 = expend1;
	}

	public String getExpend2() {
		return expend2;
	}

	public void setExpend2(String expend2) {
		this.expend2 = expend2;
	}

}
