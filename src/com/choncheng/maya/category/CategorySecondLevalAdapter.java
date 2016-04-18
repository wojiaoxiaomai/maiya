package com.choncheng.maya.category;

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

import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.search.SearchFilterActivity;

/**
 * 
 * @项目名称：Maya
 * @类名称：CategoryFirstLevalAdapter
 * @类描述：分类中二级级的Adapter
 * @创建人：李波
 * @创建时间：2015-8-8 下午10:11:59
 * @版本号：v1.0
 * 
 */
public class CategorySecondLevalAdapter extends BaseAdapter {
	private List<CategorySecondLeval> mList;
	private Context mContext;

	// private int index = -1;

	public CategorySecondLevalAdapter(List<CategorySecondLeval> mList,
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		Myholder holder = null;
		if (convertView == null) {
			holder = new Myholder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.category_second_leval_item, null);
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			holder.nameClickView = convertView
					.findViewById(R.id.name_click_view);
			holder.gridView = (GridView) convertView
					.findViewById(R.id.gridview);

			convertView.setTag(holder);
		} else {
			holder = (Myholder) convertView.getTag();
		}

		// if (index == position) {
		// holder.nameTxt.setTextColor(mContext.getResources().getColor(
		// R.color.green));
		// } else {
		// holder.nameTxt.setTextColor(mContext.getResources().getColor(
		// R.color.login_text_color));
		// }

		holder.nameTxt.setTextColor(mContext.getResources().getColor(
				R.color.login_text_color));
		holder.nameClickView.setTag(position);
		holder.nameClickView.setOnClickListener(new nameClickViewListener());
		holder.nameTxt
				.setText(mList.get(position).getCategory().getCate_name());

		holder.gridView.setAdapter(new CategoryThreeLevalAdapter(mList.get(
				position).getThreeLevalList(), mContext));
		holder.gridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int pos, long id) {
						Intent it = new Intent(mContext,
								SearchFilterActivity.class);

						it.putExtra(
								Extra.CATE_ID,
								Integer.parseInt(mList.get(position)
										.getThreeLevalList().get(pos)
										.getCart_id()));
						it.putExtra(Extra.P_ID, mList.get(position)
								.getThreeLevalList().get(pos).getP_id());
						it.putExtra(Extra.SEARCH_TYPE,
								Constants.SEARCH_TYPE_THIRD);
						it.putExtra(Extra.TITLE, mList.get(position).getCategory().getCate_name());
						it.putExtra(Extra.IS_FROM_CATEGORYACTIVITY, true);
						mContext.startActivity(it);
					}
				});
		return convertView;
	}

	// 二级标题点击事件
	private class nameClickViewListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int positon = (Integer) v.getTag();
			// index = positon;
			notifyDataSetChanged();
			Intent it = new Intent(mContext, SearchFilterActivity.class);
			it.putExtra(
					Extra.CATE_ID,
					Integer.parseInt(mList.get(positon).getCategory()
							.getCart_id()));

			it.putExtra(Extra.P_ID, mList.get(positon).getCategory().getP_id());
			it.putExtra(Extra.SEARCH_TYPE, Constants.SEARCH_TYPE_SECOND);
			it.putExtra(Extra.IS_FROM_CATEGORYACTIVITY, true);
			it.putExtra(Extra.TITLE, mList.get(positon).getCategory().getTitle());
			mContext.startActivity(it);
		}

	}

	private class Myholder {
		TextView nameTxt;
		GridView gridView;
		View nameClickView;
	}

	// public void setIndex(int index) {
	// this.index = index;
	// }
}
