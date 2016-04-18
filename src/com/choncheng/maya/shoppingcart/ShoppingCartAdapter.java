package com.choncheng.maya.shoppingcart;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @项目名称：Maya
 * @类名称：ShoppingCartAdapter
 * @类描述： 购物车adapter
 * @创建人：李波
 * @创建时间：2015-8-12 上午11:14:50
 * @版本号：v1.0
 * 
 */
public class ShoppingCartAdapter extends BaseAdapter {
	private List<ShoppingCart> mList;
	private Context mContext;
	private DeleteshopCartCallback mDelCallback;
	private CheckboxCallback mCheckboxCallback;

	private QuantityCallback mQuantityCallback;// 数量变化

	// private boolean isUpdateGoodsImage = true;// 是否要刷新商品图片

	public ShoppingCartAdapter(List<ShoppingCart> mList, Context mContext,
			DeleteshopCartCallback mDelCallback,
			CheckboxCallback mCheckboxCallback,
			QuantityCallback mQuantityCallback) {
		this.mList = mList;
		this.mContext = mContext;
		this.mDelCallback = mDelCallback;
		this.mCheckboxCallback = mCheckboxCallback;
		this.mQuantityCallback = mQuantityCallback;
	}

	public interface DeleteshopCartCallback {
		public void onDel(int positon);
	}

	public interface CheckboxCallback {
		public void onCheck(int position, boolean checked);
	}

	public interface QuantityCallback {
		public void onQuantityChange(int position, boolean checked);
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
					R.layout.shopping_cart_item, null);
			holder = new Myholder();
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			holder.priceTxt = (TextView) convertView
					.findViewById(R.id.price_txt);
			holder.beforePriceTxt = (TextView) convertView
					.findViewById(R.id.before_price_txt);
			holder.numEdit = (EditText) convertView.findViewById(R.id.num_edit);
			holder.tag1 = (TextView) convertView.findViewById(R.id.tag1);
			holder.tag2 = (TextView) convertView.findViewById(R.id.tag2);
			holder.tag3 = (TextView) convertView.findViewById(R.id.tag3);
			holder.stateTxt = (TextView) convertView
					.findViewById(R.id.state_txt);
			holder.goodsImage = (ImageView) convertView
					.findViewById(R.id.goods_image);
			holder.deleteImage = (ImageView) convertView
					.findViewById(R.id.del_btn);

