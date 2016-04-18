package com.choncheng.maya.personal;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.R;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalOrderAdapter
 * @类描述： 我的订单详情adater
 * @创建人：李波
 * @创建时间：2015-8-11 下午7:50:15
 * @版本号：v1.0
 * 
 */
public class PersonalOrderDetailAdapter extends BaseAdapter {
	private List<PersonalOrderDetail> mList;
	private Context mContext;

	public PersonalOrderDetailAdapter(List<PersonalOrderDetail> mList,
			Context mContext) {
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
		Myholder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.personal_order_detail_item, null);
			holder = new Myholder();
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			holder.priceTxt = (TextView) convertView
					.findViewById(R.id.price_txt);
			holder.beforePriceTxt = (TextView) convertView
					.findViewById(R.id.before_price_txt);
			holder.numTxt = (TextView) convertView.findViewById(R.id.num_txt);
			holder.goodsImage = (ImageView) convertView
					.findViewById(R.id.goods_image);
			holder.tag1 = (TextView) convertView.findViewById(R.id.tag1);
			holder.tag2 = (TextView) convertView.findViewById(R.id.tag2);
			holder.tag3 = (TextView) convertView.findViewById(R.id.tag3);
			convertView.setTag(holder);
		} else {
			holder = (Myholder) convertView.getTag();
		}

		PersonalOrderDetail personalOrderDetail=mList.get(position);
		holder.nameTxt.setText(personalOrderDetail.getGoods_name());
		holder.priceTxt.setText(CommUtils.getMoney(personalOrderDetail
				.getGoods_price()));
		holder.beforePriceTxt.setText(CommUtils.getMoney(personalOrderDetail
				.getOld_price()));
		holder.beforePriceTxt.getPaint()
				.setFlags((Paint.STRIKE_THRU_TEXT_FLAG));
		//==>2015.12.9 修改价格展示
		if(personalOrderDetail.getGoods_price().equals(personalOrderDetail.getOld_price())){
			holder.beforePriceTxt.setVisibility(View.GONE);
		}else{
			holder.beforePriceTxt.setVisibility(View.VISIBLE);
			holder.beforePriceTxt.setText(CommUtils.getMoney(personalOrderDetail.getOld_price()));
		}
		//<==2015.12.9 
		holder.numTxt.setText("数量：" + personalOrderDetail.getQuantity() + "");
		ImageLoader.getInstance().displayImage(
				CommUtils.getImageUrl(personalOrderDetail.getGoods_image()),
				holder.goodsImage, BitmapUtil.getDefaultOption());

		List<String> tagList = JSON.parseArray(personalOrderDetail.getExpend1(),
				String.class);
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
		return convertView;
	}

	private class Myholder {
		TextView nameTxt;
		TextView priceTxt;
		TextView beforePriceTxt;
		TextView numTxt;
		ImageView goodsImage;
		TextView tag1;
		TextView tag2;
		TextView tag3;
	}
}
