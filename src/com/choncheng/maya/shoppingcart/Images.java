package com.choncheng.maya.shoppingcart;

import java.io.Serializable;

/**
 * 
 * @项目名称：Maya
 * @类名称：Images
 * @类描述： 产品详情里面轮播图片的实体
 * @创建人：李波
 * @创建时间：2015-8-16 下午8:42:49
 * @版本号：v1.0
 * 
 */
public class Images implements Serializable {
	private String goods_id;
	private String image_url;
	private String thumbnail;
	private String sort_order;

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getSort_order() {
		return sort_order;
	}

	public void setSort_order(String sort_order) {
		this.sort_order = sort_order;
	}

}
