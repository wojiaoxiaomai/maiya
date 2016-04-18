package com.choncheng.maya.personal;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.login.LoginActivity;
import com.choncheng.maya.shoppingcart.GoodsDetail;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.sax.StartElementListener;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalCollectionAdapter
 * @类描述： 我的收藏adapter
 * @创建人：李波
 * @创建时间：2015-8-9 下午12:03:09
 * @版本号：v1.0
 * 
 */
public class PersonalCollectionAdapter extends BaseAdapter {
	private List<PersonalCollection> mList;
	private Context mContext;

	public PersonalCollectionAdapter(List<PersonalCollection> mList,
			Context mContext) {
		super();
		this.mList = mList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
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
					R.layout.personal_collection_item, null);
			holder.goodImage = (ImageView) convertView
					.findViewById(R.id.good_image);
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			holder.actualPriceTxt = (TextView) convertView
					.findViewById(R.id.actual_price_txt);
			holder.beforePriceTxt = (TextView) convertView
					.findViewById(R.id.before_price_txt);

			holder.collectImage = (ImageView) convertView
					.findViewById(R.id.shoucang_image);
			holder.shopcartImage = (ImageView) convertView
					.findViewById(R.id.gouwuche_image);
			holder.tag1 = (TextView) convertView.findViewById(R.id.tag1);
			holder.tag2 = (TextView) convertView.findViewById(R.id.tag2);
			holder.tag3 = (TextView) convertView.findViewById(R.id.tag3);
			holder.goodStateTxt = (TextView) convertView
					.findViewById(R.id.goods_state_txt);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}

		List<String> tagList = JSON.parseArray(mList.get(position)
				.getGood_info().getExpend1(), String.class);
		if (tagList != null) {
			int size = tagList.size();
			switch (size) {
			case 0:
				holder.tag1.setVisibility(View.INVISIBLE);
				holder.tag2.setVisibility(View.INVISIBLE);
				holder.tag3.setVisibility(View.INVISIBLE);
				break;
			case 1:
				holder.tag1.setVisibility(View.VISIBLE);
				holder.tag2.setVisibility(View.GONE);
				holder.tag3.setVisibility(View.GONE);
				holder.tag1.setText(tagList.get(0));
				break;
			case 2:
				holder.tag1.setVisibility(View.VISIBLE);
				holder.tag2.setVisibility(View.VISIBLE);
				holder.tag3.setVisibility(View.GONE);
				holder.tag1.setText(tagList.get(0));
				holder.tag2.setText(tagList.get(1));
				break;
			case 3:
				holder.tag1.setVisibility(View.VISIBLE);
				holder.tag2.setVisibility(View.VISIBLE);
				holder.tag3.setVisibility(View.VISIBLE);

				holder.tag1.setText(tagList.get(0));
				holder.tag2.setText(tagList.get(1));
				holder.tag3.setText(tagList.get(2));
				break;
			default:
				break;
			}

		}
		holder.collectImage.setTag(position);
		holder.shopcartImage.setTag(position);
		holder.collectImage.setOnClickListener(new CollectClickListener());
		holder.shopcartImage.setOnClickListener(new ShopClickListener());
		holder.nameTxt.setText(mList.get(position).getGood_info()
				.getGoods_name());
		holder.actualPriceTxt.setText(CommUtils.getMoney(mList.get(position)
				.getGood_info().getGoods_price()));
		holder.beforePriceTxt.setText(CommUtils.getMoney(mList.get(position)
				.getGood_info().getOld_price()));
		holder.beforePriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		//==>2015.12.9 修改价格展示
		if((mList.get(position).getGood_info().getGoods_price()).equals(mList.get(position)
				.getGood_info().getOld_price())){
			holder.beforePriceTxt.setVisibility(View.GONE);
		}else{
			holder.beforePriceTxt.setVisibility(View.VISIBLE);
			holder.beforePriceTxt.setText(CommUtils.getMoney(mList.get(position)
					.getGood_info().getOld_price()));
		}
		//<==2015.12.9 		
		
		ImageLoader.getInstance().displayImage(
				CommUtils.getImageUrl(mList.get(position).getGood_info()
						.getGoods_image()), holder.goodImage,
				BitmapUtil.getDefaultOption());
		if (mList.get(position).getGood_info().getStock()
				- mList.get(position)
				.getGood_info().getVirtual_sales() > 0) {
			holder.goodStateTxt.setText(R.string.goods_not_empty);
			holder.goodStateTxt.setTextColor(mContext.getResources().getColor(R.color.green));
			holder.goodStateTxt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.goods_not_empty_bg));
		} else {
			holder.goodStateTxt.setText(R.string.goods_empty);
			holder.goodStateTxt.setTextColor(mContext.getResources().getColor(R.color.red));
			holder.goodStateTxt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.goods_empty_bg));
		}
		return convertView;
	}

	private class MyHolder {
		ImageView goodImage;
		TextView nameTxt;
		TextView actualPriceTxt;
		TextView beforePriceTxt;
		ImageView collectImage;// 收藏
		ImageView shopcartImage;// 购物车

		TextView tag1;// 标签显示
		TextView tag2;
		TextView tag3;
		TextView goodStateTxt;// 有货还是无货
	}

	// 点击取消收藏
	private class CollectClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			String goods_id = mList.get(position).getGood_info().getGoods_id();
			if (MyApplication.getInstance().isLogin()) {
				if (!TextUtils.isEmpty(goods_id)) {
					collectgoodsCancel(MyApplication.getInstance().getUser()
							.getUcode(), goods_id, position);
				}

			} else {
				Intent it = new Intent(mContext, LoginActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				mContext.startActivity(it);
			}
		}
	}

	// 点击购物
	private class ShopClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			PersonalCollection perCollection = mList.get(position);
			if (MyApplication.getInstance().isLogin()) {
				// 加入购物车

				if (perCollection != null
						&& perCollection.getGood_info() != null) {
					if (perCollection.getGood_info().getStock()
							- perCollection.getGood_info().getVirtual_sales() > 0) {
						// 有货物
						shopcartaddAdd(MyApplication.getInstance().getUser()
								.getUcode(), perCollection.getGood_info()
								.getGoods_id(), perCollection.getGood_info()
								.getSpec_id(), perCollection.getGood_info()
								.getGoods_sn(), perCollection.getGood_info()
								.getShop_id(), perCollection.getGood_info()
								.getGoods_name(), perCollection.getGood_info()
								.getGoods_image(), 1 + "");
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
	 * 取消收藏
	 * 
	 * @param ucode
	 * @param goods_id
	 * @param positon
	 */
	private void collectgoodsCancel(String ucode, String goods_id,
			final int positon) {
		API.collectgoodsCancel(mContext, ucode, goods_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						switch (state) {
						case STATE_OK:
							Toast.makeText(mContext,
									R.string.cancle_good_sucess,
									Toast.LENGTH_SHORT).show();
							mList.remove(positon);
							notifyDataSetChanged();
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

	// 加入购物车
	private void shopcartaddAdd(String ucode, String goods_id, String spec_id,
			String specification, String shop_id, String goods_name,
			String goods_image, String quantity) {
		API.shopcartaddAdd(mContext, ucode, goods_id, spec_id, specification,
				shop_id, goods_name, goods_image, quantity,
				new ResponseHandler() {

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
}
