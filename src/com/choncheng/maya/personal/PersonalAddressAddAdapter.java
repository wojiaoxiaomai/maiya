package com.choncheng.maya.personal;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.choncheng.maya.R;
import com.choncheng.maya.comm.entity.Area;

public class PersonalAddressAddAdapter extends BaseAdapter {
	private List<Area> mList;
	private Context mContext;

	public PersonalAddressAddAdapter(List<Area> mList, Context mContext) {
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
					R.layout.personal_address_add_item, null);
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}
		holder.nameTxt.setText(mList.get(position).getArea());
		return convertView;

	}

	private class MyHolder {
		TextView nameTxt;
	}

}
