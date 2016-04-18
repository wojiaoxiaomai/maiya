package com.choncheng.maya.homepage;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.choncheng.maya.R;
import com.choncheng.maya.comm.entity.Goods;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @项目名称：Maya
 * @类名称：DiscountAndHotSingleGoodsAdapter
 * @类描述： 主页下面的 特惠以及热销的商品单个商品信息adaper
 * @创建人：李波
 * @创建时间：2015-8-8 下午7:02:52
 * @版本号：v1.0
 * 
 */
public class DiscountAndHotSingleGoodsAdapter extends BaseAdapter {
	private List<Goods> mList;
	private Context mContext;

	public DiscountAndHotSingleGoodsAdapter(List<Goods> mList, Context mContext) {
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
					R.layout.discountandhot_single_goos_item, null);
			holder.singleGoodImage = (ImageView) convertView
					.findViewById(R.id.single_good_img);
			holder.contentTxt = (TextView) convertView
					.findViewById(R.id.content_txt);
			holder.actualPriceTxt = (TextView) convertView
					.findViewById(R.id.actual_price_txt);
			holder.beforePriceTxt = (TextView) convertView
					.findViewById(R.id.before_price_txt);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}

		Goods goods=mList.get(position);
		ImageLoader.getInstance().displayImage(
				CommUtils.getImageUrl(goods.getGoods_image()),
				holder.singleGoodImage, BitmapUtil.getDefaultOption());
		holder.beforePriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		holder.contentTxt.setText(goods.getGoods_name());
		holder.actualPriceTxt.setText(CommUtils.getMoney(goods.getGoods_price()));
		//==>2015.12.9 修改价格展示
		//holder.beforePriceTxt.setText(CommUtils.getMoney(goods.getOld_price()));
		if(goods.getGoods_price().equals(goods.getOld_price())){
			holder.beforePriceTxt.setVisibility(View.GONE);
		}else{
			holder.beforePriceTxt.setVisibility(View.VISIBLE);
			holder.beforePriceTxt.setText(CommUtils.getMoney(goods.getOld_price()));
		}
		//<==2015.12.9 
		return convertView;
	}

	private class MyHolder {
		ImageView singleGoodImage;
		TextView contentTxt;
		TextView actualPriceTxt;
		TextView beforePriceTxt;
	}
}
