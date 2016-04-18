package com.choncheng.maya.shoppingcart;

import java.util.List;

import com.choncheng.maya.R;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Status;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.DateTime;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @项目名称：Maya
 * @类名称：GoodsCommentsAdapter
 * @类描述： 商品评价
 * @创建人：李波
 * @创建时间：2015-8-11 下午3:17:28
 * @版本号：v1.0
 * 
 */
public class GoodsCommentsAdapter extends BaseAdapter {
	private List<GoodsComments> mList;
	private Context mContext;

	public GoodsCommentsAdapter(List<GoodsComments> mList, Context mContext) {
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
					R.layout.goods_comments_item, null);
			holder.headImge = (ImageView) convertView
					.findViewById(R.id.head_image);
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			holder.dateTxt = (TextView) convertView.findViewById(R.id.date_txt);
			holder.levalTxt = (TextView) convertView
					.findViewById(R.id.leval_txt);
			holder.contentTxt = (TextView) convertView
					.findViewById(R.id.content_txt);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}

		int level = mList.get(position).getLevel();
		switch (level) {
		case 1:
			holder.levalTxt.setBackgroundDrawable(mContext.getResources()
					.getDrawable(R.drawable.commens_haoping_bg));
			break;
		case 2:
			holder.levalTxt.setBackgroundDrawable(mContext.getResources()
					.getDrawable(R.drawable.commens_zhongping_bg));
			break;
		case 3:
			holder.levalTxt.setBackgroundDrawable(mContext.getResources()
					.getDrawable(R.drawable.commens_chaping_bg));
			break;
		default:
			holder.levalTxt.setVisibility(View.GONE);
			break;
		}
		holder.nameTxt
				.setText(mList.get(position).getUserinfo().getNack_name());
		holder.dateTxt.setText(DateTime.getDateAndSecondbyStemp(mList.get(
				position).getCreate_time()));
		holder.levalTxt
				.setText(Status.getLeval(mList.get(position).getLevel()));

		if (TextUtils.isEmpty(mList.get(position).getContent())) {
			holder.contentTxt.setVisibility(View.GONE);
		} else {
			holder.contentTxt.setVisibility(View.VISIBLE);
			holder.contentTxt.setText(mList.get(position).getContent());
		}

		ImageLoader.getInstance().displayImage(
				Constants.SERVER
						+ mList.get(position).getUserinfo().getHead_img(),
				holder.headImge, BitmapUtil.getCircleOption());
		return convertView;
	}

	private class MyHolder {
		TextView nameTxt;
		ImageView headImge;
		TextView dateTxt;
		TextView levalTxt;
		TextView contentTxt;
	}

}