			holder.addBtn = (Button) convertView.findViewById(R.id.add_btn);
			holder.minusBtn = (Button) convertView.findViewById(R.id.minus_btn);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.check_box);
			convertView.setTag(holder);
		} else {
			holder = (Myholder) convertView.getTag();
		}
		holder.deleteImage.setTag(position);
		holder.addBtn.setTag(position);
		holder.minusBtn.setTag(position);
		holder.checkBox.setTag(position);
		holder.deleteImage.setOnClickListener(new DeleteClickListener());
		holder.minusBtn.setOnClickListener(new MinusClickListener());
		holder.addBtn.setOnClickListener(new AddClickListener());
		ShoppingCart shoppingCart=mList.get(position);
		holder.nameTxt.setText(shoppingCart.getGoods_name());
		holder.priceTxt.setText(CommUtils.getMoney(shoppingCart.getGoods_info().getGoods_price()));
		holder.beforePriceTxt.setText(CommUtils.getMoney(shoppingCart.getGoods_info().getOld_price()));
		holder.beforePriceTxt.getPaint()
				.setFlags((Paint.STRIKE_THRU_TEXT_FLAG));
		//==>2015.12.9 修改价格展示
		if(shoppingCart.getGoods_info().getGoods_price().equals(shoppingCart.getGoods_info().getOld_price())){
			holder.beforePriceTxt.setVisibility(View.GONE);
		}else{
			holder.beforePriceTxt.setVisibility(View.VISIBLE);
			holder.beforePriceTxt.setText(CommUtils.getMoney(shoppingCart.getGoods_info().getOld_price()));
		}
		//<==2015.12.9 
		
		holder.numEdit.setText(mList.get(position).getQuantity() + "");
		if (mList.get(position).getGoods_info().getStock()
				- mList.get(position).getVirtual_sales() <= 0) {
			// 无货设置false
			mList.get(position).setChecked(false);
		}
		holder.checkBox.setChecked(mList.get(position).isChecked());
		holder.checkBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// isUpdateGoodsImage = false;
						int position = (Integer) buttonView.getTag();

						if (mList.get(position).getGoods_info().getStock()
								- mList.get(position).getVirtual_sales() > 0) {
							// 有货才改变状态，无货就设置false
							mList.get(position).setChecked(isChecked);
							mCheckboxCallback.onCheck(position, isChecked);
						} else {
							mList.get(position).setChecked(false);
							mCheckboxCallback.onCheck(position, false);
						}

					}
				});
		List<String> tagList = JSON.parseArray(mList.get(position)
				.getGoods_info().getExpend1(), String.class);
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

		if (mList.get(position).getGoods_info().getStock()
				- mList.get(position).getVirtual_sales() > 0) {
			// 有货
			holder.stateTxt.setText(R.string.goods_not_empty);
			holder.stateTxt.setTextColor(mContext.getResources().getColor(
					R.color.green));
			holder.stateTxt.setBackgroundDrawable(mContext.getResources()
					.getDrawable(R.drawable.goods_not_empty_bg));
			// 有货的时候可以选择购买商品，无货就不能选择购买了
			holder.checkBox.setEnabled(true);
		} else {
			// 无货
			holder.checkBox.setEnabled(false);
			holder.stateTxt.setText(R.string.goods_empty);
			holder.stateTxt.setTextColor(mContext.getResources().getColor(
					R.color.red));
			holder.stateTxt.setBackgroundDrawable(mContext.getResources()
					.getDrawable(R.drawable.goods_empty_bg));
		}

		ImageLoader.getInstance().displayImage(
				CommUtils.getImageUrl(mList.get(position).getGoods_image()),
				holder.goodsImage, BitmapUtil.getShopcartOption());
		return convertView;
	}

	/*
	 * 删除事件
	 */
	private class DeleteClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int positon = (Integer) v.getTag();
			mDelCallback.onDel(positon);
		}

	}

	/*
	 * 增加数量
	 */
	private class AddClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// isUpdateGoodsImage = false;
			int postion = (Integer) v.getTag();
			int quantity = mList.get(postion).getQuantity();
			quantity++;
			mList.get(postion).setQuantity(quantity);
			notifyDataSetChanged();
			shopcarEditQuantity(mList.get(postion).getCart_id(), quantity);
			mQuantityCallback.onQuantityChange(postion, mList.get(postion)
					.isChecked());
		}

	}

	/*
	 * 减少数量
	 */
	private class MinusClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// isUpdateGoodsImage = false;
			int postion = (Integer) v.getTag();
			int quantity = mList.get(postion).getQuantity();
			if (quantity == 1) {
				Toast.makeText(mContext, R.string.goods_atlest_num,
						Toast.LENGTH_SHORT).show();
				return;
			}
			quantity--;
			mList.get(postion).setQuantity(quantity);
			notifyDataSetChanged();
			shopcarEditQuantity(mList.get(postion).getCart_id(), quantity);
			mQuantityCallback.onQuantityChange(postion, mList.get(postion)
					.isChecked());
		}

	}

	private class Myholder {
		TextView nameTxt;
		TextView priceTxt;
		TextView beforePriceTxt;
		EditText numEdit;
		TextView tag1;
		TextView tag2;
		TextView tag3;
		TextView stateTxt;// 有货还是无货
		ImageView goodsImage;
		ImageView deleteImage;

		Button minusBtn;// 减少数量
		Button addBtn;// 增加数量
		private CheckBox checkBox;// 是否选择购买
	}

	/**
	 * 修改单个购物车的数量
	 * 
	 * @param cart_id
	 * @param quantity
	 */
	private void shopcarEditQuantity(String cart_id, int quantity) {
		if (MyApplication.getInstance().getUser() != null) {
			API.shopcarEditQuantity(mContext, MyApplication.getInstance()
					.getUser().getUcode(), cart_id, quantity,
					new ResponseHandler() {

						@Override
						public void onRese(String data, int state) {
						}
					});
		}
	}
}
