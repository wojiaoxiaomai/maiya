package com.choncheng.maya.category;

import java.util.List;

import com.choncheng.maya.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @项目名称：Maya
 * @类名称：CategoryFirstLevalAdapter
 * @类描述：分类中三级的Adapter
 * @创建人：李波
 * @创建时间：2015-8-8 下午10:11:59
 * @版本号：v1.0
 * 
 */
public class CategoryThreeLevalAdapter extends BaseAdapter {
	private List<Category> mList;
	private Context mContext;
	private int index=-1;

	public CategoryThreeLevalAdapter(List<Category> mList,
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
					R.layout.category_three_leval_item, null);
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			convertView.setTag(holder);
		} else {
			holder = (Myholder) convertView.getTag();
		}
		if (index == position) {
			holder.nameTxt.setTextColor(mContext.getResources().getColor(
					R.color.green));
		} else {
			holder.nameTxt.setTextColor(mContext.getResources().getColor(
					R.color.login_text_color));
		}
		holder.nameTxt.setText(mList.get(position).getCate_name());
		return convertView;
	}

	private class Myholder {
		TextView nameTxt;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
