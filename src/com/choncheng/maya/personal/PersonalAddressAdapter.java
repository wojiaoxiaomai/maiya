package com.choncheng.maya.personal;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.choncheng.maya.R;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PersonalAddressAdapter
 * @�������� ��ַ����adaper
 * @�����ˣ��
 * @����ʱ�䣺2015-8-18 ����8:28:10
 * @�汾�ţ�v1.0
 * 
 */
public class PersonalAddressAdapter extends BaseAdapter {
	private List<PersonalAddress> mList;
	private Context mContext;
	private UpdateAddressListCallback mCallback;

	public PersonalAddressAdapter(List<PersonalAddress> mList,
			Context mContext, UpdateAddressListCallback mCallback) {
		super();
		this.mList = mList;
		this.mContext = mContext;
		this.mCallback = mCallback;
	}

	public interface UpdateAddressListCallback {
		public void setDefaultAdd(String add_id);
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
					R.layout.personal_address_item, null);
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			holder.telTxt = (TextView) convertView.findViewById(R.id.tel_txt);
			holder.streetTxt = (TextView) convertView
					.findViewById(R.id.city_txt);
			holder.addDetailTxt = (TextView) convertView
					.findViewById(R.id.add_detail_txt);
			holder.defaultTxt = (TextView) convertView
					.findViewById(R.id.set_default_add_txt);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}

		holder.defaultTxt.setTag(position);
		holder.nameTxt.setText(mList.get(position).getLink_uname());
		holder.telTxt.setText(mList.get(position).getTel());
		holder.streetTxt.setText(mList.get(position).getExtend1());
		holder.addDetailTxt.setText(mList.get(position).getAddress());
		holder.defaultTxt.setOnClickListener(new defaultOnClickListener());
		String isDefault = mList.get(position).getIs_default();
		if (isDefault.equals("1")) {
			// 1��Ĭ�ϵ�ַ2����Ĭ�ϵ�ַ
			holder.defaultTxt.setText(R.string.add_default);
			holder.defaultTxt.setTextColor(mContext.getResources().getColor(
					R.color.white));
			holder.defaultTxt.setBackgroundDrawable(mContext.getResources()
					.getDrawable(R.drawable.green_btn_selector));
		} else {
			//
			holder.defaultTxt.setText(R.string.add_set_default);
			holder.defaultTxt.setTextColor(mContext.getResources().getColor(
					R.color.green));
			holder.defaultTxt.setBackgroundColor(mContext.getResources()
					.getColor(R.color.white));
		}
		return convertView;
	}

	private class MyHolder {
		TextView nameTxt;
		TextView telTxt;
		TextView streetTxt;// С�����ֵ߽�
		TextView addDetailTxt;
		TextView defaultTxt;
	}

	/*
	 * ����ΪĬ�ϵ�ַ����¼��������Ĭ�ϣ��Ͳ�����1��Ĭ�ϵ�ַ2����Ĭ�ϵ�ַ
	 */
	private class defaultOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int positon = (Integer) v.getTag();
			if (!mList.get(positon).getIs_default().equals("1")) {
				mCallback.setDefaultAdd(mList.get(positon).getAddr_id());
			}
		}
	}
}
