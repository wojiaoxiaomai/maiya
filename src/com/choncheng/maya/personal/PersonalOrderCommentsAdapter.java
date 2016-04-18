package com.choncheng.maya.personal;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.choncheng.maya.R;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalOrderAdapter
 * @类描述： 我的订单评价adater
 * @创建人：李波
 * @创建时间：2015-8-11 下午7:50:15
 * @版本号：v1.0
 * 
 */
public class PersonalOrderCommentsAdapter extends BaseAdapter {
	private List<PersonalOrderDetail> mList;
	private Context mContext;

	public PersonalOrderCommentsAdapter(List<PersonalOrderDetail> mList,
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
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final PersonalOrderDetail orderDetail = mList.get(position);
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.personal_order_comments_item, null);
		TextView nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
		TextView priceTxt = (TextView) convertView.findViewById(R.id.price_txt);
		TextView beforePriceTxt = (TextView) convertView
				.findViewById(R.id.before_price_txt);
		ImageView goodsImage = (ImageView) convertView
				.findViewById(R.id.goos_image);
		EditText contentEdit = (EditText) convertView
				.findViewById(R.id.content_edit);
		contentEdit.setText(orderDetail.getComments_content());
		RadioGroup levelGroup = (RadioGroup) convertView
				.findViewById(R.id.group);
		RadioButton level_goods_btn = (RadioButton) convertView
				.findViewById(R.id.btn_1);
		RadioButton level_mid_btn = (RadioButton) convertView
				.findViewById(R.id.btn_2);
		RadioButton level_bad_btn = (RadioButton) convertView
				.findViewById(R.id.btn_3);

		levelGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.btn_1:
					// 好评
					orderDetail.setLevel(1);
					break;
				case R.id.btn_2:
					// 中评
					orderDetail.setLevel(2);
					break;
				case R.id.btn_3:
					// 差评
					orderDetail.setLevel(3);
					break;
				default:
					break;
				}
			}
		});

		if (mList.get(position).getLevel() == 1) {
			level_goods_btn.setChecked(true);
		} else if (mList.get(position).getLevel() == 2) {
			level_mid_btn.setChecked(true);
		} else if (mList.get(position).getLevel() == 3) {
			level_bad_btn.setChecked(true);
		}
		nameTxt.setText(mList.get(position).getGoods_name());
		priceTxt.setText(CommUtils.getMoney(mList.get(position)
				.getGoods_price()));
		beforePriceTxt.setText(CommUtils.getMoney(mList.get(position)
				.getOld_price()));
		beforePriceTxt.getPaint().setFlags((Paint.STRIKE_THRU_TEXT_FLAG));
		//==>2015.12.9 修改价格展示
		if((mList.get(position).getGoods_price()).equals(mList.get(position)
				.getOld_price())){
			beforePriceTxt.setVisibility(View.GONE);
		}else{
			beforePriceTxt.setVisibility(View.VISIBLE);
			beforePriceTxt.setText(CommUtils.getMoney(mList.get(position)
					.getOld_price()));
		}
		//<==2015.12.9 
		
		ImageLoader.getInstance().displayImage(
				CommUtils.getImageUrl(mList.get(position).getGoods_image()),
				goodsImage, BitmapUtil.getDefaultOption());

		contentEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				orderDetail.setComments_content(s.toString());
			}
		});

		return convertView;
	}

}
