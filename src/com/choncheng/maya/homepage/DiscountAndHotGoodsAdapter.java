package com.choncheng.maya.homepage;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.choncheng.maya.R;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.customview.NoScrollGridView;
import com.choncheng.maya.shoppingcart.GoodsDetailActivity;

public class DiscountAndHotGoodsAdapter extends BaseAdapter {
	private List<DiscountAndHotGoods> mList;
	private Context mContext;

	public DiscountAndHotGoodsAdapter(List<DiscountAndHotGoods> mList,
			Context mContext) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		MyHolder holder = null;
		if (convertView == null) {
			holder = new MyHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.discountandhotgoods_adapter, null);
			holder.titilTxt = (TextView) convertView
					.findViewById(R.id.title_txt);
			holder.gridView = (NoScrollGridView) convertView
					.findViewById(R.id.gridview);
			holder.parentView = convertView.findViewById(R.id.parent_view);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}
//		if (mList.get(position).getGoodList().isEmpty()) {
//			holder.parentView.setVisibility(View.GONE);
//		} else {
//			holder.parentView.setVisibility(View.VISIBLE);
//		}
		holder.titilTxt.setText(mList.get(position).getAdvertisement()
				.getAd_name());
		holder.gridView.setAdapter(new DiscountAndHotSingleGoodsAdapter(mList
				.get(position).getGoodList(), mContext));
		holder.gridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int gridPosition, long id) {
						Intent it = new Intent(mContext,
								GoodsDetailActivity.class);
						it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP);
						it.putExtra(Extra.GOODS_ID, mList.get(position)
								.getGoodList().get(gridPosition).getGoods_id());
						mContext.startActivity(it);
					}
				});
		return convertView;
	}

	private class MyHolder {
		TextView titilTxt;
		NoScrollGridView gridView;
		View parentView;
	}
}
