package com.choncheng.maya.search;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.Goods;
import com.choncheng.maya.login.LoginActivity;
import com.choncheng.maya.shoppingcart.GoodsDetail;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @项目名称：Maya
 * @类名称：SearchFilterAdapter
 * @类描述： 过滤搜索商品（按分类，价格，销量排序）
 * @创建人：李波
 * @创建时间：2015-8-9 下午11:14:45
 * @版本号：v1.0
 * 
 */
public class SearchFilterAdapter extends BaseAdapter {
	private List<GoodsDetail> mList;
	private Context mContext;

	public SearchFilterAdapter(List<GoodsDetail> mList, Context mContext) {
		super();
		this.mList = mList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyHolder holder = null;
		if (convertView == null) {
			holder = new MyHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.search_filter_item, null);
			holder.goodImage = (ImageView) convertView
					.findViewById(R.id.good_image);
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			holder.actualPriceTxt = (TextView) convertView
					.findViewById(R.id.actual_price_txt);
			holder.beforePriceTxt = (TextView) convertView
					.findViewById(R.id.before_price_txt);
			holder.addToShopcartImage = (ImageView) convertView
					.findViewById(R.id.gouwuche_image);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}

		GoodsDetail goodsDetail=mList.get(position);
		holder.addToShopcartImage.setTag(position);
		holder.addToShopcartImage
				.setOnClickListener(new AddToShopcartClickListener());
		holder.nameTxt.setText(goodsDetail.getGoods_name());
		holder.actualPriceTxt.setText(CommUtils.getMoney(goodsDetail
				.getGoods_price()));
		holder.beforePriceTxt.setText(CommUtils.getMoney(goodsDetail
				.getOld_price()));
		holder.beforePriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		//==>2015.12.9 修改价格展示
		if(goodsDetail.getGoods_price().equals(goodsDetail.getOld_price())){
			holder.beforePriceTxt.setVisibility(View.GONE);
		}else{
			holder.beforePriceTxt.setVisibility(View.VISIBLE);
			holder.beforePriceTxt.setText(CommUtils.getMoney(goodsDetail.getOld_price()));
		}
		//<==2015.12.9 
		ImageLoader.getInstance().displayImage(
				CommUtils.getImageUrl(goodsDetail.getGoods_image()),
				holder.goodImage, BitmapUtil.getDefaultOption());
		return convertView;
	}

	/*
	 * 加入购物车点击事件
	 */
	private class AddToShopcartClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			GoodsDetail goodsDetail = mList.get(position);
			if (MyApplication.getInstance().isLogin()) {
				if (goodsDetail != null) {
					if (goodsDetail.getStock() - goodsDetail.getVirtual_sales() > 0) {
						// 有货物
						shopcartaddAdd(MyApplication.getInstance().getUser()
								.getUcode(), goodsDetail.getGoods_id(),
								goodsDetail.getSpec_id(),
								goodsDetail.getGoods_sn(),
								goodsDetail.getShop_id(),
								goodsDetail.getGoods_name(),
								goodsDetail.getGoods_image(), 1 + "");
					} else {
						Toast.makeText(mContext, R.string.godds_empty,
								Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				Intent it = new Intent(mContext, LoginActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				mContext.startActivity(it);
			}

		}
	}

	/**
	 * 添加到购物车
	 * 
	 * @param ucode
	 * @param goods_id
	 * @param spec_id
	 * @param specification
	 * @param shop_id
	 * @param goods_name
	 * @param goods_image
	 * @param quantity
	 */
	private void shopcartaddAdd(String ucode, String goods_id, String spec_id,
			String specification, String shop_id, String goods_name,
			String goods_image, String quantity) {
		API.shopcartaddAdd(mContext,ucode, goods_id, spec_id, specification, shop_id,
				goods_name, goods_image, quantity, new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						switch (state) {
						case STATE_OK:
							Toast.makeText(mContext,
									R.string.addto_shopcar_sucess,
									Toast.LENGTH_SHORT).show();
							break;
						case STATE_FAIL:
							Toast.makeText(mContext, data, Toast.LENGTH_SHORT)
									.show();
							break;
						case STATE_ERROR:
							Toast.makeText(mContext,
									R.string.http_respone_error,
									Toast.LENGTH_SHORT).show();
							break;

						default:
							break;
						}
					}
				});
	}

	private class MyHolder {
		ImageView goodImage;
		TextView nameTxt;
		TextView actualPriceTxt;
		TextView beforePriceTxt;
		ImageView addToShopcartImage;// 加入购物车
	}
}
