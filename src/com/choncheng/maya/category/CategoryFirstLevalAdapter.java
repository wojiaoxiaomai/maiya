package com.choncheng.maya.category;

import java.util.List;

import com.choncheng.maya.R;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.utils.BitmapUtil;
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
 * @项目名称：Maya
 * @类名称：CategoryFirstLevalAdapter
 * @类描述：分类中第一级的Adapter
 * @创建人：李波
 * @创建时间：2015-8-8 下午10:11:59
 * @版本号：v1.0
 * 
 */
public class CategoryFirstLevalAdapter extends BaseAdapter {
	private List<Category> mList;
	private Context mContext;
	private int index;

	public CategoryFirstLevalAdapter(List<Category> mList,
			Context mContext) {
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
			holder = new Myholder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.category_first_leval_item, null);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			holder.containerView = convertView
					.findViewById(R.id.contanier_view);
			convertView.setTag(holder);
		} else {
			holder = (Myholder) convertView.getTag();
		}
		if (index == position) {
			holder.containerView.setBackgroundColor(mContext.getResources()
					.getColor(R.color.white));
			holder.nameTxt.setTextColor(mContext.getResources().getColor(
					R.color.green));
		} else {
			holder.containerView.setBackgroundColor(mContext.getResources()
					.getColor(R.color.category_grey_color));
			holder.nameTxt.setTextColor(mContext.getResources().getColor(
					R.color.textview_grey));
		}
		ImageLoader.getInstance().displayImage(
				Constants.SERVER + mList.get(position).getImage(),
				holder.image, BitmapUtil.getDefaultOption2());
		holder.nameTxt.setText(mList.get(position).getCate_name());
		return convertView;
	}

	private class Myholder {
		ImageView image;
		TextView nameTxt;
		View containerView;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
