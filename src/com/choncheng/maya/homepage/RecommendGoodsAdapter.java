package com.choncheng.maya.homepage;

import java.util.List;

import com.choncheng.maya.R;
import com.choncheng.maya.comm.entity.Advertisement;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�RecommendGoodsAdapter
 * @�������� �Ƽ���Ʒadapter
 * @�����ˣ��
 * @����ʱ�䣺2015-8-8 ����4:27:11
 * @�汾�ţ�v1.0
 * 
 */
public class RecommendGoodsAdapter extends BaseAdapter {
	private List<Advertisement> mList;
	private Context mContext;

	public RecommendGoodsAdapter(List<Advertisement> mList, Context mContext) {
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
					R.layout.recommend_goods_item, null);
			holder.goodsImageView = (ImageView) convertView
					.findViewById(R.id.good_img);
			holder.contentTxt = (TextView) convertView
					.findViewById(R.id.good_content);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(
				CommUtils.getImageUrl(mList.get(position).getAd_image()),
				holder.goodsImageView, BitmapUtil.getDefaultOption());
		holder.contentTxt.setText(mList.get(position).getAd_name());
		return convertView;
	}

	private final class MyHolder {
		ImageView goodsImageView;
		TextView contentTxt;
	}
}
